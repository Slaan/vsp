package services.Exception;

import java.io.IOException;

/**
 * Created by slaan on 25.11.15.
 */
public class ServerException extends RuntimeException {

  public ServerException(String s) {
    super(s);
  }
}
