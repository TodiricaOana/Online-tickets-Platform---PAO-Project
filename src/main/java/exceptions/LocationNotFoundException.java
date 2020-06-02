package exceptions;

public class LocationNotFoundException extends Exception{
    public LocationNotFoundException() { }

    public LocationNotFoundException(String message) {
        super(message);
    }
}
