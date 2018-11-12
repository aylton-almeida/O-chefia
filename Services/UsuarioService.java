import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

public class UsuarioService {

    //Cadastro de usuario
    public Usuario cadastroUsuario(Request request) throws ExceptionUsuario {
        Query query = request.getQuery();
        Usuario user = new Usuario(query.get("nome"), query.get("senha"), query.get("email"), query.get("cpf"), query.get("telefone"));
        return user;
    }

    //Cadastro Usuario de restaurante
    public UsuarioRestaurante cadastroUsuarioRestaurante(Request request) throws ExceptionRestaurante, ExceptionUsuario {
        Query query = request.getQuery();
        return new UsuarioRestaurante(new Restaurante(query.get("nome"), query.get("cnpj"), new Endereco(query.getInteger("numero"), query.get("rua"), query.get("bairro"), query.getInteger("cep"), query.get("cidade"), query.get("uf")), query.getInteger("numMesas"), query.get("telefone")), new Usuario(query.get("nomeUsuario"), query.get("senha"), query.get("email")));
    }
}
