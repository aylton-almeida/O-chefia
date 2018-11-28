import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import java.util.ArrayList;

public class PratoService {
    private static Restaurantes restauranteDAO = new Restaurantes();

    public JSONObject cadastrarPrato(Request request, Restaurante res) {
        Query query = request.getQuery();
        JSONObject obj = new JSONObject();
        try {
            res.addPratoCardapio(new Prato(query.get("nome"), Double.parseDouble(query.get("preco")), query.get("ingredientes"), query.getInteger("tipo")));
            restauranteDAO.updateObject(res);
            obj.put("status", 1);
            obj.put("message", "Prato cadastrado com sucesso");
        } catch (Exception e) {
            obj.put("status", 0);
            obj.put("message", e.getMessage());
            obj.put("stackTrace", e.getStackTrace());
            obj.put("type", e.getClass());
        }
        return obj;
    }

    public JSONObject recuperarPratos(UsuarioRestaurante user) {
        JSONObject obj = new JSONObject();
        try {
           Restaurante res = (Restaurante) restauranteDAO.getObject(user.getRestaurante().getCnpj());
           obj.put("status", 1);
           obj.put("message", "Foram recuperados " + res.getCardapio().size() + " pratos");
           obj.put("array", res.cardapioToJsonArray());
        } catch (Exception e) {
            obj.put("status", 0);
            obj.put("message", e.getMessage());
            obj.put("stackTrace", e.getStackTrace());
            obj.put("type", e.getClass());
        }
        return obj;
    }
}
