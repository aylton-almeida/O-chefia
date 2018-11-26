import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;
import org.simpleframework.http.Status;
import org.simpleframework.xml.stream.Mode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RestauranteService {
    private Restaurantes restaurantesDAO = new Restaurantes();
    private UsuarioRestauranteService usuarioRestauranteService = new UsuarioRestauranteService();

    //Recupera restaurantes
    public JSONObject recuperaRestaurantes() {
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<ModelObject> list = restaurantesDAO.getAllObjects();
            for (ModelObject r : list) {
                array.put(((Restaurante)r).toJson());
            }
            obj.put("status", 1);
            obj.put("message", "Foram recuperados " + list.size() + " restaurantes");
            obj.put("array", array);
        } catch (Exception e) {
            obj.put("status", 0);
            obj.put("type", e.getClass());
            obj.put("stackTrace", e.getStackTrace());
            obj.put("message", e.getMessage());
        }
        return obj;
    }

    //Cadastro restaurante
    public JSONObject cadastrarRestaurante (Request request){
        JSONObject obj = new JSONObject();
        try {
            Query query = request.getQuery();
            restaurantesDAO.addObject( new Restaurante(query.get("nome"), query.get("cnpj"), new Endereco(query.getInteger("numero"), query.get("rua"), query.get("bairro"), query.getInteger("cep"), query.get("cidade"), query.get("uf")), query.getInteger("numMesas"), query.get("telefone")));
            obj = usuarioRestauranteService.cadastroUsuarioRestaurante(request);
            obj.put("status", 1);
            obj.put("message", "Cadastro efetuado com sucesso");
        } catch (Exception e) {
            obj.put("status", 0);
            obj.put("type", e.getClass());
            obj.put("stackTrace", e.getStackTrace());
            obj.put("message", e.getMessage());
        }
        return obj;
    }

}
