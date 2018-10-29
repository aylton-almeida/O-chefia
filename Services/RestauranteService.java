import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

public class RestauranteService {

    //Add prato passado pir web ao cardápio
    public String addPratoCardapio(Request request, Restaurante restaurante) throws ExceptionPrato, ExceptionRestaurante {
        Query query = request.getQuery();
        restaurante.addPratoCardapio(new Prato(query.get("nome"), Double.parseDouble(query.get("preco")), query.get("ingredientes")));
        return "Prato adicionado";
    }
}
