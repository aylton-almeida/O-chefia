import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

public class RestauranteService {
    private Restaurante restaurante;

    public String addPratoCardapio(Request request){
        Query query = request.getQuery();
        try {
            restaurante.addPratoCardapio(new Prato(query.get("nome"), Integer.parseInt(query.get("preco"))));
            return "Prato adicionado";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
