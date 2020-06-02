package exceptions;

public class NoAvailableSeatsException extends Exception{
    NoAvailableSeatsException() {
    }

    public NoAvailableSeatsException(String message) {
        super(message);
    }
}
