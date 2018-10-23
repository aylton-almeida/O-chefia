import java.util.Date;

public class Comentario {
    private String mensagem;
    private Cliente autor;
    private Date data;

    public Comentario (String mensagem, Cliente autor){
        setMensagem(mensagem);
        setAutor(autor);
        this.data = new Date();
    }

    public Cliente getAutor() {
        return autor;
    }

    private void setAutor(Cliente autor) {
        this.autor = autor;
    }

    public String getMensagem() {
        return mensagem;
    }

    private void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
