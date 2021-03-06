package View;

import entities.Game;
import services.Exception.ClientException;
import services.Exception.ServerException;
import services.RestController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
                Game game = restController.createGame();
                updateAvailableGames(game);
            }
        });

        mainWindowUI.getGetGamesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              List<Game> games = new ArrayList<>();
              try {
                games = restController.getAllGames();
              } catch (ClientException ce) {
                handleClientException();
              } catch (ServerException se) {
                handleServerException();
              }
              for (Game g : games) {
                updateAvailableGames(g);
              }
            }
        });
    }

  private void handleServerException() {
    new ErrorWindow("Server is at fault, bad Server!");
  }

  private void handleClientException() {
    new ErrorWindow("It's our fault, we are sorry :(");
  }

  public void updateAvailableGames(Game game) {
    if (mainWindowUI.getJoinedGamesTextField().getText().equals("Check available Games!")) {
      mainWindowUI.getJoinedGamesTextField().setText(game.getGameid()+"");
    } else {
      mainWindowUI.getJoinedGamesTextField().setText(mainWindowUI.getJoinedGamesTextField().getText()
          + ", \n" + game.getGameid());
    }

  }

}
