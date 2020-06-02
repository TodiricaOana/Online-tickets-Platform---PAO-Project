package exceptions;

public class WrongPasswordException extends Exception{
    WrongPasswordException() {
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
