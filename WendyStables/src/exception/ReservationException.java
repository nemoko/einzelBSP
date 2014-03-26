package exception;

public class ReservationException extends Exception {

    public ReservationException() {
        super();
    }

    public ReservationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
