package services;

import View.GUI.LogInWindow;
import services.Adapters.GamesServiceAdapter;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */
public class MainThread extends Thread {

    @Override
    public void run() {
        new LogInWindow(new GamesServiceAdapter());
    }
}
