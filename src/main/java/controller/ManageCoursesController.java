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

import static utils.Util.confirmMessage;
import static utils.Util.showAlert;

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
    public TableColumn<Course, String> name;
    @FXML
    public TableColumn<Course, String> difficulty;
    @FXML
    public TableColumn<Course, String> coordinator;
    @FXML
    public TableColumn<Course, String> studentCount;

    @FXML
    private TableView<Course> courseTable;

    private Course selectedCourse;




    public void setup() {
        List<Course> courses = courseDao.getAll();
         // set up table data
        ObservableList<Course> coursesData = FXCollections.observableArrayList(courses);
        courseTable.getItems().addAll(coursesData);
        courseTable.getSelectionModel().selectFirst();
        selectedCourse = courseTable.getSelectionModel().getSelectedItem();
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
        if (selectedCourse != null) {
            sceneManager.showCreateUpdateCourseScene(selectedCourse);
        } else {
            showAlert(Alert.AlertType.ERROR, "Fout", "Selecteer eerst een cursus");
        }
    }

    public void doDeleteCourse() {
        if (selectedCourse != null) {
            showConfirmAlert();
        } else {
            showAlert(Alert.AlertType.ERROR, "Fout", "Selecteer eerst een cursus");
        }


    }
    // setup table data with columns
    private void generateCourseTable() {
        name.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
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

    private void showConfirmAlert() {
        String message = String.format("Weet je zeker dat je %s wil verwijderen?", selectedCourse.getName());
        if (confirmMessage("Course Verwijderen",message )) {
            courseDao.deleteOneById(selectedCourse.getCourseId());
            courseTable.getItems().remove(selectedCourse);
        }
    }

}
