package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 22 June Saturday 2024 - 22:07
 */
class QuizResultTest {
    private final Difficulty difficulty = new Difficulty("Beginner");
    private final User coordinator = new User("ekrem2",
            "password123", "Ekrem2", "SARI", new Role("Coordinator"));
    private final User student = new User("ekrem",
            "password123", "Ekrem", "SARI", new Role("Student"));
    private final Course course = new Course("TestCourse", coordinator, difficulty);
    private final Quiz quiz = new Quiz(1, "TestQuiz",5,course,difficulty,10);

    @Test
   @DisplayName("Test QuizResult Constructor and Getters")
    void testConstructorAndGetters() {
        // Arrange
        QuizResult quizResult = new QuizResult(1, student, quiz, 10);
        int defaultResultId = 1;
        int defaultStudentId = student.getUserId();
        int defaultQuizId = quiz.getQuizId();
        int defaultScore = 10;
        String defaultdate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        // Act
        int resultId = quizResult.getResultId();
        int studentId = quizResult.getStudent().getUserId();
        int quizId = quizResult.getQuiz().getQuizId();
        int score = quizResult.getScore();
        String date = quizResult.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE);

        // Assert
        assertEquals(defaultResultId, resultId);
        assertEquals(defaultStudentId, studentId);
        assertEquals(defaultQuizId, quizId);
        assertEquals(defaultScore, score);
        assertEquals(defaultdate, date);
        
    }
    @Test
    @DisplayName("Test QuizResult Setters")
    void testSetters() {
        // Arrange
        QuizResult quizResult = new QuizResult(1, student, quiz, 10);
        int defaultResultId = 1;
        int defaultStudentId = student.getUserId();
        int defaultQuizId = quiz.getQuizId();
        int defaultScore = 10;
        String defaultdate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        // Act
        quizResult.setResultId(1);
        quizResult.setStudent(student);
        quizResult.setQuiz(quiz);
        
        // Assert
        assertEquals(defaultResultId, quizResult.getResultId());
        assertEquals(defaultStudentId, quizResult.getStudent().getUserId());
        assertEquals(defaultQuizId, quizResult.getQuiz().getQuizId());
        assertEquals(defaultScore, quizResult.getScore());
        assertEquals(defaultdate, quizResult.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @Test
    @DisplayName(" Test QuizResult compareTo")
    void testCompareTo() {
        // Arrange
        QuizResult quizResult = new QuizResult(1, student, quiz, 10);
        QuizResult quizResult2 = new QuizResult(2, student, quiz, 10);
        int expected = 1;
        // Act
        int result = quizResult2.compareTo(quizResult);
        // Assert
        assertEquals(expected, result);

    }


}