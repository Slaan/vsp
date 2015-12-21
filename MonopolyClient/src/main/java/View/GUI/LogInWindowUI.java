package View.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */
public class LogInWindowUI {

    private JFrame logInFrame;
    private Container container;
    private JLabel idField;
    private JTextField idTextField;
    private JLabel nameField;
    private JTextField nameTextField;
    private JButton okButton;

    public LogInWindowUI() {
        logInFrame = new JFrame("Log In");
        container = logInFrame.getContentPane();
        idField = new JLabel("Whats your id?");
        idTextField = new JTextField(20);
        nameField = new JLabel("Whats your name?");
        nameTextField = new JTextField(20);
        okButton = new JButton("JOIN");

        logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLayout(new GridLayout(0,2));

        container.add(nameField);
        container.add(nameTextField);
        container.add(idField);
        container.add(idTextField);
        container.add(new JLabel());
        container.add(okButton);
    }

    public void showWindow() {
        logInFrame.setSize(400, 150);
        logInFrame.setVisible(true);
    }

    public void closeWindow() {
        logInFrame.dispose();
    }

    public JLabel getIdField() {
        return idField;
    }

    public JTextField getIdTextField() {
        return idTextField;
    }

    public JLabel getNameField() {
        return nameField;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JButton getOkButton() {
        return okButton;
    }
}
