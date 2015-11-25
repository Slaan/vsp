package View;

import javax.swing.*;
import java.awt.*;

public class MainWindowUI {

    private JFrame mainframe;
    private Container container;
    private JButton stopButton;
    private JButton joinGameButton;
    private JButton createGameButton;
    private JButton getGamesButton;
    private JLabel gamesLabel;
    private JLabel gamesTextField;
    private JLabel myGamesLabel;
    private JLabel joinedGamesTextField;

    public MainWindowUI() {
        mainframe = new JFrame("Monopoly!");
        container = mainframe.getContentPane();
        stopButton = new JButton("End Session");
        joinGameButton = new JButton("Join Game");
        getGamesButton = new JButton("Available Games");
        createGameButton = new JButton("Create New Game");
        gamesLabel = new JLabel("All available Games (id): ");
        joinedGamesTextField = new JLabel("Check available Games!");
        myGamesLabel = new JLabel("My Games (id):  ");
        gamesTextField = new JLabel("Join a Game mate!");

        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLayout(new GridLayout(0, 2));
        container.add(gamesLabel);
        container.add(joinedGamesTextField);
        container.add(myGamesLabel);
        container.add(gamesTextField);
        container.add(getGamesButton);
        container.add(createGameButton);
        container.add(joinGameButton);
        container.add(stopButton);
    }

    public void showWindow() {
        mainframe.setSize(400, 150);
        mainframe.setVisible(true);
    }

    public void closeWindow() {
        mainframe.dispose();
    }

    public JButton getCreateGameButton() {
        return createGameButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JButton getJoinGameButton() {
        return joinGameButton;
    }

    public JButton getGetGamesButton() {
        return getGamesButton;
    }

    public JLabel getGamesTextField() {
        return gamesTextField;
    }

    public JLabel getJoinedGamesTextField() {
        return joinedGamesTextField;
    }


}
