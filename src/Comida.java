public class Comida extends Prato {
    private String[] ingredientes;

    public Comida(String nome, float preco, String[] ingredientes) throws ExceptionPrato{
        super(nome, preco);
        setIngredientes(ingredientes);
    }

    public void setIngredientes(String[] ingredientes){
        this.ingredientes = ingredientes;
    }

    public String[] getIngredientes(){
        return ingredientes;
    }

    @Override
    public String getNome(){
        return super.getNome();
    }

    @Override
    public double getPreco(){
        return super.getPreco();
    }
}
