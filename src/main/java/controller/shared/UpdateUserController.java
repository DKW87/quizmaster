package controller.shared;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import model.dao.UserDao;
import model.entity.user.Role;
import view.SceneManager;

public class UpdateUserController {

  protected SceneManager manager = SceneManager.getSceneManager();
  protected UserDao userDao = UserDao.getInstance();
  protected Role role;

  @FXML
  protected TextField nameField;

  @FXML
  protected TextField passwordField;

  @FXML
  protected MenuButton roleMenuButton;

  @FXML
  protected Button menuButton;


  public void setup() {
    Role[] roles = Role.values();
    for ( Role role: roles) {
      MenuItem item = new MenuItem();
      item.setText(role.toString());
      item.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          setRole(role) ;
        }
      });
      roleMenuButton.getItems().add(item);
    }
  }

  private void setRole(Role role) {
    this.role = role;
  }

  public void doMenu(ActionEvent event) {
    manager.showWelcomeScene();
  }
}
