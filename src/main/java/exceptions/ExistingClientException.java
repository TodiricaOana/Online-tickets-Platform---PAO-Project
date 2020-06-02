package exceptions;

public class ExistingClientException extends Exception {
    ExistingClientException() {
    }

    public ExistingClientException(String message) {
        super(message);
    }
}
