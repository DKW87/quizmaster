package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import view.SceneManager;

public class StudentSignInOutController {

  private SceneManager sceneManager = SceneManager.getSceneManager();

  @FXML
  private Button signInButton;

  @FXML
  private Button signOutButton;

  public void setup() {}

  public void doMenu(ActionEvent event){
    sceneManager.showWelcomeScene();
  }

  public void doSignIn(ActionEvent event){}

  public void doSignOut(ActionEvent event){}
}
