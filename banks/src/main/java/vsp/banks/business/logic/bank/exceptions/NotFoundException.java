package vsp.banks.business.logic.bank.exceptions;

/**
 * Created by alex on 1/18/16.
 */
public class NotFoundException extends Exception {

  public NotFoundException() {
    super();
  }

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotFoundException(Throwable cause) {
    super(cause);
  }
}
