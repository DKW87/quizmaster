package controller;

import database.mysql.DBAccess;
import database.mysql.QuizResultDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.QuizResult;
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
    public TableColumn<QuizResult, String> resultColumn;
    @FXML
    public Button menuButton;
    @FXML
    public Button newQuizButton;
    @FXML
    private TableView<QuizResult> quizResultTable;
    @FXML
    private Label feedbackLabel;


    public void setup(QuizResult quizResult) {
        feedbackLabel.setText(String.format("Feedback voor quiz <%s>", quizResult.getQuiz().getQuizName()));
        List<QuizResult> quizResults = quizResultDAO.
                getStudentResultsByQuizId(quizResult.getQuiz().getQuizId(),
                        Main.getUserSession().getUser().getUserId());
        generateQuizResultTable();
        quizResultTable.getItems().addAll(quizResults);
        quizResultTable.getSelectionModel().selectFirst();
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
                new SimpleStringProperty(String.valueOf(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(cellData.getValue().getDate()))));
        scoredPointsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(getScoredPoints(cellData.getValue()))));
        resultColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(getResult(cellData.getValue()))));


    }
    public  String getResult(QuizResult quizResult) {
        return quizResult.getScore() >= quizResult.getQuiz().getQuizPoints()
                ? "Geslaagd" :
                "Niet geslaagd";
    }
    public String getScoredPoints(QuizResult quizResult) {
        return String.valueOf(quizResult.getScore() + "/" + quizResult.getQuiz().getQuizPoints());
    }

}

