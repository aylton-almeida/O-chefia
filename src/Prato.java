public class Prato {
    private String nome;
    private float preco;
    private char tipo;

    public Prato(String nome, float preco, char tipo) throws ExceptionPrato {
        setNome(nome);
        setPreco(preco);
        setTipo(tipo);
    }

    public Prato(String nome, float preco) throws ExceptionPrato {
        setNome(nome);
        setPreco(preco);
        setTipo('c');
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

    public void setTipo(char tipo) throws ExceptionPrato{
        if (tipo == 'c' || tipo == 'b')
            this.tipo = tipo;
        else
            throw new ExceptionPrato("Tipo invalido");
    }
}
