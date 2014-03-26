package exception;

public class BoxException extends Exception  {

    public BoxException() {
        super();
    }

    public BoxException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
