package vsp.banks.exceptions;

/**
 * Created by alex on 11/19/15.
 */
public class PlayerNotFoundException extends Exception {
  public PlayerNotFoundException() { super(); }
  public PlayerNotFoundException(String message) { super(message); }
  public PlayerNotFoundException(String message, Throwable cause) { super(message, cause); }
  public PlayerNotFoundException(Throwable cause) { super(cause); }
}
