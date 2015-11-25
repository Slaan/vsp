package vsp.dice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class MyMainClass {

  public static void main(String[] args) {
    SpringApplication.run(MyMainClass.class, args);
  }
}
