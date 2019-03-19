package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.entity.Quiz;

public class QuestionOverviewController {

  public void setup(Quiz quiz) {}

  @FXML
  Label quizLabel;

  @FXML
  Button menuButton;

  @FXML
  Button editQuestionButton;

  @FXML
  Button newQuestionButton;

  @FXML
  Button deleteQuestionButton;

  @FXML
  Button selectQuizButton;

  @FXML
  ListView<String> questionList;

  public void doMenu(ActionEvent event) {}

  public void doEditQuestion(ActionEvent event) {}

  public void doCreateQuestion(ActionEvent event) {}

  public void doDeleteQuestion(ActionEvent event) {}

  public void doSelectQuiz(ActionEvent event) {}
}
