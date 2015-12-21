package View.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by slaan on 25.11.15.
 */
public class ErrorWindow {

  private ErrorWindowUI ui;

  public ErrorWindow(String error) {
    ui = new ErrorWindowUI();
    registerUIActions();
  }

  public void registerUIActions() {
    ui.getOkButton().addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        ui.closeWindow();
      }
    });
  }
}
