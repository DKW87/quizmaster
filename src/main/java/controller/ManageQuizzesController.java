package controller;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Quiz;
import model.UserSession;
import utils.Util;
import view.Main;
import view.SceneManager;

import java.util.List;
import java.util.Optional;

/**
 * @author Rob Jansen
 * @project quizmaster
 * @created 18 juni 2024 - 10:00
 */


public class ManageQuizzesController {
    private final DBAccess dDacces = Main.getdBaccess();
    private final SceneManager sceneManager = Main.getSceneManager();
    QuizDAO quizDAO = new QuizDAO(dDacces);
    private final UserSession userSession = Main.getUserSession();
    private int logedinUser;

    @FXML
    public TableView<Quiz> quizTable;
    @FXML
    public TableColumn<Quiz, String> nameColumn;
    @FXML
    public TableColumn<Quiz, String> courseColumn;
    @FXML
    public TableColumn<Quiz,String> difficultyColumn;
    @FXML
    public TableColumn<Quiz, String> passMarkColumn;
    @FXML
    public TableColumn<Quiz, String> numberQuestionsColumn;



//    @FXML
//    TextField errorfield; // nog toe te voegen



    // setup bij openen van het QuizList scherm waarbij de bestaande Quizes uit de DB worden gehaald.
    public void setup() {

        List<Quiz> quizzen = quizDAO.getAll();
        for (Quiz quiz : quizzen) {
            quizTable.getItems().add(quiz);
        }
        generateQuizTable();
        logedinUser = userSession.getUser().getUserId();
        System.out.println(logedinUser);

    }
    @FXML
    public void doMenu(ActionEvent actionEvent){sceneManager.showWelcomeScene();}

    @FXML
    public void doCreateQuiz(ActionEvent event){sceneManager.showCreateUpdateQuizScene(null);}
    // TO DO: eerst een nieuwe quiz in de DB creeren, daarvan id ophalen? en daarmee een nieuwe Scene oproepen met input de nieuwe quiz?


    @FXML
    public void doUpdateQuiz(){
        // voor als je iets wilt gebruiken van de SELECTIE uit een lijst
        Quiz selectedQuiz = quizTable.getSelectionModel().getSelectedItem();
        if (selectedQuiz == null) {
            Util.showAlert(Alert.AlertType.ERROR, "Foutmelding", "Selecteer eerst een quiz!");
        } else {
                sceneManager.showCreateUpdateQuizScene(selectedQuiz);
            }
        }

    @FXML
    public void doDeleteQuiz(ActionEvent event){
        // voor als je iets wilt gebruiken van de SELECTIE uit een lijst
        Quiz quiz = quizTable.getSelectionModel().getSelectedItem();
        if (quiz != null){
            String message = String.format("Weet je zeker dat je de %s wilt verwijderen?",quiz.getQuizName());
            if (Util.confirmMessage("Delete Quiz",message)) {
                quizTable.getItems().remove(quiz);
                quizDAO.deleteOneById(quiz.getQuizId());
            }
        } else {
            Util.showAlert(Alert.AlertType.ERROR, "Foutmelding", "Selecteer eerst een quiz!");
        }
    }

    private void generateQuizTable() {
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuizName())));
        courseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCourse().getName()));
        difficultyColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getQuizDifficulty().getName()));
        numberQuestionsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuestionsInQuizCount())));
        passMarkColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuizPoints())));
        quizTable.getSelectionModel().selectFirst();
    }
}