import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
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
    private static PratoService prato = new PratoService();
    private static List<Usuario> listUsuario = new ArrayList<>();
    private static List<Restaurante> listRestaurante = new ArrayList<>();
    private static Usuario user;
    private static UsuarioRestaurante userRestaurante;
    private static Restaurante res;
    private static PrintWriter writer = null;
    private static BufferedReader reader = null;
    private static ObjectMapper mapper = new ObjectMapper();

    public void handle(Request request, Response response) {
        try {
            // Recupera a URL e o método utilizado.

            String path = request.getPath().getPath();

            // Verifica qual ação está sendo chamada

            if (path.startsWith("/cadastrarPratos")) {
                //Cadastro de pratos
                //JSONObject obj = prato.cadastrarPrato(request, userRestaurante);
                //this.enviaResposta(Status.CREATED, response, obj.toString());
            } else {
                if (path.startsWith("/recuperarPratos")) {
                    //Recuperação de pratos
                    //JSONObject obj = prato.recuperarPratos(userRestaurante);
                    //this.enviaResposta(Status.CREATED, response, obj.toString());
                } else {
                    //Cadastro de usuários
                    if (path.startsWith("/cadastrarUsuario")) {
                        JSONObject obj = usuario.cadastroUsuario(request);
                        if (!obj.get("usuario").equals(new Usuario().toJson()))
                            user = mapper.readValue(obj.get("usuario").toString(), Usuario.class);
                        this.enviaResposta(Status.CREATED, response, obj.toString());
                    } else {
                        //Alteração de usuarios
                        if (path.startsWith("/alterarUsuario")) {
                            JSONObject obj = usuario.alteraUsuario(request);
                            if (!obj.get("usuario").equals(new Usuario().toJson()))
                                user = mapper.readValue(obj.get("usuario").toString(), Usuario.class);
                            this.enviaResposta(Status.CREATED, response, obj.toString());
                        } else {
                            //Deletar usuario
                            if (path.startsWith("/deletarUsuario")) {
                                JSONObject obj = usuario.deletaUsuario(request);
                                user = null;
                                this.enviaResposta(Status.CREATED, response, obj.toString());
                            } else {
                                //Login do usuario
                                if (path.startsWith("/loginUsuario")) {
                                    JSONObject obj = usuario.loginUsuario(request);
                                    if (!obj.get("usuario").equals(new Usuario().toJson()))
                                        user = mapper.readValue(obj.get("usuario").toString(), Usuario.class);
                                    this.enviaResposta(Status.CREATED, response, obj.toString());
                                } else {
                                    //Recupera restaurantes
                                    if (path.startsWith("/recuperarRestaurantes")) {
                                        JSONObject obj = new JSONObject();
                                        JSONArray array = new JSONArray();
                                        String line;
                                        try {
                                            reader = new BufferedReader(new FileReader("arquivos/restaurantes"));
                                            while ((line = reader.readLine()) != null) {
                                                listRestaurante.add(mapper.readValue(line, Restaurante.class));
                                            }
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
                                        //cadastro Usuario/restaurante
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
