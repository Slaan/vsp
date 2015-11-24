package services;

import View.LogInWindow;
import View.MainWindow;
import entities.Player;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */
public class MainThread extends Thread {

    @Override
    public void run() {
        new LogInWindow(new RestController());
    }
}
