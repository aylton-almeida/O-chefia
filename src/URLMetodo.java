import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class URLMetodo implements Container {

    //Dados estaticos
    static RestauranteService restaurante = new RestauranteService();
    static UsuarioService usuario = new UsuarioService();
    static Restaurante jorge;
    static Restaurante XCBurger;
    static List<Usuario> listUsuario = new ArrayList<>();
    static List<Restaurante> listRestaurante = new ArrayList<>();
    static Usuario user, pedro, aylton, lucas;
    static PrintWriter writer = null;
    static BufferedReader reader = null;
    ObjectMapper mapper = new ObjectMapper();

    static {
        try {
            jorge = new Restaurante("Jorge's Burger", "123.456.789-36", new Endereco(413, "rua jorge", "santo antonio", 31123232, "belo horizonte", "Minas gerais"), 20, "31 99938-5992");
            XCBurger = new Restaurante("XCBurger", "132.312.434-43", new Endereco(123, "x", "x", 12312, "São Paulo", "São Paulo"), 15, "31 3112-3223");
            jorge.addPratoCardapio(new Prato("X-Bacon", 10, "Pão, hamburger bovino, bacon, queijo, alface", 1));
            jorge.addPratoCardapio(new Prato("X-Burger", 10, "Pão, hamburger bovino, queijo, alface", 1));
            listRestaurante.add(jorge);
            listRestaurante.add(XCBurger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handle(Request request, Response response) {
        try {
            // Recupera a URL e o método utilizado.

            String path = request.getPath().getPath();

            // Verifica qual ação está sendo chamada

            //Cadastro de pratos
            if (path.startsWith("/cadastrarPratos")) {
                JSONObject obj = new JSONObject();
                try {
                    writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/restaurantes", false)));
                    reader = new BufferedReader(new FileReader("arquivos/restaurantes"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        listRestaurante.add(mapper.readValue(line, Restaurante.class));
                    }
                    Query query = request.getQuery();
                    Boolean achouRestaurante = false;
                    for (int i = 0; i < listRestaurante.size(); i++) {
                        if (listRestaurante.get(i).getNome().equals(query.get("nomeRestaurante"))) {
                            achouRestaurante = true;
                            obj.put("status", 1);
                            obj.put("message", restaurante.addPratoCardapio(request, listRestaurante.get(i)));
                            for (Restaurante r: listRestaurante) writer.println(r.toJson());
                        }
                    }
                    if (!achouRestaurante){
                        obj.put("status", -1);
                        obj.put("message", "Restaurante não encontrado");
                    }
                } catch (Exception e) {
                    obj.put("status", 0);
                    obj.put("type", e.getClass());
                    obj.put("stackTrace", e.getStackTrace());
                    obj.put("message", e.getMessage());
                } finally {
                    writer.close();
                    reader.close();
                    this.enviaResposta(Status.CREATED, response, obj.toString());
                }
            } else {
                //Recuperação de pratos
                if (path.startsWith("/recuperarPratos")) {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("status", 1);
                        obj.put("obj", jorge.cardapioToJsonArray());
                    } catch (Exception e) {
                        obj.put("status", 0);
                        obj.put("type", e.getClass());
                        obj.put("stackTrace", e.getStackTrace());
                        obj.put("message", e.getMessage());
                    }
                    this.enviaResposta(Status.CREATED, response, obj.toString());
                } else {
                    //Cadastro de usuários
                    if (path.startsWith("/cadastrarUsuario")) {
                        JSONObject obj = new JSONObject();
                        try {
                            user = usuario.cadastroUsuario(request);
                            writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/usuarios", true)));
                            writer.println(user.toJson());
                            obj.put("status", 1);
                            obj.put("message", "Cadastro efetuado com sucesso");
                        } catch (Exception e) {
                            obj.put("status", 0);
                            obj.put("type", e.getClass());
                            obj.put("stackTrace", e.getStackTrace());
                            obj.put("message", e.getMessage());
                        } finally {
                            writer.close();
                            this.enviaResposta(Status.CREATED, response, obj.toString());
                        }
                    } else {
                        //Login do usuario
                        if (path.startsWith("/loginUsuario")) {
                            JSONObject obj = new JSONObject();
                            try {
                                reader = new BufferedReader(new FileReader("arquivos/usuarios"));
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    listUsuario.add(mapper.readValue(line, Usuario.class));
                                }
                            } catch (Exception e) {
                                obj.put("status", 0);
                                obj.put("type", e.getClass());
                                obj.put("stackTrace", e.getStackTrace());
                                obj.put("message", e.getMessage());
                            } finally {
                                reader.close();
                            }
                            Query query = request.getQuery();
                            Boolean achouUsuario = false;
                            Boolean senhaCorreta = false;
                            for (int i = 0; i < listUsuario.size(); i++) {
                                if (listUsuario.get(i).getEmail().equals(query.get("email"))) {
                                    achouUsuario = true;
                                    if (listUsuario.get(i).getSenha().equals(query.get("senha"))) {
                                        senhaCorreta = true;
                                        user = listUsuario.get(i);
                                    }
                                }
                            }
                            if (achouUsuario) {
                                if (senhaCorreta) {
                                    obj.put("status", 1);
                                    obj.put("message", "Login efetuado com sucesso");
                                    obj.put("usuario", user.toJson());
                                } else {
                                    obj.put("status", 0);
                                    obj.put("message", "Senha incorreta");
                                }
                            } else {
                                obj.put("status", -1);
                                obj.put("message", "Usuário não encontrado");
                            }
                            listUsuario = new ArrayList<>();
                            this.enviaResposta(Status.CREATED, response, obj.toString());
                        } else {
                            //Recupera restaurantes
                            if (path.startsWith("/recuperarRestaurantes")) {
                                JSONObject obj = new JSONObject();
                                JSONArray array = new JSONArray();
                                for (Restaurante r : listRestaurante) {
                                    array.put(r.toJson());
                                }
                                try {
                                    obj.put("status", 1);
                                    obj.put("message", "Foram recuperados " + listRestaurante.size() + " restaurantes");
                                    obj.put("array", array);
                                } catch (Exception e) {
                                    obj.put("status", 0);
                                    obj.put("type", e.getClass());
                                    obj.put("stackTrace", e.getStackTrace());
                                    obj.put("message", e.getMessage());
                                }
                                this.enviaResposta(Status.CREATED, response, obj.toString());
                            }else{
                                //Login Usuario restaurante
                                if (path.startsWith("/loginUsuarioRestaurante")){
                                    JSONObject obj = new JSONObject();
                                    try {
                                        reader = new BufferedReader(new FileReader("arquivos/usuariosRestaurante"));
                                        String line;
                                        while ((line = reader.readLine()) != null) {
                                            listUsuario.add(mapper.readValue(line, Usuario.class));
                                        }
                                    } catch (Exception e) {
                                        obj.put("status", 0);
                                        obj.put("type", e.getClass());
                                        obj.put("stackTrace", e.getStackTrace());
                                        obj.put("message", e.getMessage());
                                    } finally {
                                        reader.close();
                                    }
                                    Query query = request.getQuery();
                                    Boolean achouUsuario = false;
                                    Boolean senhaCorreta = false;
                                    for (int i = 0; i < listUsuario.size(); i++) {
                                        if (listUsuario.get(i).getEmail().equals(query.get("email"))) {
                                            achouUsuario = true;
                                            if (listUsuario.get(i).getSenha().equals(query.get("senha"))) {
                                                senhaCorreta = true;
                                                user = listUsuario.get(i);
                                            }
                                        }
                                    }
                                    if (achouUsuario) {
                                        if (senhaCorreta) {
                                            obj.put("status", 1);
                                            obj.put("message", "Login efetuado com sucesso");
                                            obj.put("usuario", user.toJson());
                                        } else {
                                            obj.put("status", 0);
                                            obj.put("message", "Senha incorreta");
                                        }
                                    } else {
                                        obj.put("status", -1);
                                        obj.put("message", "Usuário não encontrado");
                                    }
                                    listUsuario = new ArrayList<>();
                                    this.enviaResposta(Status.CREATED, response, obj.toString());
                                }else{
                                    //cadastro Usuario restaurante
                                    if (path.startsWith("/cadastroUsuarioRestaurante")){

                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enviaResposta(Status status, Response response, String str) throws Exception {

        PrintStream body = response.getPrintStream();
        long time = System.currentTimeMillis();

        response.setValue("Content-Type", "application/json");
        response.setValue("Server", "Gestão de restaurantes (1.0)");
        response.setValue("Access-Control-Allow-Origin", "null");
        response.setDate("Date", time);
        response.setDate("Last-Modified", time);
        response.setStatus(status);

        if (str != null)
            body.println(str);
        body.close();
    }

    public static void main(String args[]) throws IOException {

        int porta = 7200;

        // Configura uma conexão soquete para o servidor HTTP.
        Container container = new URLMetodo();
        ContainerSocketProcessor servidor = new ContainerSocketProcessor(container);
        Connection conexao = new SocketConnection(servidor);
        SocketAddress endereco = new InetSocketAddress(porta);
        conexao.connect(endereco);

        System.out.println("Tecle ENTER para interromper o servidor...");
        System.in.read();

        conexao.close();
        servidor.stop();

    }

}
