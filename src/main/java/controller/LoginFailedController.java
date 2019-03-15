package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import view.SceneManager;

public class LoginFailedController {

  private SceneManager manager = SceneManager.getSceneManager();

  public LoginFailedController() {
    super();
  }

  @FXML
  public void doTryAgain(ActionEvent event) {
    manager.showLoginScene();
  }

  @FXML
  public void doQuit(ActionEvent event) {
    System.exit(0);
  }
}
