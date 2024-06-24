package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.DifficultyDAO;
import database.mysql.UserDAO;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import model.Course;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 23 June Sunday 2024 - 16:35
 */
class ManageCoursesControllerTest {
    private static final DBAccess dbAccess = Main.getdBaccess();
    private static final CourseDAO courseDAO = new CourseDAO(dbAccess);
    private static final UserDAO userDAO = new UserDAO(dbAccess);
    private static final DifficultyDAO difficultyDAO = new DifficultyDAO(dbAccess);
    private static ManageCoursesController manageCoursesController;

    private static Course defaultCourse ;


    @BeforeAll
    public static void beforeAll() throws SQLException, IOException {
        Platform.startup(() -> {
        });
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/fxml/manageCourses.fxml"));
         fxmlLoader.load();
        manageCoursesController = fxmlLoader.getController();
        manageCoursesController.setup();
        defaultCourse = new Course("Test Default Course" + LocalDateTime.now(), userDAO.getById(1), difficultyDAO.getById(1));
        courseDAO.storeOne(defaultCourse);
    }

    @Test
    @DisplayName("Test CoursManagerController doDeleteCourse with no parent key")
    void doDeleteCourseSuccess() {
        Course course = courseDAO.getByName(defaultCourse.getName());
        int tableCoursesCount = manageCoursesController.courseTable.getItems().size();
        manageCoursesController.setSelectedCourse(course);
        manageCoursesController.doDeleteCourse();
        int coursesCount = courseDAO.getAll().size();
        assertEquals(tableCoursesCount , coursesCount);
    }
    @Test
    @DisplayName("Test CoursManagerController doDeleteCourse with  parent key")
    void doDeleteCourseUnSuccess() {
        Course course = courseDAO.getById(1);
        manageCoursesController.setSelectedCourse(course);
        // Act
        // assertThrows
        Exception exception = assertThrows(RuntimeException.class, () -> {
            manageCoursesController.doDeleteCourse();
        });
        // Assert
        String expectedMessage = "Cannot delete or update a parent row: a foreign key constraint fails";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

}
