package entities.Exceptions;

/**
 * Created by Daniel Hofmeister on 11.12.2015.
 */
public class EventIdNotFoundException extends Exception{

    public EventIdNotFoundException() {};

    public EventIdNotFoundException(String message) {
        super(message);
    }
}
