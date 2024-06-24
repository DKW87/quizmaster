package database.mysql;

import model.Course;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.Main;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 22 June Saturday 2024 - 23:33
 */
class CourseDAOTest {

    private static final DBAccess dbAccess = Main.getdBaccess();
    private final UserDAO userDAO = new UserDAO(dbAccess);
    private static final CourseDAO courseDAO = new CourseDAO(dbAccess);
    private final DifficultyDAO difficultyDAO = new DifficultyDAO(dbAccess);

    private static int defaultCourseCount;

    @BeforeAll
    static void beforeAll() {
        defaultCourseCount = courseDAO.getAll().size();
    }

    @Test
    @DisplayName("Test CourseDAO storeOne and bulkCreate")
    void storeOneTest() throws SQLException {
        List<Course> courses = new ArrayList<>();
        // Arrange
        Course course = new Course("TestCourse-" + LocalDateTime.now(), userDAO.getById(2),difficultyDAO.getById(1)) ;
        courses.add(course);

        int defaultCoursesSize = courseDAO.getAll().size();
        // Act
        courseDAO.bulkCreate(courses);
        // Assert
        List<Course> allCourses = courseDAO.getAll();
        assertEquals(defaultCoursesSize + 1, allCourses.size());
    }
    @Test
    @DisplayName("Test CourseDAO storeOne check unique name")
    void storeOneUniqueNameTest()  {
        // Arrange
        Course course = new Course("TestCourse-2", userDAO.getById(2),difficultyDAO.getById(1)) ;
        // Act
        // assertThrows
        Exception exception = assertThrows(RuntimeException.class, () -> {
            courseDAO.storeOne(course);
        });

        // Assert
        String expectedMessage = "Duplicate entry  " + course.getName() + " for key 'course.name_UNIQUE'";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Test CourseDAO deleteOneById")
    void deleteOneById() {
        Course course = new Course("TestCourse-12", userDAO.getById(2),difficultyDAO.getById(1)) ;
        courseDAO.storeOne(course);
        Course testCourse = courseDAO.getByName("TestCourse-12");
        // Act
        courseDAO.deleteOneById(testCourse.getCourseId());
        boolean result = courseDAO.getById(testCourse.getCourseId()) == null;
        // Assert
        assertTrue(result);

    }@Test
    @DisplayName("Test CourseDAO deleteOneById with parent key")
    void deleteOneByIdUnSuccess() {
        Course testCourse = courseDAO.getById(1);
        // Act
        // assertThrows
        Exception exception = assertThrows(RuntimeException.class, () -> {
            courseDAO.deleteOneById(testCourse.getCourseId());;
        });

        // Assert
        String expectedMessage = "Cannot delete or update a parent row: a foreign key constraint fails";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

    }



    @Test
    @DisplayName("Test CourseDAO getById Success")
    void getByIdSuccess() {
        int courseId = 1;
        int nullCourseId = 0;
        int expected = 1;
        Course course = courseDAO.getById(courseId);
        Course nullCourse = courseDAO.getById(nullCourseId);
        int actual = course.getCourseId();
        assertNotNull(course);
        assertNull(nullCourse);
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Test CourseDAO getByName Success")
    void getByNameSuccess() {
        String courseName = "TestCourse-2";
        String nullCourseName = "TestCourse-0";
        Course course = courseDAO.getByName(courseName);
        Course nullCourse = courseDAO.getByName(nullCourseName);
        assertNotNull(course);
        assertNull(nullCourse);
    }



    @Test
    @DisplayName("Test CourseDAO updateOne")
    public void testUpdateOne()  {

        Course testCourse = new Course(
                "Test Course Update Name", userDAO.getById(2), difficultyDAO.getById(1));

        testCourse.setCourseId(1);

        courseDAO.updateOne(testCourse);

        // Example code to query the database and compare the values
        Course updatedCourse = courseDAO.getById(testCourse.getCourseId());
        assertEquals(testCourse.getName(), updatedCourse.getName());
        assertEquals(testCourse.getDifficulty().getDifficultyId(), updatedCourse.getDifficulty().getDifficultyId());
        assertEquals(testCourse.getCoordinator().getUserId(), updatedCourse.getCoordinator().getUserId());
    }

    @Test
    @DisplayName("Test CourseDAO addStudentToCourse")
    void addStudentToCourseTest() {
        // Arrange
        Course course = courseDAO.getById(1);
        int defaultStudentCount = course.getStudentCount();
        // Act
        courseDAO.addStudentToCourse(1,1);
        // Assert
        int actual = courseDAO.getCourseByStudentCount(1);
        assertEquals(defaultStudentCount + 1, actual);
    }

    @Test
    @DisplayName("Test CourseDAO getUnAssignedCoursesByStudent")
    void getUnAssignedCoursesByStudentTest() {
        // Arrange
        int allCoursesCount = courseDAO.getAll().size();
        int assignedCoursesCount = courseDAO.getCoursesByStudent(1).size();
        int expected = allCoursesCount - assignedCoursesCount;
        int actual = courseDAO.getUnAssignedCoursesByStudent(1).size();
        // Assert
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Test CourseDAO addStudentToCourse")
    void removeStudentToCourseTest() {
        // Arrange
        Course course = courseDAO.getById(1);
        int defaultStudentCount = course.getStudentCount();
        // Act
        int studentCourseId = courseDAO.getCoursesByStudent(1).get(0).getStudentCourseId();
        courseDAO.removeStudentFromCourse(studentCourseId);
        // Assert
        int actual = courseDAO.getCourseByStudentCount(1);
        assertEquals(defaultStudentCount , actual);
    }

    @Test
    @DisplayName("Test CourseDAO getAll")
    void getAll() {
        int expected = defaultCourseCount;
        int actual = courseDAO.getAll().size();
        assertEquals(expected, actual);
    }


}