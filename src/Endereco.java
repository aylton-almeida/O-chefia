import org.json.JSONObject;

public class Endereco implements JsonFormatter {
    private int numero;
    private String rua;
    private String bairro;
    private int cep;
    private String cidade;
    private String uf;

    public Endereco(int numero, String rua, String bairro, int cep, String cidade, String uf) {
        setNumero(numero);
        setRua(rua);
        setBairro(bairro);
        setCep(cep);
        setCidade(cidade);
        setUf(uf);
    }

    public Endereco() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua.substring(0, 1).toUpperCase() + rua.substring(1).toLowerCase();
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro.substring(0, 1).toUpperCase() + bairro.substring(1).toLowerCase();
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade.substring(0, 1).toUpperCase() + cidade.substring(1).toLowerCase();
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf.substring(0, 1).toUpperCase() + uf.substring(1).toLowerCase();
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("numero", this.numero);
        obj.put("rua", this.rua);
        obj.put("bairro", this.bairro);
        obj.put("cep", this.cep);
        obj.put("cidade", this.cidade);
        obj.put("uf", this.uf);
        return obj;
    }
}
