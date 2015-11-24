package View;

import entities.Player;
import services.RestController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */
public class LogInWindow {

    private RestController restController;
    private LogInWindowUI ui;

    public LogInWindow(RestController restController) {
        this.restController = restController;
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
                restController.setOwnPlayer(player);
                new MainWindow(restController);
                ui.closeWindow();
            }
        });
    }
}
