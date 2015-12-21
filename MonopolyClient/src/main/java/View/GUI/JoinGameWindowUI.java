package View.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */
public class JoinGameWindowUI {

    private JFrame joinGameFrame;
    private Container container;
    private JLabel idField;
    private JTextField idTextField;
    private JButton okButton;

    public JoinGameWindowUI() {
        joinGameFrame = new JFrame("Join a Game!");
        container = joinGameFrame.getContentPane();
        idField = new JLabel("Which ID do you want to join?");
        idTextField = new JTextField(20);
        okButton = new JButton("JOIN");

        joinGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLayout(new GridLayout(0,2));

        container.add(idField);
        container.add(idTextField);
        container.add(new JLabel());
        container.add(okButton);
    }

    public void showWindow() {
        joinGameFrame.setSize(400,150);
        joinGameFrame.setVisible(true);
    }

    public void closeWindow() {
        joinGameFrame.dispose();
    }

    public JTextField getIdTextField() {
        return idTextField;
    }

    public JButton getOkButton() {
        return okButton;
    }
}
