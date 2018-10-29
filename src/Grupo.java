import java.time.*;
import java.util.Dictionary;
import java.util.List;

public class Grupo {
	
	private int numMesa;
	private List<Usuario> Usuarios;
	private Instant dataAbertura;
	private Instant dataFechamento;
	private Dictionary historicoDePedidos;
	private Dictionary pedidosAbertos;
	
	public Grupo(int numMesa) {
		setNumMesa(numMesa);
	}
	
	public int getNumMesa() {
		return numMesa;
	}
	public void setNumMesa(int numMesa) {
		this.numMesa = numMesa;
	}
	public List<Usuario> getUsuarios() {
		return Usuarios;
	}
	public void adicionaUsuario(Usuario novoUsuario) {
		getUsuarios().add(novoUsuario);
	}
	public void excluiUsuario(Usuario UsuarioASerRemovido) {
		getUsuarios().remove(UsuarioASerRemovido);
	}
	public Instant getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(Instant dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	public Instant getDataFechamento() {
		return dataFechamento;
	}
	public void setDataFechamento(Instant dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	public Dictionary getHistoricoDePedidos() {
		return historicoDePedidos;
	}
	public void setHistoricoDePedidos(Dictionary historicoDePedidos) {
		this.historicoDePedidos = historicoDePedidos;
	}
	public Dictionary getPedidosAbertos() {
		return pedidosAbertos;
	}
	public void setPedidosAbertos(Dictionary pedidosAbertos) {
		this.pedidosAbertos = pedidosAbertos;
	}
	
	
	
}
