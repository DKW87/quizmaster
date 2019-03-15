package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.entity.user.User;

public class CoordinatorCourseOverviewController {

  @FXML
  Button menuButton;

  @FXML
  Button manageQuizButton;

  @FXML
  ListView<String> courseList;

  public void setup(User user) {}

  public void doMenu(ActionEvent event) {}

  public void doManageQuiz(ActionEvent event) {}
}
