package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Course;
import view.Main;
import view.SceneManager;

import java.util.List;
import java.util.Optional;

public class ManageCoursesController {
    private final DBAccess dbAccess = Main.getdBaccess();
    private final SceneManager sceneManager = Main.getSceneManager();
    private final CourseDAO courseDao = new CourseDAO(dbAccess);

    @FXML
    public Button newCourseButton;
    @FXML
    public Button updateButton;
    @FXML
    public Button menuButton;
    @FXML
    public Button deleteButton;

    @FXML
    public TableColumn<Course, String> courseId;
    @FXML
    public TableColumn<Course, String> name;
    @FXML
    public TableColumn<Course, String> difficulty;
    @FXML
    public TableColumn<Course, String> coordinator;
    @FXML
    public TableColumn<Course, String> studentCount;


    @FXML
    private ListView<Course> courseList;

    @FXML
    private TableView<Course> courseTable;

    private Course selectedCourse;




    public void setup() {

        List<Course> courses = courseDao.getAll();
//        courseList.getItems().addAll(courses);
//        courseList.getSelectionModel().selectFirst();

         // set up table data
        ObservableList<Course> coursesData = FXCollections.observableArrayList(courses);
        courseTable.getItems().addAll(coursesData);
        courseTable.getSelectionModel().selectFirst();
        // event listeners for selection
        courseTable.getSelectionModel().selectedItemProperty().addListener(this::onChangeCourse);
        generateCourseTable();


    }

    public void doMenu() {
        sceneManager.showWelcomeScene();
    }

    public void doCreateCourse() {
        sceneManager.showCreateUpdateCourseScene(null);
    }

    public void doUpdateCourse() {
//        var course = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            sceneManager.showCreateUpdateCourseScene(selectedCourse);
        }
    }

    public void doDeleteCourse() {
//        var course = courseTable.getSelectionModel().getSelectedItem();
        // FIXME @Ekrem maak een generic DeleteAlert method
        if (selectedCourse != null) {
            Alert confirmLogOut = new Alert(Alert.AlertType.CONFIRMATION);
            confirmLogOut.setTitle("Delete Course");
            confirmLogOut.setHeaderText(null);
            confirmLogOut.setContentText("Weet je zeker dat je course wil verwijderen?");
            Optional<ButtonType> result = confirmLogOut.showAndWait();
            if (result.get() == ButtonType.OK) {
//            courseDao.deleteOneById(course.getCourseId());
//            courseTable.getItems().remove(course);
                courseDao.deleteOneById(selectedCourse.getCourseId());
                courseTable.getItems().remove(selectedCourse);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Selecteer eerst een cursus");
            alert.showAndWait();
        }


    }
    // setup table data with columns
    private void generateCourseTable() {
        name.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        courseId.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getCourseId())));
        difficulty.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDifficulty().getName()));
        coordinator.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCoordinator().getUserFullName()));
        studentCount.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getStudentCount())));
        courseTable.getSelectionModel().selectFirst();
    }

    private void onChangeCourse(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
        selectedCourse = newValue;
    }

}
