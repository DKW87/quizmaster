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

import static utils.Util.showAlert;

/**
 * @author Rob Jansen
 * @project quizmaster
 * @created 17 juni 2024 - 10:00
 */

public class CreateUpdateQuizController {
    private final SceneManager sceneManager = Main.getSceneManager();
    private final DBAccess dbAccess = Main.getdBaccess();
    private final QuizDAO quizDAO = new QuizDAO(dbAccess);
    private final DifficultyDAO difficultyDAO = new DifficultyDAO(dbAccess);
    private final CourseDAO courseDao = new CourseDAO(dbAccess);
    private final QuestionDAO questionDAO = new QuestionDAO(dbAccess);
    private final List<Difficulty> difficulties = difficultyDAO.getAll();

    @FXML
    public TextField quizIdField;

    @FXML
    public TextField quizNameField;

    @FXML
    public ComboBox<Difficulty> difficultyComboBox;


    @FXML
    public TextField quizPointsField;

    @FXML
    public ComboBox<Course> courseComboBox;

    @FXML
    public TextField questionsInQuizCountField;

    public Button saveQuiz;
    public Button QuizzesList;
    public Button menu;
    public TextField quizCesuurPercentageField;


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
        }
    }

    private void setDefaultQuiz(Quiz quiz) {


        if (quiz != null) {
            double quizCesuurPercentage=0.0;
            final double MINIMUM_CESUUR_PERCENTAGE = 50.0;
            if (quiz.getQuestionsInQuizCount()!=0){
                quizCesuurPercentage = (double) Math.round((((double) quiz.getQuizPoints() / (double) quiz.getQuestionsInQuizCount()) * 100.0) * 10) /10; // om het percentage van aantal benodigde goede vragen (Cesuur) t.o.v. aantal beschikbare vragen in Quiz te laten zien
            }
            if (quizCesuurPercentage ==0.0){
                String messageGeenVragen = String.format("LET OP: Voor deze Quiz zijn nog geen vragen aangemaakt");
                showAlert(Alert.AlertType.INFORMATION, "Geen vragen", messageGeenVragen);
            }

            if (quizCesuurPercentage >0.0 & quizCesuurPercentage <MINIMUM_CESUUR_PERCENTAGE){
                String messageCesuur = String.format("LET OP: Het benodigde aantal goede vragen t.o.v. totaal aantal vragen in de Quiz is KLEINER dan %.0f procent",MINIMUM_CESUUR_PERCENTAGE);
                showAlert(Alert.AlertType.INFORMATION, "Cesuur Klein", messageCesuur);
            }

            selectedQuiz = quiz;
            quizIdField.setText(String.valueOf(quiz.getQuizId()));
            quizNameField.setText(quiz.getQuizName());
            difficultyComboBox.setValue(quiz.getQuizDifficulty());
            quizPointsField.setText(String.valueOf(quiz.getQuizPoints()));
            courseComboBox.setValue(quiz.getCourse());
            questionsInQuizCountField.setText(String.valueOf(quiz.getQuestionsInQuizCount()));
            quizCesuurPercentageField.setText(String.valueOf(quizCesuurPercentage + " %"));
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
            showAlert(Alert.AlertType.ERROR, "Fout", "Quiz naam mag niet leeg zijn!");
            return null;
        }
        int quizId = 0;
        if (!quizIdField.getText().isEmpty()) {
            quizId = Integer.parseInt(quizIdField.getText());
        }
        Difficulty difficulty = (Difficulty) difficultyComboBox.getValue();
        int points = quizPointsField.getText().isEmpty() ? 0 : Integer.parseInt(quizPointsField.getText());
        Course course = (Course) courseComboBox.getValue();

        return new Quiz(quizId,quizname,points,course,difficulty);
    }

    // check of Quiz al voorkomt in de DB (check op naam van de Quiz)
    private boolean isExistingQuiz(Quiz quiz) {return quizDAO.getByName(quiz.getQuizName()) != null;}


    private void selectAction(Quiz quiz){
        if (quiz.getQuizId() ==0 ) {
            if (isExistingQuiz(quiz)) {
                showAlert(Alert.AlertType.ERROR, "Fout", "Quiznaam bestaat al!");
                return;
            }
            quizDAO.storeOne(quiz);
            resetForm();
        } else {
            quizDAO.updateOne(quiz);
            sceneManager.showManageQuizScene();
        }
        // Display success message
        showAlert(Alert.AlertType.INFORMATION, "Succes", "Quiz (wijzigingen) opgeslagen");
    }

    // methode (Observable) om bij te houden of de selectieBox van Course in het scherm wijzigt
    // roept methode setDifficultyComboBoxItems aan met de nieuwe verkregen DifficultyId van Course
    private void onChangeCourse(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
        int difficultyId = newValue.getDifficulty().getDifficultyId();
        setDifficultyComboBoxItems(difficultyId);
    }

    // methode om de Quiz Difficulty ComboBox te vullen
    // 3 levels van Difficulty (1: Beginner, 2: Medium, 3: Gevorderd)
    // in Quiz Difficulty Combobox difficulty gelijk aan of 1 lager (indien van toepassing) t.o.v. (nieuw) verkregen Course Difficulty
    // for loop loopt effectief van 1 (beginner) tm 3 (gevorderd). Indien Course Difficulty = 3, dan Quiz difficulty 3 en 2 // Course difficulty =2, dan Quiz difficulty 2 en 1 // Course difficulty =1 dan Quiz difficulty =1;

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

    }

    private void resetForm() {
        quizNameField.clear();
//        courseComboBox.getSelectionModel().clearSelection();
        difficultyComboBox.getSelectionModel().clearSelection();
        questionsInQuizCountField.clear();
        quizPointsField.clear();
    }





}