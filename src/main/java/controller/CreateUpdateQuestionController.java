package controller;

import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Question;
import model.Quiz;
import view.Main;

import java.util.Optional;

public class CreateUpdateQuestionController {

    // FXML attributes
    @FXML
    private Label headerLabel;
    @FXML
    private Label questionIdLabel;
    @FXML
    private TextArea questionDescription;
    @FXML
    private TextField answerA;
    @FXML
    private TextField answerB;
    @FXML
    private TextField answerC;
    @FXML
    private TextField answerD;
    @FXML
    private ComboBox<Quiz> quizList;

    // variables
    private final QuestionDAO questionDAO;
    private final QuizDAO quizDAO;


    public CreateUpdateQuestionController() {
        questionDAO = new QuestionDAO(Main.getdBaccess());
        quizDAO = new QuizDAO(Main.getdBaccess());
    }

    public void setup(Question question) {
        setupInit();
        if (question != null) {
            setupUpdate(question);
        } else {
            setupCreate();
        }
    }

    @FXML
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    @FXML
    public void doCreateUpdateQuestion() {
        Question question = createQuestion();
        if (question != null) {
            if (questionIdLabel.getText().isEmpty()) {
                storeQuestion(question);
            } else {
                updateQuestion(question);
            }
        }
    }

    @FXML
    public void doQuestionList() {
        Main.getSceneManager().showManageQuestionsScene();
    }

    public void setupInit() {
        quizList.getItems().addAll(quizDAO.getAll());
    }

    public void setupCreate() { headerLabel.setText("Vraag aanmaken");
    }

    public void setupUpdate(Question question) {
        headerLabel.setText("Vraag aanpassen");
        questionIdLabel.setText(String.valueOf(question.getQuestionId()));
        questionDescription.setText(question.getQuestionDescription());
        answerA.setText(question.getAnswerA());
        answerB.setText(question.getAnswerB());
        answerC.setText(question.getAnswerC());
        answerD.setText(question.getAnswerD());
        quizList.getSelectionModel().select(quizDAO.getById(question.getQuiz().getQuizId()));
        questionDescription.positionCaret(questionDescription.getLength());
    }

    private Question createQuestion() {
        String questionDescription = this.questionDescription.getText();
        String answerA = this.answerA.getText();
        String answerB = this.answerB.getText();
        String answerC = this.answerC.getText();
        String answerD = this.answerD.getText();
        Quiz quiz = this.quizList.getSelectionModel().getSelectedItem();
        boolean allFieldsFilled = checkAllFieldsFilled();

        if (!allFieldsFilled) {
            alertAllFieldsRequired();
            return null;
        } else {
            Question question = new Question(questionDescription, answerA, answerB, answerC, answerD, quiz);
            setQuestionId(question);
            return question;
        }
    }

    private void setQuestionId(Question question) {
        if (!questionIdLabel.getText().isEmpty()) {
            question.setQuestionId(Integer.parseInt(questionIdLabel.getText()));
        }
    }

    private boolean checkAllFieldsFilled() {
        String[] fields = {questionDescription.getText(), answerA.getText(), answerB.getText(), answerC.getText(),
                answerD.getText()};
        for (String string : fields) {
            if (string.isEmpty() || string.isBlank()) {
                return false;
            }
        }
        return quizList.getSelectionModel().getSelectedItem() != null;
    }

    private void storeQuestion(Question question) {
        if (isExistingQuestion(question)) {
          alertQuestionAlreadyExists();
        } else {
            questionDAO.storeOne(question);
            alertNewQuestionSaveSuccessfull();
        }
    }

    private void updateQuestion(Question question) {
        if (isExistingQuestion(question)) {
            alertQuestionAlreadyExists();
        }
        else {
            questionDAO.updateOne(question);
            alertUpdateQuestionSuccessfull();
        }
    }

    private void alertAllFieldsRequired() {
        Alert allFieldsRequired = new Alert(Alert.AlertType.ERROR);
        allFieldsRequired.setTitle("Foutmelding");
        allFieldsRequired.setHeaderText(null);
        allFieldsRequired.setContentText("Alle velden zijn verplicht!");
        allFieldsRequired.showAndWait();
    }

    private void alertQuestionAlreadyExists() {
        Alert duplicateQuestion = new Alert(Alert.AlertType.ERROR);
        duplicateQuestion.setTitle("Foutmelding");
        duplicateQuestion.setHeaderText(null);
        duplicateQuestion.setContentText("Deze vraag bestaat al! Omschrijving, vraag a en quiz moeten gezamenlijk uniek zijn!");
        duplicateQuestion.showAndWait();
    }

    private void alertNewQuestionSaveSuccessfull() {
        Alert storeQuestion = new Alert(Alert.AlertType.INFORMATION);
        storeQuestion.setTitle("Vraag Toegevoegd");
        storeQuestion.setHeaderText(null);
        storeQuestion.setContentText("Je nieuwe vraag is opgeslagen! Klik op OK om verder te gaan.");
        Optional<ButtonType> result = storeQuestion.showAndWait();
        if (result.get() == ButtonType.OK) {
            resetForm();
        }
    }

    private void alertUpdateQuestionSuccessfull() {
        Alert updateQuestion = new Alert(Alert.AlertType.INFORMATION);
        updateQuestion.setTitle("Vraag Gewijzigd");
        updateQuestion.setHeaderText(null);
        updateQuestion.setContentText("Je wijzigingen zijn opgeslagen! Klik op OK om terug naar de lijst te gaan.");
        Optional<ButtonType> result = updateQuestion.showAndWait();
        if (result.get() == ButtonType.OK) {
            Main.getSceneManager().showManageQuestionsScene();
        }
    }

    private boolean isExistingQuestion(Question question) {
        return questionDAO.checkUnique(question) != null;
    }

    private void resetForm() {
        questionDescription.clear();
        answerA.clear();
        answerB.clear();
        answerC.clear();
        answerD.clear();
        quizList.getSelectionModel().clearSelection();
    }

} // class