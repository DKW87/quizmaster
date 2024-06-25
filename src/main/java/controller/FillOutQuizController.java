package controller;

import database.mysql.QuestionDAO;
import database.mysql.QuizResultDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Question;
import model.Quiz;
import model.QuizResult;
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
    private final QuizResultDAO quizResultDAO;
    private Quiz currentQuiz;
    private List<Question> questionList;
    private int[] pointsEarned;

    public FillOutQuizController() {
        questionDAO = new QuestionDAO(Main.getdBaccess());
        quizResultDAO = new QuizResultDAO(Main.getdBaccess());
    }

    public void setup(Quiz quiz) {
        currentQuiz = quiz;
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
                } else {
                    pointsEarned[currentQuestionIndex] = NUL; // zero points equals wrong
                }
            }
        }
    }

    @FXML
    public void doRegisterA() {
        checkCorrectAnswer(ANTWOORD_A);
        if (currentQuestionIndex == questionList.size() - EEN) {
            endOfQuizAlertAndSubmit();
        }
        else {
            doNextQuestion();
        }
    }

    @FXML
    public void doRegisterB() {
        checkCorrectAnswer(ANTWOORD_B);
        if (currentQuestionIndex == questionList.size() - EEN) {
            endOfQuizAlertAndSubmit();
        }
        else {
            doNextQuestion();
        }
    }

    @FXML
    public void doRegisterC() {
        checkCorrectAnswer(ANTWOORD_C);
        if (currentQuestionIndex == questionList.size() - EEN) {
            endOfQuizAlertAndSubmit();
        }
        else {
            doNextQuestion();
        }
    }

    @FXML
    public void doRegisterD() {
        checkCorrectAnswer(ANTWOORD_D);
        if (currentQuestionIndex == questionList.size() - EEN) {
            endOfQuizAlertAndSubmit();
        }
        else {
            doNextQuestion();
        }
    }

    @FXML
    public void doNextQuestion() {
        if (currentQuestionIndex == questionList.size() - EEN) {
            endOfQuizAlertAndSubmit();
        } else {
            currentQuestionIndex++;
            displayQuestionAndAnswers(questionList);
        }
    }

    @FXML
    public void doPreviousQuestion() {
        if (currentQuestionIndex < EEN) {
            Util.showAlert(Alert.AlertType.ERROR, "Foutmelding", "", "Geen vorige vraag beschikbaar.");
        } else {
            currentQuestionIndex--;
            displayQuestionAndAnswers(questionList);
        }
    }

    @FXML
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    public void displayQuestionAndAnswers(List<Question> questionList) {
        titleLabel.setText(String.format("Vraag %d:", currentQuestionIndex + EEN));
        questionArea.setText(String.valueOf(questionBuilder(questionList.get(currentQuestionIndex))));
    }

    public StringBuilder questionBuilder(Question question) {
        List<String> answers = shuffleAnswers(question);
        StringBuilder stringBuilder = new StringBuilder();
        String[] labels = {ANTWOORD_A, ANTWOORD_B, ANTWOORD_C, ANTWOORD_D};

        stringBuilder.append(question.getQuestionDescription() + "\n\n");
        for (int i = 0; i < answers.size(); i++) {
            stringBuilder.append(labels[i] + answers.get(i) + "\n\n");
        }
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

    public void endOfQuizAlertAndSubmit() {
        Alert submitQuiz = new Alert(Alert.AlertType.CONFIRMATION);
        submitQuiz.setTitle("Quiz beëindigen?");
        submitQuiz.setHeaderText(null);
        submitQuiz.setContentText("Druk op OK om de quiz te beëindigen en de resultaten te versturen," +
                " of kies Cancel om verder met de quiz te gaan.");
        submitQuiz.showAndWait();
        if (submitQuiz.getResult() == ButtonType.OK) {
            QuizResult quizResult = new QuizResult(0, Main.getUserSession().getUser(), currentQuiz, calculateScore());
            quizResultDAO.storeOne(quizResult);
            Main.getSceneManager().showStudentFeedback(quizResult);
        }
    }

    private int calculateScore() {
        int correctPoints = 0;

        for (int i = 0; i < pointsEarned.length; i++) {
            if (pointsEarned[i] == EEN) {
                correctPoints++;
            }
        }
        return correctPoints;
    }

} // class
