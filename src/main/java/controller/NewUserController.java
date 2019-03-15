package controller;

import controller.shared.UpdateUserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.entity.user.User;
import model.factory.UserFactory;

public class NewUserController extends UpdateUserController {

  @FXML
  protected Button createUserButton;

  public void doCreateUser(ActionEvent event) {
    String name = nameField.getText();
    String password = passwordField.getText();
    User newUser = UserFactory.createUserForRole(name, password, role);
    userDao.addUser(newUser);
    manager.showSelectUserScene();
  }
}
