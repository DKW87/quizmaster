package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 22 June Saturday 2024 - 20:06
 */

class CourseTest {

    private final Difficulty difficulty = new Difficulty("Beginner");
    private final User coordinator = new User("ekrem",
            "password123", "Ekrem", "SARI", new Role("Coordinator"));

    @Test
    @DisplayName("Test Course Constructor and Getters")
    void testConstructorAndGetters() {
        // Arrange
        Course course = new Course("TestCourse", coordinator, difficulty);
        int defaultCourseId = 0;
        int defaultStudentCount = 0;
        int courseDifficultyId = difficulty.getDifficultyId();
        String courseName = "TestCourse";
        int courseCoordinatorId = coordinator.getUserId();
        // Act
        int courseId = course.getCourseId();
        String name = course.getName();
        int difficultyId = course.getDifficulty().getDifficultyId();
        int studentCount = course.getStudentCount();
        int coordinatorId = course.getCoordinator().getUserId();
        // Assert
        assertEquals(defaultCourseId, courseId); // Default course ID
        assertEquals(courseName, name);
        assertEquals(courseDifficultyId, difficultyId);
        assertEquals(courseCoordinatorId, coordinatorId);
        assertEquals(defaultStudentCount, studentCount);
    }
    @Test
    @DisplayName("Test Course Setters")
    void testSetters() {
        // Arrange
        Course course = new Course("TestCourse", coordinator, difficulty);
        int defaultCourseId = 1;
        int defaultStudentCount = 10;

        // Act
        course.setCourseId(1);
        course.setStudentCount(10);
        // Assert
        assertEquals(defaultCourseId, course.getCourseId());
        assertEquals(defaultStudentCount, course.getStudentCount());
    }

    @Test
    @DisplayName("Test Course to String")
    void testToString() {
        // Arrange
        Course course = new Course("TestCourse", coordinator, difficulty);
        // Act
        String courseString = course.toString();
        // Assert
        assertEquals("TestCourse", courseString);
    }

  
}