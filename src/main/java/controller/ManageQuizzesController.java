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
    private final DBAccess dDacces = Main.getdBaccess();
    private final SceneManager sceneManager = Main.getSceneManager();
    QuizDAO quizDAO = new QuizDAO(dDacces);

    @FXML
    ListView<Quiz> quizList;

    @FXML
    TextField errorfield; // nog toe te voegen



    // setup bij openen van het QuizList scherm waarbij de bestaande Quizes uit de DB worden gehaald.
    public void setup() {

        List<Quiz> quizzen = quizDAO.getAll();
        for (Quiz quiz : quizzen) {
            quizList.getItems().add(quiz);
        }
    }
    @FXML
    public void doMenu(ActionEvent actionEvent){sceneManager.showWelcomeScene();}

    @FXML
    public void doCreateQuiz(ActionEvent event){sceneManager.showCreateUpdateQuizScene(null);}
    // TO DO: eerst een nieuwe quiz in de DB creeren, daarvan id ophalen? en daarmee een nieuwe Scene oproepen met input de nieuwe quiz?


    @FXML
    public void doUpdateQuiz(ActionEvent event){
        // voor als je iets wilt gebruiken van de SELECTIE uit een lijst
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
        sceneManager.showCreateUpdateQuizScene(quiz);
    }

    @FXML
    public void doDeleteQuiz(ActionEvent event){
        // voor als je iets wilt gebruiken van de SELECTIE uit een lijst
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
        quizList.getItems().remove(quiz);
        quizDAO.deleteOneById(quiz.getQuizId());

    }
}