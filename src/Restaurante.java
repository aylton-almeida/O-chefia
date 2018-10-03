import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Restaurante {
    private String nome, cnpj;
    private Endereco endereco;
    private int numMesas;
    private List<Prato> cardapio;
    private Dictionary comentarios, pedidosAbertos, historicoDePedidos;

    public Restaurante(String nome, String cnpj, Endereco endereco, int numMesas, List<Prato> cardapio) throws ExceptionRestaurante {
        setNome(nome);
        setCnpj(cnpj);
        setEndereco(endereco);
        setNumMesas(numMesas);
        setCardapio(cardapio);
        setComentarios(new Hashtable());
        setPedidosAbertos(new Hashtable());
        setHistoricoDePedidos(new Hashtable());
    }

    public Restaurante(String nome, String cnpj, Endereco endereco, int numMesas) throws ExceptionRestaurante {
        setNome(nome);
        setCnpj(cnpj);
        setEndereco(endereco);
        setNumMesas(numMesas);
        setCardapio(new ArrayList());
        setComentarios(new Hashtable());
        setPedidosAbertos(new Hashtable());
        setHistoricoDePedidos(new Hashtable());
    }

    //Pegar nome restaurante
    public String getNome() {
        return nome;
    }

    //Colocar nome no restaurante
    public void setNome(String nome) throws ExceptionRestaurante {
        if (nome != null && nome.equals(""))
            this.nome = nome;
        else
            throw new ExceptionRestaurante("Nome invalido");
    }

    //Pegar cnpj do restaurante
    public String getCnpj() {
        return cnpj;
    }

    //Colocar cnpj no restaurante
    public void setCnpj(String cnpj) throws ExceptionRestaurante {
        if (cnpj != null && cnpj.equals(""))
            this.cnpj = cnpj;
        else
            throw new ExceptionRestaurante("CNPJ invalido");
    }

    //Pegar endereco do restaurante
    public Endereco getEndereco() {
        return endereco;
    }

    //Colocar endereco no restaurante
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    //Pegar numero de mesas do restaurante
    public int getNumMesas() {
        return numMesas;
    }

    //Colocar numero de mesas do restaurante
    public void setNumMesas(int numMesas) throws ExceptionRestaurante {
        if (numMesas > 0)
            this.numMesas = numMesas;
        else
            throw new ExceptionRestaurante("Numero de mesas invalido");
    }

    //Pegar cardapio
    public List<Prato> getCardapio() {
        return cardapio;
    }

    //Colocar cardapio
    private void setCardapio(List<Prato> cardapio) {
        if (cardapio != null)
            this.cardapio = cardapio;
        else
            this.cardapio = new ArrayList();
    }

    //Adicionar prato ao cardápio
    public void addPratoCardapio(Prato prato) throws ExceptionRestaurante {
        if (prato != null)
            this.cardapio.add(prato);
        else
            throw new ExceptionRestaurante("Prato invalido");
    }

    //Pegar comentarios
    public Dictionary getComentarios() {
        return comentarios;
    }

    //Colocar comentarios
    private void setComentarios(Dictionary comentarios) {
        this.comentarios = comentarios;
    }

    //Adicionar comentario a lista
    public void addComentario(Comentario comentario) throws ExceptionRestaurante {
        if (comentario != null)
            this.comentarios.put(comentarios.size() + 1, comentario);
        else
            throw new ExceptionRestaurante("Comentario invalido");
    }

    //Colocar pedidos abertos
    private void setPedidosAbertos(Dictionary pedidosAbertos) {
        this.pedidosAbertos = pedidosAbertos;
    }

    //Adicionar pedido aos abertos
    public void addPedidoAberto(Pedido pedido) throws ExceptionRestaurante {
        if (pedido != null)
            pedidosAbertos.put(pedidosAbertos.size() + 1, pedido);
        else
            throw new ExceptionRestaurante("Pedido invalido");
    }

    //Pegar pedido em aberto
    public Pedido getPedidoAberto() {
        return (Pedido) pedidosAbertos.get(1);
    }

    //Fechar primeiro pedido e adiciona-lo ao historico de pedido
    public Pedido fechaPrimeiroPedido() {
        historicoDePedidos.put(pedidosAbertos.size() + 1, pedidosAbertos.get(1));
        return (Pedido) pedidosAbertos.remove(1);
    }

    //Pegar historio de pedido
    public Dictionary getHistoricoDePedidos() {
        return historicoDePedidos;
    }

    //Colocar historico de pedido
    private void setHistoricoDePedidos(Dictionary historicoDePedidos) {
        this.historicoDePedidos = historicoDePedidos;
    }
}
