import org.json.JSONObject;

public class Usuario implements JsonFormatter {
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

    public Usuario (){

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
        if (senhaAtual == this.senha)
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
}
