import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PratoService {
    private static RestauranteService restaurante = new RestauranteService();
    private static UsuarioService usuario = new UsuarioService();
    private static List<Usuario> listUsuario = new ArrayList<>();
    private static List<Restaurante> listRestaurante = new ArrayList<>();
    private static Usuario user, userRestaurante;
    private static Restaurante res;
    private static PrintWriter writer = null;
    private static BufferedReader reader = null;
    private static ObjectMapper mapper = new ObjectMapper();

    public JSONObject cadastrarPrato(Request request, UsuarioRestaurante user) {
        Query query = request.getQuery();
        JSONObject obj = new JSONObject();
        try {
            new Prato().addObject(new Prato(query.get("nome"), Double.parseDouble(query.get("preco")), query.get("ingredientes"), query.getInteger("tipo")), user.getRestaurante());
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
        JSONArray array = new JSONArray();
        try {
            List<Prato> list = new Prato().getAllObjects(user.getRestaurante());
            for (Prato p : list)
                array.put(p.toJson());
            obj.put("status", 1);
            obj.put("message","Foram recuperados " + list.size() + " pratos");
            obj.put("array", array);
        } catch (Exception e) {
            obj.put("status", 0);
            obj.put("message", e.getMessage());
            obj.put("stackTrace", e.getStackTrace());
            obj.put("type", e.getClass());
        }
        return obj;
    }
}
