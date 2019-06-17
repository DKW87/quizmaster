package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class CoordinatorDashboardController {

  @FXML
  private Button menuButton;

  @FXML
  private Button newCourseButton;

  @FXML
  private Button editCourseButton;

  @FXML
  private Button newQuizButton;

  @FXML
  private Button editQuizButton;

  @FXML
  private Button newQuestionButton;

  @FXML
  private Button editQuestionButton;

  @FXML
  private ListView<String> courseList;

  @FXML
  private ListView<String> quizList;

  @FXML
  private ListView<String> questionList;

  public void setup() {
    courseList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        System.out.println("Selected item in courseList: " + observableValue + ", " + s + ", " + t1);
      }
    });

    quizList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        System.out.println("Selected item in quizList: " + observableValue + ", " + s + ", " + t1);
      }
    });

  }

  public void doMenu() {}

  public void doNewCourse() {}

  public void doEditCourse() {}

  public void doNewQuiz() {}

  public void doEditQuiz() {}

  public void doNewQuestion() {}

  public void doEditQuestion() {}
}
