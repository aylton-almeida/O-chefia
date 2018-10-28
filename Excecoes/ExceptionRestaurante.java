public class ExceptionRestaurante extends Exception {
    private String message;

    public ExceptionRestaurante(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
