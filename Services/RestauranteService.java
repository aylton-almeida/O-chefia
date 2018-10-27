import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

public class RestauranteService {
    private Restaurante restaurante;

    //Add prato passado pir web ao cardápio
    public String addPratoCardapio(Request request) throws ExceptionPrato, ExceptionRestaurante {
        Query query = request.getQuery();
        restaurante.addPratoCardapio(new Prato(query.get("nome"), Integer.parseInt(query.get("preco"))));
        return "Prato adicionado";
    }
}
