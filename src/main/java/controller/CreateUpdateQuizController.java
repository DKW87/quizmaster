package controller;

import database.mysql.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Course;
import model.Difficulty;
import model.Quiz;
import view.Main;
import view.SceneManager;

import java.util.ArrayList;
import java.util.List;

public class CreateUpdateQuizController {
    private final SceneManager sceneManager = Main.getSceneManager();
    private final DBAccess dbAccess = Main.getdBaccess();
    private final QuizDAO quizDAO = new QuizDAO(dbAccess);
    private final DifficultyDAO difficultyDAO = new DifficultyDAO(dbAccess);
    private final CourseDAO courseDao = new CourseDAO(dbAccess);
    private final QuestionDAO questionDAO = new QuestionDAO(dbAccess);
    private List<Difficulty> difficulties = difficultyDAO.getAll();

    @FXML
    public TextField quizIdField;

    @FXML
    public TextField quizNameField;

    @FXML
    public ComboBox<Difficulty> difficultyComboBox;

    @FXML
    public TextField quizPassmarkField;

    @FXML
    public TextField quizPointsField;

    @FXML
    public ComboBox<Course> courseComboBox;

    @FXML
    public TextField questionsInQuizCountField;

    @FXML
    public Label errorMsg;
    public Button saveQuiz;
    public Button QuizzesList;
    public Button menu;


    private Quiz selectedQuiz;




    public void setup(Quiz quiz) {
        courseComboBox.getItems().addAll(FXCollections.observableArrayList(courseDao.getAll()));
        courseComboBox.getSelectionModel().selectedItemProperty().addListener(this::onChangeCourse);
        setDefaultQuiz(quiz);
    }

    @FXML
    public void doGoToQuizzesList(ActionEvent actionEvent) {
        sceneManager.showManageQuizScene();
    }

    @FXML
    public void doMenu(ActionEvent actionEvent) {
        sceneManager.showWelcomeScene();
    }

    public void doCreateUpdateQuiz(ActionEvent event) {
        Quiz quiz = getQuiz();
        if (quiz != null) {
            selectAction(quiz);
            Alert savedUser = new Alert(Alert.AlertType.INFORMATION);
            savedUser.setContentText("Quiz (wijzigingen) opgeslagen");
            savedUser.show();
            sceneManager.showManageQuizScene();
        }
    }

    private void setDefaultQuiz(Quiz quiz) {
        if (quiz != null) {
            selectedQuiz = quiz;
            quizIdField.setText(String.valueOf(quiz.getQuizId()));
            quizNameField.setText(quiz.getQuizName());
            difficultyComboBox.setValue(quiz.getQuizDifficulty());
            quizPassmarkField.setText(String.valueOf(quiz.getPassMark()));
            quizPointsField.setText(String.valueOf(quiz.getQuizPoints()));
            courseComboBox.setValue(quiz.getCourse());
            questionsInQuizCountField.setText(String.valueOf(quiz.getQuestionsInQuizCount()));
        } else {
            courseComboBox.getSelectionModel().selectFirst();
            var selectedCourseDifficultyId = courseComboBox.getSelectionModel().getSelectedItem().getDifficulty().getDifficultyId();
            setDifficultyComboBoxItems(selectedCourseDifficultyId);
            difficultyComboBox.getSelectionModel().selectFirst();
        }
    }

    private Quiz getQuiz() {
        String quizname = quizNameField.getText();
        // Check if name is not blank or empty
        if (quizname.isBlank() || quizname.isEmpty()) {
            errorMsg.setText("Quiz naam mag niet leeg zijn!");
            return null;
        }
        int quizId = 0;
        if (!quizIdField.getText().isEmpty()) {
            quizId = Integer.parseInt(quizIdField.getText());
        }
        Difficulty difficulty = (Difficulty) difficultyComboBox.getValue();
        int passMark = Integer.parseInt(quizPassmarkField.getText());
        int points = Integer.parseInt(quizPointsField.getText());
        Course course = (Course) courseComboBox.getValue();

        return new Quiz(quizId,quizname,passMark,points,course,difficulty);
    }

    private boolean isExistingQuiz(Quiz quiz) {return quizDAO.getByName(quiz.getQuizName()) != null;}


    private void selectAction(Quiz quiz){
        if (quiz.getQuizId() ==0 ) {
            if (isExistingQuiz(quiz)) {
                errorMsg.setText("Quiz bestaat al!");
            }
            quizDAO.storeOne(quiz);
        } else quizDAO.updateOne(quiz);
    }

    private void onChangeCourse(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
        int difficultyId = newValue.getDifficulty().getDifficultyId();
        setDifficultyComboBoxItems(difficultyId);
    }
    private void setDifficultyComboBoxItems(int difficultyId){
        List<Difficulty> filteredDifficulty = new ArrayList<>();
        for (Difficulty difficulty : difficulties) {
            if (difficultyId!=3) {
                if (difficulty.getDifficultyId() <= difficultyId) {
                    filteredDifficulty.add(difficulty);
                }
            } else {
                if (difficulty.getDifficultyId() == difficultyId ||difficulty.getDifficultyId() + 1 == difficultyId) {
                    filteredDifficulty.add(difficulty);
                }
            }
        }
        difficultyComboBox.getItems().clear();
        difficultyComboBox.getItems().addAll(FXCollections.observableArrayList(filteredDifficulty));
        difficultyComboBox.getSelectionModel().selectLast();
    }

}