package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.entity.user.User;

public class Main extends Application {

  private static SceneManager sceneManager = null;
  private static Stage primaryStage = null;

  private static User currentUser;

  public static SceneManager getSceneManager() {
    if (sceneManager == null) {
      sceneManager = new SceneManager(primaryStage);
    }
    return sceneManager;
  }

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Main.primaryStage = primaryStage;
    primaryStage.setTitle("Make IT Work - Project 1");
    getSceneManager().setWindowTool();
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  public static User getCurrentUser() {
    return currentUser;
  }

  public static void setCurrentUser(User user) {
    System.out.println("Current user: " + user);
    currentUser = user;
  }
}
