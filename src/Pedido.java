import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Pedido implements JsonFormatter {

    private Instant hora;
    private float precoFinal;
    private List<Item> itens;
    private int estado;// estados possíveis: Espera (1), Preparo (2), Finalizado (3), Cancelado (4)
    private int numMesa;

    public Pedido(List<Item> itens, int numMesa) {
        this.setHora(Instant.now());
        this.setItens(itens);
        this.setNumMesa(numMesa);
        this.setEstado(1);
    }

    public void avancaStatus() {
        switch (this.getEstado()) {

            case 1:
                this.setEstado(2);
                break;

            case 2:
                this.setEstado(3);
                break;
            default:
                break;
        }
    }

    public void cancelaPedido() {
        this.setEstado(4);
    }

    public boolean estaAberto() {
        return this.getEstado() != 4;
    }

    public float getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(float precoFinal) {
        this.precoFinal = precoFinal;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(int numMesa) {
        this.numMesa = numMesa;
    }

    public Instant getHora() {
        return hora;
    }

    public void setHora(Instant hora) {
        this.hora = hora;
    }

    public JSONArray itensToJsonArray() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < itens.size(); i++) {
            array.put(((Item) itens.get(i)).toJson());
        }
        return array;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("hora", this.hora);
        obj.put("precoFinal", this.precoFinal);
        obj.put("itens", this.itensToJsonArray());
        obj.put("estado", this.estado);
        obj.put("numMesa", this.numMesa);
        return obj;
    }
}
