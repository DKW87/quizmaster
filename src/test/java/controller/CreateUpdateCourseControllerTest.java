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

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 24 June Monday 2024 - 13:28
 */
class CreateUpdateCourseControllerTest {

    private static final DBAccess dbAccess = Main.getdBaccess();
    private static final CourseDAO courseDAO = new CourseDAO(dbAccess);
    private static final UserDAO userDAO = new UserDAO(dbAccess);
    private static final DifficultyDAO difficultyDAO = new DifficultyDAO(dbAccess);
    private static CreateUpdateCourseController createUpdateCourseController;

    @BeforeAll
    public static void beforeAll() throws SQLException, IOException {
        Platform.startup(() -> {
        });
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/fxml/createUpdateCourse.fxml"));
        fxmlLoader.load();
        createUpdateCourseController = fxmlLoader.getController();
        createUpdateCourseController.setup(null);

    }

    @Test
    @DisplayName("Test CreateUpdateCourseController onCreateCourse")
    void onCreateCourse() {
        String defaultCourseName ="Test Course" + LocalDateTime.now();
        createUpdateCourseController.courseNameField.setText(defaultCourseName);
        createUpdateCourseController.coordinatorComboBox.setValue(userDAO.getByRoleID(2).get(0));
        createUpdateCourseController.difficultyComboBox.setValue(difficultyDAO.getById(1));
        createUpdateCourseController.doCreateUpdateCourse();
        assertEquals(defaultCourseName, courseDAO.getByName(defaultCourseName).getName());
        assertEquals(userDAO.getByRoleID(2).get(0).getUserId(), courseDAO.getByName(defaultCourseName).getCoordinator().getUserId());
        assertEquals(difficultyDAO.getById(1).getDifficultyId(), courseDAO.getByName(defaultCourseName).getDifficulty().getDifficultyId());
    }
    @Test
    @DisplayName("Test CreateUpdateCourseController onUpdateCourse")
    void onUpdateCourse() {
        Course course = new Course("Test Default Course" + LocalDateTime.now(), userDAO.getById(1), difficultyDAO.getById(1));
        courseDAO.storeOne(course);
        createUpdateCourseController.setup(course);
        String defaultCourseName ="Update Test Course" + LocalDateTime.now();
        createUpdateCourseController.courseNameField.setText(defaultCourseName);
        createUpdateCourseController.coordinatorComboBox.setValue(userDAO.getByRoleID(2).get(0));
        createUpdateCourseController.difficultyComboBox.setValue(difficultyDAO.getById(1));
        createUpdateCourseController.doCreateUpdateCourse();
        assertEquals(defaultCourseName, courseDAO.getByName(defaultCourseName).getName());
        assertEquals(userDAO.getByRoleID(2).get(0).getUserId(), courseDAO.getByName(defaultCourseName).getCoordinator().getUserId());
        assertEquals(difficultyDAO.getById(1).getDifficultyId(), courseDAO.getByName(defaultCourseName).getDifficulty().getDifficultyId());

    }
}