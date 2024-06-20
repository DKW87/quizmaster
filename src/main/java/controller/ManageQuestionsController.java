package controller;

import database.mysql.QuestionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.Question;
import view.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageQuestionsController {

    @FXML
    ListView<Question> questionList;

    private final QuestionDAO questionDAO = new QuestionDAO(Main.getdBaccess());


    public void setup() {
        List<Question> questions = questionDAO.getAll();
        questionList.getItems().addAll(questions);
        questionList.getSelectionModel().selectFirst();
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
        if (questionList.getSelectionModel().getSelectedItem() == null) {
            noSelectionError();
        } else {
            Question question = questionList.getSelectionModel().getSelectedItem();
            Main.getSceneManager().showCreateUpdateQuestionScene(question);
        }
    }

    @FXML
    private void doDeleteQuestion() {
        if (questionList.getSelectionModel().getSelectedItem() == null) {
            noSelectionError();
        } else {
            Question question = questionList.getSelectionModel().getSelectedItem();
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDelete.setTitle("Verwijder Vraag");
            confirmDelete.setHeaderText(null);
            confirmDelete.setContentText("Weet je zeker dat je deze vraag wil verwijderen?");
            Optional<ButtonType> result = confirmDelete.showAndWait();
            if (result.get() == ButtonType.OK) {
                questionDAO.deleteOneById(question.getQuestionId());
                questionList.getItems().remove(question);
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

}
