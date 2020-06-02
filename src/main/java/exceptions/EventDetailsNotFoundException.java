package exceptions;

public class EventDetailsNotFoundException extends Exception{
    public EventDetailsNotFoundException() { }

    public EventDetailsNotFoundException(String message) {
        super(message);
    }
}
