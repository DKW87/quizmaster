package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import model.entity.user.Role;
import model.entity.user.User;
import model.factory.UserFactory;
import view.Main;
import view.SceneManager;

import java.io.File;

public class WindowToolController {

  private SceneManager manager = SceneManager.getSceneManager();

  @FXML
  private Button startButton;

  @FXML
  private MenuButton screenMenuButton;

  public WindowToolController() {
    super();
    System.out.println();
  }

  public void doStart(ActionEvent event) {
    manager.showLoginScene();
  }

  public void populateScreenMenu() {
    String dir = System.getProperty("user.dir");
    File fxmlDir = new File(dir + "/src/main/java/view/fxml");
    if (fxmlDir.isDirectory()) {
      String[] files = fxmlDir.list();
      for (String filename : files) {
        if (!filename.equals("windowtool.fxml")) {
          MenuItem item = new MenuItem();
          item.setText(filename);
          item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              User tech = UserFactory.createUserForRole("Willy Wortel", "x", Role.TECHSUPPORT);
              User student = UserFactory.createUserForRole("Kwik", "x", Role.STUDENT);
              User admin = UserFactory.createUserForRole("Sidonia", "x", Role.ADMIN);
              Main.setCurrentUser(student);
              switch (filename) {
                case "welcomeScene.fxml":
                  Main.setCurrentUser(student);
                  manager.showWelcomeScene();
                  break;
                case "newUser.fxml":
                  Main.setCurrentUser(tech);
                  manager.showNewUserScene();
                  break;
                case "selectUser.fxml":
                  Main.setCurrentUser(tech);
                  manager.showSelectUserScene();
                  break;
                default:
                  manager.getScene("/view/fxml/" + filename);
                  break;
              }
            }
          });
          screenMenuButton.getItems().add(item);
        }
      }
    }
  }
}
