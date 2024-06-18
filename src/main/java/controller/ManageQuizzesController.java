package controller;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Quiz;
import view.Main;
import view.SceneManager;

import java.util.List;


public class ManageQuizzesController {
    private final DBAccess dDacces;
    private final SceneManager sceneManager = Main.getSceneManager();

    @FXML
    ListView<Quiz> quizList;

    @FXML
    TextField waarschuwingTextField;
    public ManageQuizzesController() {this.dDacces = Main.getdBaccess();}

    // setup bij openen van het QuizList scherm waarbij de bestaande Quizes uit de DB worden gehaald.
    public void setup() {
        QuizDAO quizDAO = new QuizDAO(dDacces);
        List<Quiz> quizzen = quizDAO.getAll();
        for (Quiz quiz : quizzen) {
            quizList.getItems().add(quiz);
        }
    }
    @FXML
    public void doMenu(ActionEvent actionEvent){sceneManager.showWelcomeScene();}

    @FXML
    public void doCreateQuiz(ActionEvent event){sceneManager.showCreateUpdateQuizScene(null);}

    @FXML
    public void doUpdateQuiz(ActionEvent event){
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
        sceneManager.showCreateUpdateQuizScene(quiz);
    }

    @FXML
    public void doDeleteQuiz(ActionEvent event){
        QuizDAO quizDAO = new QuizDAO(dDacces);
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
        quizList.getItems().remove(quiz);
        quizDAO.deleteOneById(quiz.getQuizId());

    }
}