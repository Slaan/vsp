package vsp.banks.business.adapter.exceptions;

/**
 * Created by alex on 1/20/16.
 */
public class NetworkException extends Exception {

  String uri;

  public NetworkException() {
        super();
  }

  public NetworkException(String message, String uri) {
    super(message);
    this.uri = uri;
  }

  public NetworkException(String message, Throwable cause) {
        super(message, cause);
  }

  public NetworkException(Throwable cause) {
        super(cause);
  }

  public String getUri() {
    return this.uri;
  }
}
