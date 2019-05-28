package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entity.Course;

public class NewQuizController {

  @FXML
  private Label newQuizLabel;

  @FXML
  private TextField nameField;

  @FXML
  private TextField nrOfQuestionsField;

  @FXML
  private TextField tresholdField;

  public void setup(Course course) {}

  public void doMenu(ActionEvent event) {}

  public void doNewQuiz(ActionEvent event) {}
}
