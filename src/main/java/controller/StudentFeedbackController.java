package controller;

import database.couchdb.QuizResultCouchDBDAO;
import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import database.mysql.QuizResultDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Quiz;
import model.QuizResult;
import model.User;
import view.Main;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentFeedbackController {
    private final DBAccess dbAccess = Main.getdBaccess();
    private final QuizResultDAO quizResultDAO = new QuizResultDAO(dbAccess);

    @FXML
    public TableColumn<QuizResult, String> quizNameColumn;
    @FXML
    public TableColumn<QuizResult, String> completeDateColumn;
    @FXML
    public TableColumn<QuizResult, String> scoredPointsColumn;
    @FXML
    public Button menuButton;
    @FXML
    public Button newQuizButton;
    @FXML
    private TableView<QuizResult> quizResultTable;
    @FXML
    private Label feedbackLabel;
    @FXML
    private ListView<QuizResult> feedbackList;


    public void setup() {
        List<QuizResult> quizResults = quizResultDAO.getResultsByStudent(Main.getUserSession().getUser().getUserId());
        System.out.println("Student ID =    "  + Main.getUserSession().getUser().getUserId());
        System.out.println("Size of quizResults   " + quizResults.size());
        generateQuizResultTable();
        quizResultTable.getItems().addAll(quizResults);
    }

    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    public void doNewQuiz() {
        Main.getSceneManager().showSelectQuizForStudent();
    }

    // Method that fills the quizResultTable
    private void generateQuizResultTable() {
        quizNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuiz().getQuizName())));
        completeDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getDate())));
        scoredPointsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getScore())));
    }
}

