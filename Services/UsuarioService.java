import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

public class UsuarioService {

    //Cadastro de usuario
    public Usuario cadastroUsuario(Request request) throws ExceptionUsuario {
        Query query = request.getQuery();
        Usuario user = new Usuario(query.get("nome"), query.get("senha"), query.get("email"), query.get("cpf"), query.get("telefone"));
        return user;
    }
}
