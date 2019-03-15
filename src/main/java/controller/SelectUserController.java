package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.dao.UserDao;
import model.entity.user.User;
import view.SceneManager;

import java.util.List;

public class SelectUserController {

  private SceneManager manager = SceneManager.getSceneManager();
  private UserDao userDao = UserDao.getInstance();

  @FXML
  private ListView<String> userList;

  public void setup() {
    List<User> users = userDao.getAllUsers();
    for ( User u: users ) {
      userList.getItems().add(u.getName());
    }
  }

  public void doMenu(ActionEvent event) {
    manager.showWelcomeScene();
  }

  public void doChangeUser(ActionEvent event) {
    int index = userList.getSelectionModel().getSelectedIndex();
    User user = userDao.findUserByIndex(index);
    if ( user != null ) {
      manager.showChangeUserScene(user);
    }
  }

  public void doDeleteUser(ActionEvent event) {
    int index = userList.getSelectionModel().getSelectedIndex();
    User user = userDao.findUserByIndex(index);
    if (user != null) {
      userDao.deleteUser(user);
      manager.showSelectUserScene();
    }
  }
}
