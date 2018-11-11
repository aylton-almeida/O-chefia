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
    private static RestauranteService restaurante = new RestauranteService();
    private static UsuarioService usuario = new UsuarioService();
    private static List<Usuario> listUsuario = new ArrayList<>();
    private static List<Restaurante> listRestaurante = new ArrayList<>();
    private static Usuario user, userRestaurante;
    private static Restaurante res;
    private static PrintWriter writer = null;
    private static BufferedReader reader = null;
    private static ObjectMapper mapper = new ObjectMapper();

    public void handle(Request request, Response response) {
        try {
            // Recupera a URL e o método utilizado.

            String path = request.getPath().getPath();

            // Verifica qual ação está sendo chamada

            //Cadastro de pratos
            if (path.startsWith("/cadastrarPratos")) {
                JSONObject obj = new JSONObject();
                try {
                    reader = new BufferedReader(new FileReader("arquivos/restaurantes"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        listRestaurante.add(mapper.readValue(line, Restaurante.class));
                    }
                    reader.close();

                    File file = new File("arquivos/restaurantes");
                    file.delete();
                    writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/restaurantes")));
                    Query query = request.getQuery();
                    Boolean achouRestaurante = false;
                    for (int i = 0; i < listRestaurante.size() && !achouRestaurante; i++) {
                        if (listRestaurante.get(i).getNome().equals(query.get("nomeRestaurante"))) {
                            achouRestaurante = true;
                            obj.put("status", 1);
                            obj.put("message", restaurante.addPratoCardapio(request, listRestaurante.get(i)));
                        }
                    }
                    if (!achouRestaurante) {
                        obj.put("status", -1);
                        obj.put("message", "Restaurante não encontrado");
                    }
                } catch (Exception e) {
                    obj.put("status", 0);
                    obj.put("type", e.getClass());
                    obj.put("stackTrace", e.getStackTrace());
                    obj.put("message", e.getMessage());
                } finally {
                    for (Restaurante r : listRestaurante) writer.println(r.toJson());
                    if (writer != null)
                        writer.close();
                    if (reader != null)
                        reader.close();
                    listRestaurante = new ArrayList<>();
                    this.enviaResposta(Status.CREATED, response, obj.toString());
                }
            } else {
                //Recuperação de pratos
                if (path.startsWith("/recuperarPratos")) {
                    JSONObject obj = new JSONObject();
                    try {
                        reader = new BufferedReader(new FileReader("arquivos/restaurantes"));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            listRestaurante.add(mapper.readValue(line, Restaurante.class));
                        }
                        Query query = request.getQuery();
                        boolean achou = false;
                        for (Restaurante aListRestaurante : listRestaurante) {
                            if (aListRestaurante.getNome().equals(query.get("nomeRestaurante"))) {
                                achou = true;
                                obj.put("status", 1);
                                obj.put("message", "Pratos recuperados");
                                obj.put("obj", aListRestaurante.cardapioToJsonArray());
                            }
                        }
                        if (!achou) {
                            obj.put("status", 1);
                            obj.put("message", "Restaurante não encontrado");
                        }
                    } catch (Exception e) {
                        obj.put("status", 0);
                        obj.put("type", e.getClass());
                        obj.put("stackTrace", e.getStackTrace());
                        obj.put("message", e.getMessage());
                    } finally {
                        if (reader != null)
                            reader.close();
                        this.enviaResposta(Status.CREATED, response, obj.toString());
                    }
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
                            boolean achouUsuario = false;
                            boolean senhaCorreta = false;
                            for (Usuario aListUsuario : listUsuario) {
                                if (aListUsuario.getEmail().equals(query.get("email"))) {
                                    achouUsuario = true;
                                    if (aListUsuario.getSenha().equals(query.get("senha"))) {
                                        senhaCorreta = true;
                                        user = aListUsuario;
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
                                String line;
                                try {
                                    reader = new BufferedReader(new FileReader("arquivos/restaurantes"));
                                    while ((line = reader.readLine()) != null)
                                        listRestaurante.add(mapper.readValue(line, Restaurante.class));
                                    for (Restaurante r : listRestaurante) {
                                        array.put(r.toJson());
                                    }
                                    obj.put("status", 1);
                                    obj.put("message", "Foram recuperados " + listRestaurante.size() + " restaurantes");
                                    obj.put("array", array);
                                } catch (Exception e) {
                                    obj.put("status", 0);
                                    obj.put("type", e.getClass());
                                    obj.put("stackTrace", e.getStackTrace());
                                    obj.put("message", e.getMessage());
                                } finally {
                                    if (reader != null)
                                        reader.close();
                                    listRestaurante = new ArrayList<>();
                                    this.enviaResposta(Status.CREATED, response, obj.toString());
                                }
                            } else {
                                //cadastro Usuario restaurante
                                if (path.startsWith("/cadastrarRestauranteUsuario")) {
                                    JSONObject obj = new JSONObject();
                                    try {
                                        userRestaurante = usuario.cadastroUsuarioRestaurante(request);
                                        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/usuariosRestaurantes", true)));
                                        writer.println(userRestaurante.toJson());
                                        writer.close();
                                        res = restaurante.cadastroRestaurante(request);
                                        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/restaurantes", true)));
                                        writer.println(res.toJson());
                                        obj.put("status", 1);
                                        obj.put("message", "Cadastro efetuado com sucesso");
                                    } catch (Exception e) {
                                        obj.put("status", 0);
                                        obj.put("type", e.getClass());
                                        obj.put("stackTrace", e.getStackTrace());
                                        obj.put("message", e.getMessage());
                                    } finally {
                                        if (writer != null)
                                            writer.close();
                                        this.enviaResposta(Status.CREATED, response, obj.toString());
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

    public static void main(String[] args) throws IOException {

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
