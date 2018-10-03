public class Prato {
    private String nome;
    private float preco;

    public Prato(String nome, float preco) throws ExceptionPrato {
        setNome(nome);
        setPreco(preco);
    }

    public Prato() throws ExceptionPrato{
        setNome("Comida");
        setPreco(0.1F);
    }

    public void setNome(String nome) throws ExceptionPrato {
        if (nome != null && nome != "")
            this.nome = nome;
        else
            throw new ExceptionPrato("Nome invalido");
    }

    public void setPreco(float preco) throws ExceptionPrato {
        if (preco > 0)
            this.preco = preco;
        else
            throw new ExceptionPrato("Preço invalido");
    }

    public String getNome() {
        return nome;
    }

    public float getPreco() {
        return preco;
    }
}
