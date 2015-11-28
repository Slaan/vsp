package services.Exception;

import java.io.IOException;

/**
 * Created by slaan on 25.11.15.
 */
public class ClientException extends RuntimeException {

  public ClientException(String s) {
    super(s);
  }
}
