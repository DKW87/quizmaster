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


    public void setup() {
        QuizDAO quizDAO = new QuizDAO(dDacces);
        List<Quiz> quizzen = quizDAO.getAll();
        for (Quiz quiz : quizzen) {
            quizList.getItems().add(quiz);
        }
    }
    @FXML
    public void doMenu(ActionEvent actionEvent){sceneManager.showWelcomeScene();}

    public void doCreateQuiz(){}

    @FXML
    public void doUpdateQuiz(ActionEvent event){
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
        sceneManager.showCreateUpdateQuizScene(quiz);
    }

    public void doDeleteQuiz(){}
}