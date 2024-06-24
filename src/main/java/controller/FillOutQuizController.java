package controller;

import database.mysql.QuestionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Question;
import model.Quiz;
import view.Main;
import utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FillOutQuizController {

    @FXML
    public Label testingNumberOfPoints;
    @FXML
    private Label titleLabel;
    @FXML
    private TextArea questionArea;

    private final int NUL = 0;
    private final int EEN = 1;
    private final String ANTWOORD_A = "Antwoord A: ";
    private final String ANTWOORD_B = "Antwoord B: ";
    private final String ANTWOORD_C = "Antwoord C: ";
    private final String ANTWOORD_D = "Antwoord D: ";
    private int currentQuestionIndex = 0;
    private final QuestionDAO questionDAO;
    private List<Question> questionList;
    private int[] pointsEarned;

    public FillOutQuizController() {
        questionDAO = new QuestionDAO(Main.getdBaccess());
    }

    public void setup(Quiz quiz) {
        questionList = new ArrayList<>(questionDAO.getAllByQuizId(quiz.getQuizId()));
        pointsEarned = new int[questionList.size()];
        displayQuestionAndAnswers(questionList);
        testingNumberOfPoints.setVisible(true); // testing purposes
    }

    public void checkCorrectAnswer(String answer) {
        final int BEGIN_INDEX = 12;
        String[] lines = questionArea.getText().split("\n");

        for (String line : lines) {
            if (line.startsWith(answer)) {
                String checkAnswer = line.substring(BEGIN_INDEX).trim();
                if (checkAnswer.equals(questionList.get(currentQuestionIndex).getAnswerA())) {
                    pointsEarned[currentQuestionIndex] = EEN; // one point equals correct
                }
                else {
                    pointsEarned[currentQuestionIndex] = NUL; // zero points equals wrong
                }
            }
        }
    }

    @FXML
    public void doRegisterA() {
        checkCorrectAnswer(ANTWOORD_A);
        doNextQuestion();
    }

    @FXML
    public void doRegisterB() {
        checkCorrectAnswer(ANTWOORD_B);
        doNextQuestion();
    }

    @FXML
    public void doRegisterC() {
        checkCorrectAnswer(ANTWOORD_C);
        doNextQuestion();
    }

    @FXML
    public void doRegisterD() {
        checkCorrectAnswer(ANTWOORD_D);
        doNextQuestion();
    }

    @FXML
    public void doNextQuestion() {
        if (currentQuestionIndex == questionList.size()) {
            Util.showAlert(Alert.AlertType.ERROR, "Foutmelding", "","Geen volgende vraag beschikbaar.");
            testingNumberOfPoints();
        }
        else {
            currentQuestionIndex++;
            displayQuestionAndAnswers(questionList);
            testingNumberOfPoints();
        }
    }

    @FXML
    public void doPreviousQuestion() {
        if (currentQuestionIndex < EEN) {
            Util.showAlert(Alert.AlertType.ERROR, "Foutmelding", "","Geen vorige vraag beschikbaar.");
            testingNumberOfPoints();
        }
        else {
            currentQuestionIndex--;
            displayQuestionAndAnswers(questionList);
            testingNumberOfPoints();
        }
    }

    @FXML
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    public void displayQuestionAndAnswers(List<Question> questionList){
        titleLabel.setText(String.format("Vraag %d:", currentQuestionIndex + EEN));
        questionArea.setText(String.valueOf(questionBuilder(questionList.get(currentQuestionIndex))));
    }

    public StringBuilder questionBuilder(Question question) {
        List<String> answers = shuffleAnswers(question);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(question.getQuestionDescription()+"\n\n");
        stringBuilder.append(ANTWOORD_A + answers.get(0) + "\n\n");
        stringBuilder.append(ANTWOORD_B + answers.get(1) + "\n\n");
        stringBuilder.append(ANTWOORD_C + answers.get(2) + "\n\n");
        stringBuilder.append(ANTWOORD_D + answers.get(3));
        return stringBuilder;
    }

    private List<String> shuffleAnswers(Question question) {
        List<String> answers = new ArrayList<>();
        answers.add(question.getAnswerA());
        answers.add(question.getAnswerB());
        answers.add(question.getAnswerC());
        answers.add(question.getAnswerD());
        Collections.shuffle(answers);
        return answers;
    }

    // test method
    private void testingNumberOfPoints() {
        int correctPoints = 0;
        int incorrectPoints = 0;
        for (int i = 0; i < pointsEarned.length; i++) {

            if (pointsEarned[i] == EEN) {
                correctPoints++;
            } else if (pointsEarned[i] == NUL) {
                incorrectPoints++;
            }
        }
        testingNumberOfPoints.setText(String.format("Aantal goed: %d - Aantal fout: %d", correctPoints, incorrectPoints));
    }

} // class
