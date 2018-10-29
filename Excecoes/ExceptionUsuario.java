public class ExceptionUsuario extends Exception{
    private String message;

    public ExceptionUsuario(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}