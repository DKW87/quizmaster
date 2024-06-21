package controller;

import database.mysql.QuestionDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Question;
import view.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageQuestionsController {

    @FXML
    public TableView<Question> questionsTable;

    @FXML
    public TableColumn<Question, String> questionDescriptionColumn;

    @FXML
    public TableColumn<Question, String> answerAColumn;

    @FXML
    public TableColumn<Question, String> answerBColumn;

    @FXML
    public TableColumn<Question, String> answerCColumn;

    @FXML
    public TableColumn<Question, String> answerDColumn;

    @FXML
    public TableColumn<Question, String> partOfQuizColumn;

    @FXML
    public TableColumn<Question, Integer> questionsCounterColumn;

    private final QuestionDAO questionDAO = new QuestionDAO(Main.getdBaccess());


    public void setup() {
        List<Question> questions = questionDAO.getAll();
        questionsTable.getItems().addAll(questions);
        generateQuestionsTable();
    }

    @FXML
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    @FXML
    public void doCreateQuestion() {
        Main.getSceneManager().showCreateUpdateQuestionScene(null);
    }

    @FXML
    private void doUpdateQuestion() {
        if (questionsTable.getSelectionModel().getSelectedItem() == null) {
            noSelectionError();
        } else {
            Question question = (Question) questionsTable.getSelectionModel().getSelectedItem();
            Main.getSceneManager().showCreateUpdateQuestionScene(question);
        }
    }

    @FXML
    private void doDeleteQuestion() {
        if (questionsTable.getSelectionModel().getSelectedItem() == null) {
            noSelectionError();
        } else {
            Question question = (Question) questionsTable.getSelectionModel().getSelectedItem();
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDelete.setTitle("Verwijder Vraag");
            confirmDelete.setHeaderText(null);
            confirmDelete.setContentText("Weet je zeker dat je deze vraag wil verwijderen?");
            Optional<ButtonType> result = confirmDelete.showAndWait();
            if (result.get() == ButtonType.OK) {
                questionDAO.deleteOneById(question.getQuestionId());
                questionsTable.getItems().remove(question);
            }
        }
    }

    private void noSelectionError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Foutmelding");
        alert.setHeaderText(null);
        alert.setContentText("Je moet eerst een vraag selecteren.");
        alert.showAndWait();
    }

    private void generateQuestionsTable() {
        questionDescriptionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuestionDescription())));
        answerAColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAnswerA()));
        answerBColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAnswerB()));
        answerCColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAnswerC()));
        answerDColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAnswerD()));
        partOfQuizColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getQuiz().getQuizName()));
        questionsTable.getSelectionModel().selectFirst();
    }

}
