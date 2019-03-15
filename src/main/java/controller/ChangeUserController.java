package controller;

import controller.shared.UpdateUserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.entity.user.User;

public class ChangeUserController extends UpdateUserController {

  private User user;

  @FXML
  private Button changeUserButton;

  public void setup(User user) {
    super.setup();
    this.user = user;
    nameField.setText(user.getName());
    passwordField.setText(user.getPassword());
  }

  public void doChangeUser(ActionEvent event) {
    String name = nameField.getText();
    String password = passwordField.getText();
    if (role == null) {
      role = user.getRole();
    }
    userDao.updateUser(user, name, password, role);
    manager.showSelectUserScene();
  }
}
