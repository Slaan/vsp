package vsp.dice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MyMainClass {
  /**This is a fooking main.
   *
   * @param args asf.
   */
  public static void main(String[] args) {
    SpringApplication.run(MyMainClass.class, args);
    new YellowPageRegisterThread().run();
  }
}
