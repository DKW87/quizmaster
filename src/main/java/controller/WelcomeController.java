package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import model.entity.user.Coordinator;
import model.entity.user.User;
import view.Main;
import view.SceneManager;

public class WelcomeController {

  private SceneManager manager = SceneManager.getSceneManager();

  @FXML
  private Label welcomeLabel;

  @FXML
  private MenuButton taskMenuButton;

  @FXML
  private Button logoutButton;

  public void setup() {
    User currentUser = Main.getCurrentUser();
    String name = currentUser.getName();
    String role = currentUser.getRole().toString();
    welcomeLabel.setText("Welkom " + name + ". U bent ingelogd als " + role + ".");
    switch (currentUser.getRole()) {
      case STUDENT: populateStudentTasks();
      break;
      case TEACHER: populateTeacherTasks();
      break;
      case COORDINATOR: populateCoordinatorTasks();
      break;
      case ADMIN: populateAdminTasks();
      break;
      case TECHSUPPORT: populateSupportTasks();
      break;
      case CAPO: populateCapoTasks();
      break;
    }
  }

  private void populateStudentTasks() {
    MenuItem item1 = new MenuItem("Aanmelden voor cursus");
    item1.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        manager.showStudentSignInScene();
      }
    });
    taskMenuButton.getItems().add(item1);

    MenuItem item2 = new MenuItem("Afmelden voor cursus");
    item2.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        manager.showStudentSignOutScene();
      }
    });
    taskMenuButton.getItems().add(item2);
  }

  private void populateTeacherTasks() {
  }

  private void populateCoordinatorTasks() {
    MenuItem item1 = new MenuItem("Overzicht cursussen");
    item1.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        manager.showCoordinatorCourseOverview((Coordinator)Main.getCurrentUser());
      }
    });
    taskMenuButton.getItems().add(item1);
  }

  private void populateAdminTasks() {
    MenuItem item1 = new MenuItem("Beheer cursussen");
    item1.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        manager.showManageCoursesScene();
      }
    });
    taskMenuButton.getItems().add(item1);

    MenuItem item2 = new MenuItem("Beheer groepen");
    item2.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        manager.showManageGroupsScene();
      }
    });
    taskMenuButton.getItems().add(item2);
  }

  private void populateSupportTasks() {
    MenuItem item1 = new MenuItem("Maak nieuwe gebruiker");
    item1.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        manager.showNewUserScene();
      }
    });
    taskMenuButton.getItems().add(item1);

    MenuItem item2 = new MenuItem("Wijzig gebruiker");
    item2.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        manager.showSelectUserScene();
      }
    });
    taskMenuButton.getItems().add(item2);

    MenuItem item3 = new MenuItem("Verwijder gebruiker");
    item3.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        manager.showSelectUserScene();
      }
    });
    taskMenuButton.getItems().add(item3);
  }

  private void populateCapoTasks() {

  }

  public void doLogout(ActionEvent event) {
    manager.showLoginScene();
  }
}
