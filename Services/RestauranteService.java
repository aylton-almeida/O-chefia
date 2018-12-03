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
    private Usuarios usuarioDAO = new Usuarios();
    private UsuarioRestauranteService usuarioRestauranteService = new UsuarioRestauranteService();
    private ObjectMapper mapper = new ObjectMapper();

    //Recupera restaurantes
    public JSONObject recuperaRestaurantes() {
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<ModelObject> list = restaurantesDAO.getAllObjects();
            for (ModelObject r : list) {
                array.put(((Restaurante) r).toJson());
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
    public JSONObject cadastrarRestaurante(Request request) {
        JSONObject obj = new JSONObject();
        try {
            Query query = request.getQuery();
            restaurantesDAO.addObject(new Restaurante(query.get("nome"), query.get("cnpj"), new Endereco(query.getInteger("numero"), query.get("rua"), query.get("bairro"), query.getInteger("cep"), query.get("cidade"), query.get("uf")), query.getInteger("numMesas"), query.get("telefone")));
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

    //Fazer pedido ao restaurante
    public JSONObject fazerPedido(Request request) {
        JSONObject obj = new JSONObject();
        try {
            Query query = request.getQuery();
            List<Item> list = new ArrayList();
            Restaurante restaurante = (Restaurante) restaurantesDAO.getObject(query.get("restaurante"));
            JSONArray array = new JSONArray(query.get("pedido"));
            for (int i = 0; i < array.length(); i++) {
                String nome = array.getJSONObject(i).getString("prato");
                int qtt = array.getJSONObject(i).getInt("quantidade");
                list.add(new Item(restaurante.getPratoPeloNome(nome), qtt, 0));
            }
            Pedido pedido = new Pedido(list, (Usuario) usuarioDAO.getObject(query.get("usuario")));
            restaurante.addPedidoAberto(pedido);
            restaurantesDAO.updateObject(restaurante);
            obj.put("status", 1);
            obj.put("message", "Pedido enviado com sucesso");
        } catch (Exception e) {
            obj.put("status", 0);
            obj.put("type", e.getClass());
            obj.put("stackTrace", e.getStackTrace());
            obj.put("message", e.getMessage());
        }
        return obj;
    }

    //Recuperar pedidos abertos do restaurante
    public JSONObject recuperaPedidos(Restaurante restaurante) {
        JSONObject obj = new JSONObject();
        try {
            JSONArray array = ((Restaurante)restaurantesDAO.getObject(restaurante.getCnpj())).pedidosAbertoToJsonArray();
            obj.put("status", 1);
            obj.put("array", array);
        } catch (Exception e) {
            obj.put("status", 0);
            obj.put("type", e.getClass());
            obj.put("stackTrace", e.getStackTrace());
            obj.put("message", e.getMessage());
        }
        return obj;
    }

    //Alterar status do pedido
    public JSONObject alteraStatus (Request request, Restaurante restaurante) {
        JSONObject obj = new JSONObject();
        try {
            Query query = request.getQuery();
            Pedido pedido = restaurante.getPedidoPeloPreco(query.getFloat("pedido"));
            pedido.alteraStatus(query.getInteger("estado"));
            restaurante.updatePedido(pedido);
            restaurantesDAO.updateObject(restaurante);
            obj.put("status", 1);
            obj.put("message", "Estado alterado com sucesso");
        } catch (Exception e) {
            obj.put("status", 0);
            obj.put("type", e.getClass());
            obj.put("stackTrace", e.getStackTrace());
            obj.put("message", e.getMessage());
        }
        return obj;
    }

}
