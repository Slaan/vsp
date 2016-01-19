package vsp.banks.business.logic.twophasecommit.exceptions;

/**
 * Created by alex on 1/19/16.
 */
public class ServiceInconsistentException extends RuntimeException {

  private String uri;

  public ServiceInconsistentException() {
    super();
  }

  public ServiceInconsistentException(String message) {
    super(message);
  }

  public ServiceInconsistentException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceInconsistentException(Throwable cause) {
    super(cause);
  }

  /**
   * Wraps uri.
   */
  public ServiceInconsistentException(String message, String uri) {
    super(message);
    this.uri = uri;
  }

  public String getUri() {
    return uri;
  }
}
