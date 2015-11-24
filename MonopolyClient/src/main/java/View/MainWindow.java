package View;

import services.RestController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */
public class MainWindow {

    private RestController restController;
    private MainWindowUI mainWindowUI;

    public MainWindow(RestController rc) {
        restController = rc;
        mainWindowUI = new MainWindowUI();
        registerUIActions();
        mainWindowUI.showWindow();
    }

    private void registerUIActions() {

        mainWindowUI.getJoinGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new JoinGameWindow(restController, mainWindowUI);
            }
        });

        mainWindowUI.getStopButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindowUI.closeWindow();
            }
        });

        mainWindowUI.getCreateGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = restController.createGame();
                if (mainWindowUI.getJoinedGamesTextField().getText().equals("Join a Game mate!")) {
                    mainWindowUI.getJoinedGamesTextField().setText(id+"");
                } else {
                    mainWindowUI.getJoinedGamesTextField().setText(mainWindowUI.getJoinedGamesTextField().getText()
                            + ", " + id);
                }
            }
        });

        mainWindowUI.getGetGamesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restController.callRestQuote();
            }
        });
    }

}
