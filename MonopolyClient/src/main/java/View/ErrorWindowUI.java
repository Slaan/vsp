package View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by slaan on 25.11.15.
 */
public class ErrorWindowUI {

  private JFrame errorFrame;
  private Container container;
  private JLabel errorField;
  private JButton okButton;


  public ErrorWindowUI() {
    errorFrame = new JFrame("Sorry!");
    container = errorFrame.getContentPane();
    errorField = new JLabel("Error: A Error happend displaying the Error");
    okButton = new JButton("OK");

    errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    container.setLayout(new GridLayout(0, 1));

    container.add(errorField);
    container.add(new JLabel());
    container.add(okButton);
    showWindow();
  }

  public void showWindow() {
    errorFrame.setSize(400,150);
    errorFrame.setVisible(true);
  }

  public void closeWindow() {
    errorFrame.dispose();
  }

  public JLabel getErrorField() {
    return errorField;
  }

  public JButton getOkButton() {
    return okButton;
  }
}
