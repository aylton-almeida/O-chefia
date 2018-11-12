import com.fasterxml.jackson.databind.ObjectMapper;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestauranteService {

    //Add prato passado por web ao cardápio
    public String addPratoCardapio(Request request, Restaurante restaurante) throws ExceptionPrato, ExceptionRestaurante {
        Query query = request.getQuery();
        restaurante.addPratoCardapio(new Prato(query.get("nome"), Double.parseDouble(query.get("preco")), query.get("ingredientes"), query.getInteger("tipo")));
        return "Prato adicionado";
    }

    //Cadastro restaurante
    public Restaurante cadastroRestaurante(Request request) throws ExceptionRestaurante {
        Query query = request.getQuery();
        return new Restaurante(query.get("nome"), query.get("cnpj"), new Endereco(query.getInteger("numero"), query.get("rua"), query.get("bairro"), query.getInteger("cep"), query.get("cidade"), query.get("uf")), query.getInteger("numMesas"), query.get("telefone"));
    }
}
