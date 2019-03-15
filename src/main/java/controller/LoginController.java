package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dao.UserDao;
import model.entity.user.User;
import view.Main;
import view.SceneManager;

public class LoginController {

  private UserDao userDao;

  private SceneManager manager = SceneManager.getSceneManager();

  @FXML
  private TextField nameTextField;

  @FXML
  private PasswordField passwordField;

  public LoginController() {
    super();
    this.userDao = UserDao.getInstance();
  }

  @FXML
  public void doLogin(ActionEvent event) {
    String username = nameTextField.getText();
    User user = userDao.findUserByName(username);
    if (user != null) {
      if (user.validate(passwordField.getText())) {
        Main.setCurrentUser(user);
        manager.showWelcomeScene();
      } else {
        manager.showLoginFailedScene();
      }
    } else {
      manager.showLoginFailedScene();
    }
  }

  @FXML
  public void doQuit(ActionEvent event) {
    System.exit(0);
  }
}
