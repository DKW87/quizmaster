package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.entity.Quiz;

public class QuizOverviewController {

  @FXML
  Label courseLabel;

  @FXML
  Button menuButton;

  @FXML
  Button editQuestionsButton;

  @FXML
  Button newQuizButton;

  @FXML
  Button deleteQuizButton;

  @FXML
  Button selectCourseButton;

  @FXML
  ListView<String> quizList;

  public void setup(Quiz quiz) {}

  public void doMenu(ActionEvent event) {}

  public void doCreateQuiz(ActionEvent event) {}

  public void doDeleteQuiz(ActionEvent event) {}

  public void doSelectCourse(ActionEvent event) {}
}
