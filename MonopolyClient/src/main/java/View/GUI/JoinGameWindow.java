package View.GUI;

import services.Adapters.GamesServiceAdapter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */
public class JoinGameWindow {

    private GamesServiceAdapter gamesServiceAdapter;
    private JoinGameWindowUI ui;
    private MainWindowUI mainWindowUI;

    public JoinGameWindow(GamesServiceAdapter rc, MainWindowUI mainWindowUI) {
        gamesServiceAdapter = rc;
        this.mainWindowUI = mainWindowUI;
        ui = new JoinGameWindowUI();
        registerUIActions();
        ui.showWindow();
    }

    private void registerUIActions() {
        ui.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = ui.getIdTextField().getText();
                gamesServiceAdapter.registerPlayerForID(id);
                if (mainWindowUI.getGamesTextField().getText().equals("Join a Game mate!")) {
                    mainWindowUI.getGamesTextField().setText(id+"");
                } else {
                    mainWindowUI.getGamesTextField().setText(mainWindowUI.getGamesTextField().getText() +", " +
                            id);
                }
                ui.closeWindow();
            }
        });
    }
}
