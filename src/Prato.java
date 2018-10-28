import org.json.JSONObject;

public class Prato implements JsonFormatter {
    private String nome;
    private double preco;

    //Construtor com nome e preco
    public Prato(String nome, double preco) throws ExceptionPrato {
        setNome(nome);
        setPreco(preco);
    }

    //Construtor vazio
    public Prato() throws ExceptionPrato{
        setNome("Comida");
        setPreco(0.1);
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
        return obj;
    }
}
