public class ExceptionCliente extends Exception{
    private String message;

    public ExceptionCliente(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}