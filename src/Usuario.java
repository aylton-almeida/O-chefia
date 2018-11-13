import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements JsonFormatter, ModelObject, DAOInterface {
    private String nome;
    private String senha;
    private String email;
    private String cpf;
    private String telefone;

    public Usuario(String nome, String senha, String email, String cpf, String telefone) throws ExceptionUsuario {
        setNome(nome);
        setSenha(senha);
        setEmail(email);
        setCpf(cpf);
        setTelefone(telefone);
    }

    public Usuario(String nome, String senha, String email) throws ExceptionUsuario {
        setNome(nome);
        setSenha(senha);
        setEmail(email);
        setCpf(null);
        setTelefone(null);
    }

    public Usuario() {
    }

    public String getNome() {
        return nome;
    }

    //Coloca um nome valido
    public void setNome(String nome) throws ExceptionUsuario {
        if (nome != null && nome != "")
            this.nome = nome;
        else
            throw new ExceptionUsuario("Nome invalido");
    }

    public String getEmail() {
        return email;
    }

    //Coloca um email valido
    public void setEmail(String email) throws ExceptionUsuario {
        if (email != null && email != "")
            this.email = email;
        else
            throw new ExceptionUsuario("Email invalido");
    }

    public String getCpf() {
        return cpf;
    }

    //Coloca um cpf valido
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    //Coloca um telefone valido
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    //Coloca uma senha não nula ou vazia e maior de 8 caracteres
    private void setSenha(String senha) throws ExceptionUsuario {
        if (senha != null && senha != "" && senha.length() >= 8)
            this.senha = senha;
        else {
            if (senha == null || senha == "")
                throw new ExceptionUsuario("Senha invalida");
            else
                throw new ExceptionUsuario("Sua senha deve ter pelo menos 8 caracteres");
        }
    }

    //Get senha
    public String getSenha() {
        return this.senha;
    }

    //Altera a senha quando a senha atual passada for igual a senha do usuario
    public void alteraSenha(String senhaNova, String senhaAtual) throws ExceptionUsuario {
        if (senhaAtual.equals(this.senha))
            setSenha(senhaNova);
        else
            throw new ExceptionUsuario("Senha atual invalida");
    }

    //Converter JSON
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("nome", this.nome);
        obj.put("senha", this.senha);
        obj.put("email", this.email);
        obj.put("cpf", this.cpf);
        obj.put("telefone", this.telefone);
        return obj;
    }

    @Override
    public List<ModelObject> getAllObjects() throws Exception {
        List<ModelObject> list = new ArrayList<>();
        BufferedReader reader;
        String line;
        ObjectMapper mapper = new ObjectMapper();
        reader = new BufferedReader(new FileReader("arquivos/usuarios"));
        while ((line = reader.readLine()) != null) {
            list.add(mapper.readValue(line, Usuario.class));
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public ModelObject getObject(Object key) throws Exception {
        List<ModelObject> list = this.getAllObjects();
        for (ModelObject l : list)
            if (((Usuario) l).getEmail().equals(key))
                return l;
        return null;
    }

    @Override
    public void addObject(ModelObject o) throws Exception {
        PrintWriter writer = null;
        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/usuarios", true)));
        writer.println(((Usuario) o).toJson());
        if (writer != null)
            writer.close();
    }

    @Override
    public void updateObject(ModelObject o) throws Exception {
        PrintWriter writer;
        List<ModelObject> list = this.getAllObjects();
        for (int i = 0; i < list.size(); i++) {
            if (((Usuario) list.get(i)).getEmail().equals(((Usuario) o).getEmail())) {
                list.remove(i);
                list.add(o);
            }
        }
        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/usuarios")));
        for (ModelObject obj : list)
            writer.println(((Usuario) obj).toJson());
        if (writer != null)
            writer.close();
    }

    @Override
    public void deleteObject(ModelObject o) throws Exception {
        List<ModelObject> list = this.getAllObjects();
        PrintWriter writer = null;
        for (int i = 0; i < list.size(); i++) {
            if (((Usuario) list.get(i)).getEmail().equals(((Usuario) o).getEmail()))
                list.remove(i);
        }
        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/usuarios")));
        for (ModelObject obj : list)
            writer.println(((Usuario) obj).toJson());
        if (writer != null)
            writer.close();
    }
}
