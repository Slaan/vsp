package View.GUI;

import entities.Player;
import services.Adapters.GamesServiceAdapter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */
public class LogInWindow {

    private GamesServiceAdapter gamesServiceAdapter;
    private LogInWindowUI ui;

    public LogInWindow(GamesServiceAdapter gamesServiceAdapter) {
        this.gamesServiceAdapter = gamesServiceAdapter;
        ui = new LogInWindowUI();
        registerUIActions();
        ui.showWindow();
    }

    private void registerUIActions() {
        ui.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player player = new Player();
                player.setId(ui.getIdTextField().getText());
                player.setName(ui.getNameTextField().getText());
                player.setUri("http://127.0.0.1:4267/jippie");
                gamesServiceAdapter.setOwnPlayer(player);
                new MainWindow(gamesServiceAdapter);
                ui.closeWindow();
            }
        });
    }
}
