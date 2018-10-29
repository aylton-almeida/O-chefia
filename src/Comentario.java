import org.json.JSONObject;
import java.util.Date;

public class Comentario implements JsonFormatter {
    private String mensagem;
    private Usuario autor;
    private Date data;

    public Comentario (String mensagem, Usuario autor){
        setMensagem(mensagem);
        setAutor(autor);
        this.data = new Date();
    }

    public Usuario getAutor() {
        return autor;
    }

    private void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getMensagem() {
        return mensagem;
    }

    private void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("autor", this.autor);
        obj.put("mensagem", this.mensagem);
        obj.put("data", this.data);
        return obj;
    }
}
