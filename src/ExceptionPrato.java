public class ExceptionPrato extends Exception{
    private String message;

    public ExceptionPrato(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
