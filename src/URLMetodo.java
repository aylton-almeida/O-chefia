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
    private static UsuarioRestauranteService usuarioRestaurante= new UsuarioRestauranteService();
    private static PratoService prato = new PratoService();
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
                                JSONObject obj = restaurante.recuperaRestaurantes();
                                this.enviaResposta(Status.CREATED, response, obj.toString());
                            } else {
                                //cadastro Usuario/restaurante
                                if (path.startsWith("/cadastrarRestauranteUsuario")) {
                                    JSONObject obj = restaurante.cadastrarRestaurante(request);
                                    this.enviaResposta(Status.CREATED, response, obj.toString());
                                } else {
                                    if (path.startsWith("/cadastrarPratos")) {
                                        //Cadastro de pratos
                                        //JSONObject obj = prato.cadastrarPrato(request, userRestaurante);
                                        //this.enviaResposta(Status.CREATED, response, obj.toString());
                                    } else {
                                        if (path.startsWith("/recuperarPratos")) {
                                            //Recuperação de pratos
                                            //JSONObject obj = prato.recuperarPratos(userRestaurante);
                                            //this.enviaResposta(Status.CREATED, response, obj.toString());
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
