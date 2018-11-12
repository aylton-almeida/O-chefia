import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Prato implements JsonFormatter, ModelObject, DAOInterface {
    private String nome;
    private String ingredientes;
    private double preco;
    //Prato principal = 1/Bebida = 2/Sobremesa = 3
    private int tipo;

    //Construtor com nome e preco
    public Prato(String nome, double preco, String ingredientes, int tipo) throws ExceptionPrato {
        setIngredientes(ingredientes);
        setNome(nome);
        setPreco(preco);
        setTipo(tipo);
    }

    //Construtor vazio
    public Prato() throws ExceptionPrato {
        setNome("Comida");
        setPreco(0.1);
        setIngredientes("");
    }

    //Alterar nome
    public void setNome(String nome) throws ExceptionPrato {
        if (nome != null && nome != "")
            this.nome = nome;
        else
            throw new ExceptionPrato("Nome invalido");
    }

    //Alerar preco
    public void setPreco(double preco) throws ExceptionPrato {
        if (preco > 0)
            this.preco = preco;
        else
            throw new ExceptionPrato("Preço invalido");
    }

    //Pegar nome
    public String getNome() {
        return nome;
    }

    //Pegar preco
    public double getPreco() {
        return preco;
    }


    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("nome", this.nome);
        obj.put("preco", this.preco);
        obj.put("ingredientes", this.ingredientes);
        obj.put("tipo", this.tipo);
        return obj;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public List<Prato> getAllObjects(Restaurante restaurante) throws Exception{
        return restaurante.getCardapio();
    }

    @Override
    public ModelObject getObject(Object key) throws Exception{
        List<ModelObject> list = this.getAllObjects();
        for (ModelObject l : list)
            if (((Prato) l).getNome().equals(key))
                return l;
        return null;
    }

    public void addObject(Prato p, Restaurante r) throws Exception {
        r.addPratoCardapio(p);
        this.addObject(r);
    }

    @Override
    public void addObject(ModelObject o) throws Exception{
        new Restaurante().updateObject(o);
    }
}
