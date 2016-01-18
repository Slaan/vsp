package vsp.banks.business.logic.bank.exceptions;

/**
 * Created by alex on 1/18/16.
 */
public class BankNotFoundException extends NotFoundException {

  public BankNotFoundException() {
    super();
  }

  public BankNotFoundException(String message) {
    super(message);
  }

  public BankNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public BankNotFoundException(Throwable cause) {
    super(cause);
  }
}
