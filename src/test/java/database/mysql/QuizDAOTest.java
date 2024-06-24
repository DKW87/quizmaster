package database.mysql;

import org.junit.jupiter.api.Test;
import view.Main;

import static org.junit.jupiter.api.Assertions.*;

public class QuizDAOTest {
    private final DBAccess dDacces = Main.getdBaccess();
    QuizDAO quizDAOTest = new QuizDAO(dDacces);

    @Test
    public void testGetNumberOfQuestionsInQuizFromDB() {
        int quizIdTest = 1;
        int numberOfQuestionsTest = quizDAOTest.getQuestionsInQuizCount(quizIdTest);
        assertEquals(11, numberOfQuestionsTest);
    }

}