public class UsuarioRestaurante extends Usuario{
    private Restaurante restaurante;

    public UsuarioRestaurante(Restaurante restaurante, Usuario usuario) throws ExceptionUsuario{
        super(usuario.getNome(), usuario.getSenha(), usuario.getEmail(), usuario.getCpf(), usuario.getTelefone());
        setRestaurante(restaurante);
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }
}
