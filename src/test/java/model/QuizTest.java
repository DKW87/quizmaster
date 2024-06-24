package model;

import org.junit.jupiter.api.Test;

/**
 * @author Rob Jansen
 * @project quizmaster
 * @created 24 juni 2024 - 13:50
 */

import static org.junit.jupiter.api.Assertions.*;

public class QuizTest {
    @Test
    void testConstructorEnGetters(){
        Role testRole = new Role("Student");
        User testUser = new User("testUserName","1234","TestRob","TestJansen",testRole);
        Difficulty testDifficulty = new Difficulty("Beginner");
        Course testCourse = new Course("testCourseName",testUser,testDifficulty);
        Quiz testQuiz = new Quiz(1,"testQuiz",10,testCourse,testDifficulty);

        assertEquals(1,testQuiz.getQuizId());
        assertEquals("testQuiz",testQuiz.getQuizName());
        assertEquals(10,testQuiz.getQuizPoints());
        assertEquals(testCourse,testQuiz.getCourse());
        assertEquals(testDifficulty, testQuiz.getQuizDifficulty());

    }
}