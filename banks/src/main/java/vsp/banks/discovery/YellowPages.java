package vsp.banks.discovery;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alex on 25.11.15.
 */
public class YellowPages {

  private String yellowPageHost;

  public YellowPages(String yellowPageHost) {
    this.yellowPageHost = yellowPageHost;
    determineUri();
  }

  private void determineUri() {
    // impl me!
  }

  /**
   * Registers this service to yellow page host.
   */
  public void register(String name, String description, String service, String uri)
      throws UnirestException, IOException {
    Unirest.post(yellowPageHost + "/services")
        .field("name", name)
        .field("description", description)
        .field("service", service)
        .field("uri", uri)
        .asJson();
    Unirest.shutdown();
  }
}
