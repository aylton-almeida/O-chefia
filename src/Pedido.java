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
    private Usuario usuario;

    public Pedido(List<Item> itens, Usuario usuario) {
        this.setHora(Instant.now());
        this.setItens(itens);
        setUsuario(usuario);
        this.setEstado(1);
        setPrecoFinal(0);
        calculaPrecoFinal();
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
        obj.put("usuario", this.usuario.toJson());
        return obj;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void calculaPrecoFinal(){
        for (Item i : itens)
            precoFinal += i.getPreco();
    }
}
