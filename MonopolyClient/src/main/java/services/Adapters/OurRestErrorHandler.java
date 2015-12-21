package services.Adapters;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import services.Exception.ClientException;
import services.Exception.ServerException;

import java.io.IOException;

/**
 * Created by slaan on 25.11.15.
 */
public class OurRestErrorHandler implements ResponseErrorHandler {

  @Override public boolean hasError(ClientHttpResponse response) throws IOException {
    if (response.getStatusCode().is5xxServerError()) {
      return true;
    } else if (response.getStatusCode().is4xxClientError()) {
      return true;
    } else {
      return false;
    }
  }

  @Override public void handleError(ClientHttpResponse response)
      throws ServerException, ClientException {
    int statusCode=0;
    try {
      if (response.getStatusCode().is5xxServerError()) {
        statusCode=500;
      } else if (response.getStatusCode().is4xxClientError()) {
        statusCode=400;
      }
    } catch (IOException e) {
      System.err.print("IOException was caught when trying to accsess response Status Code: \n"
          + response.toString());
    }
    switch(statusCode) {
      case 500: throw new ServerException("Server Exception");
      case 400: throw new ClientException("Client Exception!");
    }
  }
}
