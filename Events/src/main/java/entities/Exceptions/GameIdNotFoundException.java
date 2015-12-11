package entities.Exceptions;

/**
 * Created by Daniel Hofmeister on 11.12.2015.
 */
public class GameIdNotFoundException extends Exception {


    public GameIdNotFoundException() {}

    public GameIdNotFoundException(String message) {
        super(message);
    }
}
