package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import model.Question;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import view.Main;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Danny KWANT
 * @project Quizmaster
 * @created 26/06/2024 - 03:54
 */
class FillOutQuizControllerTest {

    private static FillOutQuizController controller = new FillOutQuizController();


    private int[] pointsEarned;
    private String answerPrefixA = "Antwoord A: ";
    private String answerPrefixB = "Antwoord B: ";
    private String answerPrefixC = "Antwoord C: ";
    private String answerPrefixD = "Antwoord D: ";
    private String questionDescription = "Welke bewering over machinetaal (machine language) is NIET waar:";
    private String answerA = "In machinetaal kan je programma's schrijven die op iedere willekeurige computer kunnen runnen.";
    private String answerB = "Machinetaal bestaat uit nullen (0) en enen (1).";
    private String answerC = "In machinetaal kan je algoritmes beschrijven.";
    private String answerD = "Machinetaal wordt door een compiler geproduceerd.";
    private Question question = new Question(questionDescription, answerA, answerB, answerC, answerD, null);
    private String questionAndAnswers = questionDescription + "\n\n" + answerPrefixA + answerA + "\n\n" + answerPrefixB +
            answerB + "\n\n" + answerPrefixC + answerC + "\n\n" + answerPrefixD + answerD;

    @BeforeAll
    public static void beforeAll() throws IOException {
        Platform.startup(() -> {});
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/fxml/fillOutQuiz.fxml"));
        fxmlLoader.load();
        controller = fxmlLoader.getController();
    }

    // compares given answer, stores answer in array of given answers and stores 1 point if correct or 0 if wrong in answer array
    @Test
    void checkAndStoreCorrectAnswer() {
        pointsEarned = new int[1];
        controller.setCurrentQuestionList(question);
        controller.setQuestionArea(questionAndAnswers);
        controller.setCurrentQuestionIndex(0);
        controller.setStoreAnswersSize(1);
        controller.setPointsEarned(pointsEarned);
        controller.checkAndStoreCorrectAnswer(answerPrefixA);
        // 1: checks if the answer we gave matches with what is stored in storeAnswers array
        String expectedStringAnswer = answerA;
        String actualStringAnswer = controller.getStoredAnswer();
        assertEquals(expectedStringAnswer, actualStringAnswer);
        // 2: checks if the given answer is the same as the correct answer in the question
        boolean expectedBoolAnswer = true;
        boolean actualBoolAnswer = controller.getChosenAnswerEqualsAnswerA();
        assertEquals(expectedBoolAnswer, actualBoolAnswer);
        // 3: checks if given answer was awarded +1 as a result of being correct
        int expectedIntAnswer = 1;
        int actualIntAnswer = controller.getScore();
        assertEquals(expectedIntAnswer, actualIntAnswer);
    }

    // returns sum of all 1's in pointsEarned array
    @Test
    void calculateScore() {
        pointsEarned = new int[]{1,0,1,0,1,0,1,0,0,1};
        controller.setPointsEarned(pointsEarned);
        int expectedScore = 5;
        int actualScore = controller.calculateScore();
        assertEquals(expectedScore, actualScore);

        // round 2
        pointsEarned[0] = 0;
        pointsEarned[2] = 0;
        controller.setPointsEarned(pointsEarned);
        expectedScore = 3;
        actualScore = controller.calculateScore();

        // round 3
        assertEquals(expectedScore, actualScore);
        pointsEarned = new int[]{0,0,0,0,0,0,0,0,0,0};
        controller.setPointsEarned(pointsEarned);
        expectedScore = 0;
        actualScore = controller.calculateScore();
        assertEquals(expectedScore, actualScore);

        // round 4
        pointsEarned = new int[]{1,1,1,1,1,1,1,1,1,1};
        controller.setPointsEarned(pointsEarned);
        expectedScore = 10;
        actualScore = controller.calculateScore();
        assertEquals(expectedScore, actualScore);
    }

}