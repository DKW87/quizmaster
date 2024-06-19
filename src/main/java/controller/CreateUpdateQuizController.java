package controller;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import model.Quiz;
import view.Main;
import view.SceneManager;

import java.util.List;

public class CreateUpdateQuizController {
    private final QuizDAO quizDAO;
    private final SceneManager sceneManager = Main.getSceneManager();


    @FXML
   private TextField quizID;

   @FXML
   private TextField quizName;

   @FXML
   private ChoiceBox quizDifficulty;

   @FXML
   private TextField quizPassmark;

   @FXML
   private TextField quizPoints;

   @FXML
   private ChoiceBox quizCourse;

    public CreateUpdateQuizController() {this.quizDAO = new QuizDAO(Main.getdBaccess());}


    public void setup(Quiz quiz) {
        setDefaultQuiz(quiz);
    }

    @FXML
    public void doGoToQuizzesList(ActionEvent actionEvent) {sceneManager.showManageQuizScene();
    }

    @FXML
    public void doMenu(ActionEvent actionEvent){sceneManager.showWelcomeScene();}

    public void doCreateUpdateQuiz() {
    }

    private void setDefaultQuiz(Quiz quiz) {
        if (quiz != null) {
            quizID.setText(String.valueOf(quiz.getQuizId()));
            quizName.setText(quiz.getQuizName());
            quizDifficulty.setValue(quiz.getQuizDifficulty());
            quizPassmark.setText(String.valueOf(quiz.getPassMark()));
            quizPoints.setText(String.valueOf(quiz.getQuizPoints()));
            quizCourse.setValue(quiz.getCourse());
        }
    }
}