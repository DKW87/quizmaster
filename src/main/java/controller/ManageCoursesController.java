package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import helpers.ExcelExporter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.Course;
import view.Main;
import view.SceneManager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
    public Button exportButton;

    @FXML
    public TableView<Course> courseTable;


    private Course selectedCourse;

    public Course getSelectedCourse() {
        return selectedCourse;
    }
    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }




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
    public void doExportExcel(ActionEvent actionEvent) {
        try {
            exportTable(courseTable);
            showAlert(Alert.AlertType.INFORMATION, "Succes", "Cursussen succesvol geexporteerd");
        } catch (IOException e) {
            // Handle any exceptions that occur during the export process
            System.out.println("Couldn't export table data.");
            throw new RuntimeException(e);
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
        setSelectedCourse(newValue);
    }

    private void showConfirmAlert() {
        String message = String.format("Weet je zeker dat je %s wil verwijderen?", selectedCourse.getName());
        if(Main.getTestMode()) {
            courseDao.deleteOneById(selectedCourse.getCourseId());
            courseTable.getItems().remove(selectedCourse);
            return;
        }

        if (confirmMessage("Course Verwijderen",message )) {
            courseDao.deleteOneById(selectedCourse.getCourseId());
            courseTable.getItems().remove(selectedCourse);
        }
    }
    private void exportTable(TableView<Course> tableView) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporteer Cursussen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(tableView.getScene().getWindow());

        if (file != null) {
            String sheetName = "Cursussen_" + LocalDate.now();
            ExcelExporter.exportToExcel(tableView, sheetName, file.getAbsolutePath());
        }
    }


}
