package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.DifficultyDAO;
import database.mysql.UserDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import model.Course;
import model.Difficulty;
import model.User;
import view.Main;
import view.SceneManager;

import static utils.Util.showAlert;

public class CreateUpdateCourseController {

    private final SceneManager sceneManager = Main.getSceneManager();
    private final DBAccess dbAccess = Main.getdBaccess();
    private final DifficultyDAO difficultyDao = new DifficultyDAO(dbAccess);
    private final UserDAO userDao = new UserDAO(dbAccess);
    private final CourseDAO courseDao = new CourseDAO(dbAccess);

    @FXML
    public Button saveButton;
    @FXML
    public Button backButton;

    @FXML
    public TextField courseIdField;
    @FXML
    public TextField courseNameField;
    @FXML
    public ComboBox<Difficulty> difficultyComboBox;
    @FXML
    public ComboBox<User> coordinatorComboBox;
    @FXML
    public Label formTitle;


    private Course selectedCourse;


    public void setup(Course course) {
        difficultyComboBox.getItems()
                .addAll(FXCollections.observableArrayList(difficultyDao.getAll()));
        setupCourseCoordinatorComboBox();
        setDefaultValue(course);
    }

    @FXML
    public void doMenu() {
        sceneManager.showWelcomeScene();
    }

    public void goList(ActionEvent actionEvent) {
        sceneManager.showManageCoursesScene();
    }

    @FXML
    public void doCreateUpdateCourse(ActionEvent actionEvent) {
        Course course = getCourse();
        // Check if the course details are valid
        if (course != null) {
             // select create or update action
            selectAction(course);
        }
    }

    /**
     * Retrieves a Course object based on the values entered the course name field,
     * course ID field, difficulty combo box, and coordinator combo box.
     * If the course name is blank or empty, an error message is displayed and null is returned.
     * If the course ID is not empty, it is parsed as an integer.
     *
     * @return  a Course object with the specified values, or null if the course name is blank or empty
     */
    private Course getCourse() {
        String name = courseNameField.getText();
        // Check if name is not blank or empty
        if (name.isBlank() || name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Fout", "Cursusnaam mag niet leeg zijn!");
            return null;
        }
        int courseId = 0;
        // Check if courseId
        if (!courseIdField.getText().isEmpty()) {
            courseId = Integer.parseInt(courseIdField.getText());
        }
        Difficulty difficulty = difficultyComboBox.getSelectionModel().getSelectedItem();
        User coordinator = coordinatorComboBox.getSelectionModel().getSelectedItem();
        return new Course(courseId, name, coordinator, difficulty);
    }

    /**
     * Sets up the course coordinator combo box by adding coordinators from the user DAO to the items list.
     * Also sets a custom cell factory and converter to display the coordinator's name.
     *
     */
    private void setupCourseCoordinatorComboBox(){
        var coordinators = userDao.getByRoleID(2);
        coordinatorComboBox.getItems()
                .addAll(FXCollections.observableArrayList(coordinators));
        // Custom cell factory to display coordinator name
        coordinatorComboBox.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(User u, boolean empty) {
                super.updateItem(u, empty);
                setText(empty ? null : u.getUserFullName());
            }
        });
        // Custom converter to display coordinator name
        coordinatorComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(User u) {
                return u != null ? u.getUserFullName() : null;
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });
    }

    /**
     * Sets the default values for the form fields based on the provided Course object.
     * If the Course object is null, the default values are set to the first items in the
     * difficultyComboBox and coordinatorComboBox.
     *
     * @param course the Course object to set the default values from
     */
    private void setDefaultValue(Course course) {
        if (course != null) {
            selectedCourse = course;
            formTitle.setText("Cursus Wijzigen");
            courseIdField.setText(String.valueOf(course.getCourseId()));
            courseNameField.setText(course.getName());
            difficultyComboBox.setValue(course.getDifficulty());
            coordinatorComboBox.setValue(course.getCoordinator());

        } else {
            coordinatorComboBox.getSelectionModel().selectFirst();
            difficultyComboBox.getSelectionModel().selectFirst();
        }
    }
    /**
     * A method that checks if a course already exists based on the course name.
     *
     * @param  course   the Course object to check for existence
     * @return          true if the course already exists, false otherwise
     */
    private boolean isExistingCourse(Course course) {
        return courseDao.getByName(course.getName()) != null;
    }

    /**
     * Selects an action based on the given course. If the course ID is 0,
     * checks if the course name is unique and stores the new course in the database.
     * If the course ID is not 0, updates the existing course in the database else creates a new course.
     *
     * @param  course   the Course object to select an action for
     */
    private void selectAction(Course course) {
        if (course.getCourseId() == 0) {
            // Check if name is unique
            if(isExistingCourse(course)) {
                showAlert(Alert.AlertType.ERROR, "Fout", "Cursusnaam bestaat al!");
                return;
            }
            // Store the new course in the database
            courseDao.storeOne(course);
            // reset form
            resetForm();
            // Display success message
            showAlert(Alert.AlertType.INFORMATION, "Succes", "Cursus succesvol aangepast");
        } else {
            // Update the existing course in the database
            courseDao.updateOne(course);
            // Display success message
            showAlert(Alert.AlertType.INFORMATION, "Succes", "Cursus succesvol aangepast");

        }

    }
    /**
     * Resets the form by clearing courseIdField, courseNameField, and selecting the first items in
     * difficultyComboBox and coordinatorComboBox.
     */
    private void resetForm() {
        courseIdField.clear();
        courseNameField.clear();
        difficultyComboBox.getSelectionModel().selectFirst();
        coordinatorComboBox.getSelectionModel().selectFirst();

    }

}



