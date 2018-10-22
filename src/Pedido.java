import java.util.Calendar;
import java.util.TimeZone;

public class Pedido {

	private Calendar hora;
	private float precoFinal;
	private Item[] itens;
	private String estado;// estados possíveis: Espera, Preparo, Finalizado, Cancelado
	private int numMesa;
	
	public Pedido(Item[] itens, int numMesa) {
		this.setHora(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")));
		this.setItens(itens);
		this.setNumMesa(numMesa);
		this.setEstado("Espera");
	}

	public void avancaStatus() {
		switch(this.getEstado()) {
		
		case "Espera":
			this.setEstado("Preparo");
			break;
			
		case "Preparo":	
			this.setEstado("Finalizado");
			break;
		
		case "Finalizado":
		default:
			break;
		
		}
	} 
	
	public void cancelaPedido() {
		this.setEstado("Cancelado");
	}
	
	public boolean estaAberto() {
		return this.getEstado() == "cancelado"? false : true;
	}
	
	public Calendar getHora() {
		return hora;
	}
	public void setHora(Calendar hora) {
		this.hora = hora;
	}
	public float getPrecoFinal() {
		return precoFinal;
	}
	public void setPrecoFinal(float precoFinal) {
		this.precoFinal = precoFinal;
	}
	public Item[] getItens() {
		return itens;
	}
	public void setItens(Item[] itens) {
		this.itens = itens;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getNumMesa() {
		return numMesa;
	}
	public void setNumMesa(int numMesa) {
		this.numMesa = numMesa;
	}
}
