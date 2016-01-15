package vsp.banks.access.exceptions;

/**
 * Created by alex on 11/25/15.
 */
public class PlayerNotFoundException extends Exception {

  public PlayerNotFoundException() {
    super();
  }

  public PlayerNotFoundException(String message) {
    super(message);
  }

  public PlayerNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public PlayerNotFoundException(Throwable cause) {
    super(cause);
  }
}
