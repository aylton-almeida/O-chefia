import org.json.JSONObject;

public class Item implements JsonFormatter {

    private int quantidade;
    private Prato prato;
    private float preco;
    private float desconto;

    public Item(Prato prato, int quantidade, float desconto) {
        setPrato(prato);
        setQuantidade(quantidade);
        setDesconto(desconto);
        setPreco((float) ((prato.getPreco() * quantidade) - desconto));
    }

    public Item() {
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Prato getPrato() {
        return prato;
    }

    public void setPrato(Prato prato) {
        this.prato = prato;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public float getDesconto() {
        return desconto;
    }

    public void setDesconto(float desconto) {
        this.desconto = desconto;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("quantidade", this.quantidade);
        obj.put("preco", this.preco);
        obj.put("desconto", this.desconto);
        obj.put("prato", this.prato.toJson());
        return obj;
    }
}
