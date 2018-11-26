import org.json.JSONObject;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

public class UsuarioRestauranteService {
    private UsuariosRestaurante usuariosRestaurante = new UsuariosRestaurante();

    //Cadastro Usuario de restaurante
    public JSONObject cadastroUsuarioRestaurante(Request request) throws ExceptionRestaurante, ExceptionUsuario {
        Query query = request.getQuery();
        JSONObject obj = new JSONObject();
        try{
        UsuarioRestaurante user = new UsuarioRestaurante(new Restaurante(query.get("nome"), query.get("cnpj"), new Endereco(query.getInteger("numero"), query.get("rua"), query.get("bairro"), query.getInteger("cep"), query.get("cidade"), query.get("uf")), query.getInteger("numMesas"), query.get("telefone")), new Usuario(query.get("nomeUsuario"), query.get("senha"), query.get("email")));
        usuariosRestaurante.addObject(user);
        obj.put("user", user);
        }catch (Exception e){
            obj.put("status", 0);
            obj.put("message", e.getMessage());
            obj.put("stackTrace", e.getStackTrace());
            obj.put("type", e.getClass());
        }
        return obj;
    }
}
