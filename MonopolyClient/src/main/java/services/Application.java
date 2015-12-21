package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(Application.class);
        new MainThread().run();
    }
}
