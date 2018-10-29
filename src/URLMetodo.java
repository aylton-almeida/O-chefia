import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;

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
    static Restaurante jorge;

    static {
        try {
            jorge = new Restaurante("Jorge's Burger", "123.456.789-36", new Endereco(), 20);
            jorge.addPratoCardapio(new Prato("X-Bacon", 10, "Pão, hamburger bovino, bacon, queijo, alface"));
            jorge.addPratoCardapio(new Prato("X-Burger", 10, "Pão, hamburger bovino, queijo, alface"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handle(Request request, Response response) {
        try {

            // Recupera a URL e o método utilizado.

            String path = request.getPath().getPath();

            // Verifica qual ação está sendo chamada

            if (path.startsWith("/cadastrarPratos")) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("status", 1);
                    obj.put("message", restaurante.addPratoCardapio(request, jorge));
                } catch (Exception e) {
                    obj.put("status", 0);
                    obj.put("type", e.getClass());
                    obj.put("stackTrace", e.getStackTrace());
                    obj.put("message", e.getMessage());
                }
                this.enviaResposta(Status.CREATED, response, obj.toString());
            }

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
