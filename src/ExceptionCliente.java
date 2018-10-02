public class ExceptionCliente extends Exception{
    private String message;

    public ExceptionCliente(String message){
        setMessage(message);
    }

    private void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}