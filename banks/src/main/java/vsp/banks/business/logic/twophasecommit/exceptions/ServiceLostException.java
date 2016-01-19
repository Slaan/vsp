package vsp.banks.business.logic.twophasecommit.exceptions;

/**
 * Created by alex on 1/19/16.
 */
public class ServiceLostException extends RuntimeException {

  private String uri;

  public ServiceLostException() {
    super();
  }

  public ServiceLostException(String message) {
    super(message);
  }

  public ServiceLostException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceLostException(Throwable cause) {
    super(cause);
  }

  /**
   * Wraps uri.
   */
  public ServiceLostException(String message, String uri) {
    super(message);
    this.uri = uri;
  }

  public String getUri() {
    return uri;
  }
}
