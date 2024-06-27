package controller;

import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Question;
import model.Quiz;
import view.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageQuestionsController {

    // FXML attributes
    @FXML
    public TableView<Question> questionsTable;
    @FXML
    public TableColumn<Question, String> questionDescriptionColumn;
    @FXML
    public TableColumn<Question, String> answerAColumn;
    @FXML
    public TableColumn<Question, String> partOfQuizColumn;
    @FXML
    public TableColumn<Question, Integer> questionsCounterColumn;

    // variables
    private final QuestionDAO questionDAO;
    private final QuizDAO quizDAO;

    public ManageQuestionsController() {
        questionDAO = new QuestionDAO(Main.getdBaccess());
        quizDAO = new QuizDAO(Main.getdBaccess());
    }

    public void setup() {
        List<Quiz> quizzesByCoordinator = new ArrayList<>(quizDAO.getAllQuizzesByCoordinator(Main.getUserSession().getUser().getUserId()));
        List<Question> questions = new ArrayList<>();
        for (Quiz quiz : quizzesByCoordinator) {
            List<Question> quizQuestions = questionDAO.getAllByQuizId(quiz.getQuizId());
            questions.addAll(quizQuestions);
        }
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
            confirmAndDelete(question);
        }
    }

    private void confirmAndDelete(Question question) {
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
        partOfQuizColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getQuiz().getQuizName()));
        questionsCounterColumn.setCellValueFactory(celldata -> {
                    int count = questionDAO.countQuestionsInQuiz(celldata.getValue().getQuiz().getQuizId());
                    return new SimpleObjectProperty<>(count);
                });
        questionsTable.getSelectionModel().selectFirst();
    }

} // class
