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

    private static RestauranteService restaurante = new RestauranteService();

    public void handle(Request request, Response response) {
        try {

            // Recupera a URL e o método utilizado.

            String path = request.getPath().getPath();
            String mensagem = "";

            // Verifica qual ação está sendo chamada

            if (path.startsWith("/cadastrarPratos"))
            try{
                mensagem = restaurante.addPratoCardapio(request);
            }catch(Exception e){
                mensagem = e.getMessage();
            }
            this.enviaResposta(Status.CREATED, response, mensagem);


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

//    public JSONObject toJSON() throws JSONException {
//        JSONObject json = new JSONObject();
//        // json.put("id", id);
//        // json.put("text", text);
//        return json;
//    }

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
