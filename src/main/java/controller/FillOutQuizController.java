package controller;

import database.mysql.QuestionDAO;
import database.mysql.QuizResultDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    public Button buttonAnswerA;
    @FXML
    public Button buttonAnswerB;
    @FXML
    public Button buttonAnswerC;
    @FXML
    public Button buttonAnswerD;
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
    private String[] storeAnswers;

    public FillOutQuizController() {
        questionDAO = new QuestionDAO(Main.getdBaccess());
        quizResultDAO = new QuizResultDAO(Main.getdBaccess());
    }

    public void setup(Quiz quiz) {
        currentQuiz = quiz;
        questionList = new ArrayList<>(questionDAO.getAllByQuizId(quiz.getQuizId()));
        pointsEarned = new int[questionList.size()];
        storeAnswers = new String[questionList.size()];
        displayQuestionAndAnswers(questionList);
    }

    public void checkAndStoreCorrectAnswer(String answerPrefix) {
        final int BEGIN_INDEX = answerPrefix.length();
        String[] lines = questionArea.getText().split("\n");

        for (String line : lines) {
            if (line.startsWith(answerPrefix)) {
                String chosenAnswer = line.substring(BEGIN_INDEX).trim();
                storeAnswers[currentQuestionIndex] = chosenAnswer;
                if (chosenAnswer.equals(questionList.get(currentQuestionIndex).getAnswerA())) {
                    pointsEarned[currentQuestionIndex] = EEN; // one point equals correct
                } else {
                    pointsEarned[currentQuestionIndex] = NUL; // zero points equals wrong
                }
            }
        }
    }

    @FXML
    public void doRegisterA() {
        checkAndStoreCorrectAnswer(ANTWOORD_A);
        if (currentQuestionIndex == questionList.size() - EEN) {
            endOfQuizAlertAndSubmit();
        } else {
            doNextQuestion();
        }
    }

    @FXML
    public void doRegisterB() {
        checkAndStoreCorrectAnswer(ANTWOORD_B);
        if (currentQuestionIndex == questionList.size() - EEN) {
            endOfQuizAlertAndSubmit();
        } else {
            doNextQuestion();
        }
    }

    @FXML
    public void doRegisterC() {
        checkAndStoreCorrectAnswer(ANTWOORD_C);
        if (currentQuestionIndex == questionList.size() - EEN) {
            endOfQuizAlertAndSubmit();
        } else {
            doNextQuestion();
        }
    }

    @FXML
    public void doRegisterD() {
        checkAndStoreCorrectAnswer(ANTWOORD_D);
        if (currentQuestionIndex == questionList.size() - EEN) {
            endOfQuizAlertAndSubmit();
        } else {
            doNextQuestion();
        }
    }

    @FXML
    public void doNextQuestion() {
        if (currentQuestionIndex == questionList.size() - EEN) {
            markButtonAnswered();
            endOfQuizAlertAndSubmit();
        } else {
            currentQuestionIndex++;
            displayQuestionAndAnswers(questionList);
            markButtonAnswered();
        }
    }

    @FXML
    public void doPreviousQuestion() {
        if (currentQuestionIndex > NUL) {
            currentQuestionIndex--;
            displayQuestionAndAnswers(questionList);
            markButtonAnswered();
        } else {
            Util.showAlert(Alert.AlertType.ERROR, "Foutmelding", "", "Geen vorige vraag beschikbaar.");
        }
    }

    @FXML
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    public void displayQuestionAndAnswers(List<Question> questionList) {
        titleLabel.setText(String.format("Vraag %d/%d:", currentQuestionIndex + EEN, questionList.size()));
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

    private void markButtonAnswered() {
        setAllButtonsDefaultColor();
        String storedAnswer = storeAnswers[currentQuestionIndex];

        if (storedAnswer == null) return;

        String[] lines = questionArea.getText().split("\n");

        for (String line : lines) {
            checkWhichButtonHasAnswer(line, storedAnswer);
        }
    }

    private void checkWhichButtonHasAnswer(String line, String storedAnswer) {
        if (line.startsWith(ANTWOORD_A) && storedAnswer.equals(line.substring(ANTWOORD_A.length()).trim())) {
            chosenAnswerButtonColor(buttonAnswerA);
        } else if (line.startsWith(ANTWOORD_B) && storedAnswer.equals(line.substring(ANTWOORD_B.length()).trim())) {
            chosenAnswerButtonColor(buttonAnswerB);
        } else if (line.startsWith(ANTWOORD_C) && storedAnswer.equals(line.substring(ANTWOORD_C.length()).trim())) {
            chosenAnswerButtonColor(buttonAnswerC);
        } else if (line.startsWith(ANTWOORD_D) && storedAnswer.equals(line.substring(ANTWOORD_D.length()).trim())) {
            chosenAnswerButtonColor(buttonAnswerD);
        }
    }

    private void defaultButtonColor(Button button) {
        button.setStyle("-fx-background-color: #154360; -fx-border-color: #154360");
    }

    private void chosenAnswerButtonColor(Button button) {
        button.setStyle("-fx-background-color: #501560; -fx-border-color: #501560");
    }

    private void setAllButtonsDefaultColor() {
        defaultButtonColor(buttonAnswerA);
        defaultButtonColor(buttonAnswerB);
        defaultButtonColor(buttonAnswerC);
        defaultButtonColor(buttonAnswerD);
    }

} // class
