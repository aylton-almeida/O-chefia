import org.json.JSONObject;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;
import org.simpleframework.http.Status;

import java.io.*;
import java.util.ArrayList;

public class UsuarioService {

    //Cadastro de usuario
    public JSONObject cadastroUsuario(Request request) {
        Query query = request.getQuery();
        JSONObject obj = new JSONObject();
        try {
            new Usuario().addObject(new Usuario(query.get("nome"), query.get("senha"), query.get("email"), query.get("cpf"), query.get("telefone")));
            Usuario user = new Usuario(query.get("nome"), query.get("senha"), query.get("email"), query.get("cpf"), query.get("telefone"));
            obj.put("status", 1);
            obj.put("message", "Cadastro efetuado com sucesso");
            obj.put("usuario", user.toJson());
        } catch (Exception e) {
            obj.put("status", 0);
            obj.put("type", e.getClass());
            obj.put("stackTrace", e.getStackTrace());
            obj.put("message", e.getMessage());
            obj.put("usuario", new Usuario().toJson());
        }
        return obj;
    }

    //Login cadastro
    public JSONObject loginUsuario(Request request) {
        JSONObject obj = new JSONObject();
        Query query = request.getQuery();
        Usuario user = null;
        try {
            user = (Usuario) new Usuario().getObject(query.get("email"));
        } catch (Exception e) {
            obj.put("status", 0);
            obj.put("type", e.getClass());
            obj.put("stackTrace", e.getStackTrace());
            obj.put("message", e.getMessage());
            obj.put("usuario", new Usuario().toJson());
        }
        if (user != null) {
            if (user.getSenha().equals(query.get("senha"))) {
                obj.put("status", 1);
                obj.put("message", "Login efetuado com sucesso");
                obj.put("usuario", user.toJson());
            } else {
                obj.put("status", 0);
                obj.put("message", "Senha incorreta");
                obj.put("usuario", new Usuario().toJson());
            }
        } else {
            obj.put("status", -1);
            obj.put("message", "Usuário não encontrado");
            obj.put("usuario", new Usuario().toJson());
        }
        return obj;
    }

    //Altera usuario
    public JSONObject alteraUsuario(Request request) {
        JSONObject obj = new JSONObject();
        Query query = request.getQuery();
        try {
            Usuario user = (Usuario) new Usuario().getObject(query.get("email"));
            user.alteraSenha(query.get("senhaNova"), query.get("senhaAtual"));
            user.updateObject(new Usuario(query.get("nome"), query.get("senhaNova"), query.get("email"), query.get("cpf"), query.get("telefone")));
            obj.put("status", 1);
            obj.put("message", "Alteração feita com sucesso");
            obj.put("usuario", new Usuario(query.get("nome"), query.get("senhaNova"), query.get("email"), query.get("cpf"), query.get("telefone")).toJson());
        } catch (Exception e) {
            obj.put("status", 0);
            obj.put("type", e.getClass());
            obj.put("stackTrace", e.getStackTrace());
            obj.put("message", e.getMessage());
            obj.put("usuario", new Usuario().toJson());
        }
        return obj;
    }

    //Deletar usuario
    public JSONObject deletaUsuario(Request request) {
        JSONObject obj = new JSONObject();
        Query query = request.getQuery();
        try {
            Usuario user = (Usuario) new Usuario().getObject(query.get("email"));
            user.deleteObject(user);
            obj.put("status", 1);
            obj.put("message", "Usuario deletado com sucesso");
            obj.put("usuario", new Usuario().toJson());
        } catch (Exception e) {
            obj.put("status", 0);
            obj.put("type", e.getClass());
            obj.put("stackTrace", e.getStackTrace());
            obj.put("message", e.getMessage());
            obj.put("usuario", new Usuario().toJson());
        }
        return obj;
    }

    //Cadastro Usuario de restaurante
    public UsuarioRestaurante cadastroUsuarioRestaurante(Request request) throws ExceptionRestaurante, ExceptionUsuario {
        Query query = request.getQuery();
        return new UsuarioRestaurante(new Restaurante(query.get("nome"), query.get("cnpj"), new Endereco(query.getInteger("numero"), query.get("rua"), query.get("bairro"), query.getInteger("cep"), query.get("cidade"), query.get("uf")), query.getInteger("numMesas"), query.get("telefone")), new Usuario(query.get("nomeUsuario"), query.get("senha"), query.get("email")));
    }
}
