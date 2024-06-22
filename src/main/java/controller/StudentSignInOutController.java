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
import model.StudentCourse;
import model.UserSession;
import view.Main;

import java.util.ArrayList;
import java.util.List;

import static utils.Util.confirmMessage;
import static utils.Util.showAlert;

public class StudentSignInOutController {
    private  final DBAccess dbAccess = Main.getdBaccess();
    private  final CourseDAO courseDAO = new CourseDAO(dbAccess);
    private final UserSession userSession = Main.getUserSession();

    @FXML
    private TableView<Course>  signedOutCourseTable;
    @FXML
    public TableColumn<Course, String> nameCourse;
    @FXML
    public TableColumn<Course, String>  difficultyCourse;
    @FXML
    public TableColumn<Course, String>  coordinatorCourse;
    @FXML
    public TableView<StudentCourse> signedInCourseTable;
    @FXML
    public TableColumn<StudentCourse, String>  nameStudentCourse;
    @FXML
    public TableColumn<StudentCourse, String>  difficultyStudentCourse;
    @FXML
    public TableColumn<StudentCourse, String>  coordinatorStudentCourse;
    @FXML
    public TableColumn<StudentCourse, String>  dateStudentCourse;

    private final List<Course> selectedCourses = new ArrayList<>();
    private final List<StudentCourse> selectedStudentCourses = new ArrayList<>();


    public void setup() {
        // * set up table data
        setInitialValues();
        signedInCourseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        signedOutCourseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // * event listeners for selection
        signedOutCourseTable.getSelectionModel().selectedItemProperty().addListener(this::onChangeCourse);
        signedInCourseTable.getSelectionModel().selectedItemProperty().addListener(this::onChangeStudentCourse);
        // * set up table columns
        generateCourseTable();
        generateStudentCourseTable();
    }

    private void onChangeStudentCourse(ObservableValue<? extends StudentCourse> observable,
                                       StudentCourse oldValue, StudentCourse newValue) {
        selectedStudentCourses.clear();
        selectedStudentCourses.addAll(signedInCourseTable.getSelectionModel().getSelectedItems());
    }

    private void onChangeCourse(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
        selectedCourses.clear();
        selectedCourses.addAll(signedOutCourseTable.getSelectionModel().getSelectedItems());
    }

    @FXML
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    @FXML
    public void doSignIn() {
        if (selectedCourses.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Fout", "Selecteer eerst een of meerdere cursus");
            return;
        }
        if (confirmMessage("U staat op het punt om cursussen in te schrijven",
                "Weet u zeker dat u deze cursussen wilt ingeschreven?")) {
            handleSignIn();
        }

    }

    @FXML
    public void doSignOut() {
        if (selectedStudentCourses.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Fout", "Selecteer eerst een of meerdere cursus");
            return;
        }
        if (confirmMessage("U staat op het punt om cursussen uit te schrijven",
                "Weet u zeker dat u deze cursussen wilt uitgeschreven?")) {
            handleSignOut();
        }

    }


    private void generateCourseTable() {
        nameCourse.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        difficultyCourse.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDifficulty().getName()));
        coordinatorCourse.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCoordinator().getUserFullName()));

    }

    private void generateStudentCourseTable() {
        nameStudentCourse.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCourse().getName()));
        difficultyStudentCourse.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCourse().getDifficulty().getName()));
        coordinatorStudentCourse.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCourse().getCoordinator().getUserFullName()));
        dateStudentCourse.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getEnrollDate())));

    }
    private void setInitialValues() {
        List<Course> courseList = courseDAO.getUnAssignedCoursesByStudent(userSession.getUser().getUserId());
        List<StudentCourse> studentCourseList = courseDAO.getCoursesByStudent(userSession.getUser().getUserId());
        ObservableList<Course> courseObservableList = FXCollections.observableArrayList(courseList);
        ObservableList<StudentCourse> studenCourseObservableList = FXCollections.observableArrayList(studentCourseList);
        signedOutCourseTable.getItems().clear();
        signedInCourseTable.getItems().clear();
        signedInCourseTable.getItems().addAll(studenCourseObservableList);
        signedOutCourseTable.getItems().addAll(courseObservableList);
        if(courseObservableList.isEmpty()) {
            signedOutCourseTable.setPlaceholder(new Label("Geen cursussen om uit te schrijven"));
        }
        if(studenCourseObservableList.isEmpty()) {
            signedInCourseTable.setPlaceholder(new Label("Geen cursussen om in te schrijven"));
        }
    }
    private void handleSignIn() { StringBuilder sb = new StringBuilder();
        sb.append(String.format("Met succes uitgeschreven voor %d cursussen", selectedCourses.size() ));
        for (Course course : selectedCourses) {
            courseDAO.addStudentToCourse(userSession.getUser().getUserId(), course.getCourseId());
            sb.append(String.format("\n%s", course.getName()));
        }
        showAlert(Alert.AlertType.INFORMATION, "Ingeschreven Succes", sb.toString());
        selectedCourses.clear();
        signedOutCourseTable.getSelectionModel().clearSelection();
        // set initial values after sign in
        setInitialValues();

    }
    private void handleSignOut() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Met succes uitgeschreven voor %d cursussen", selectedStudentCourses.size() ));

        for (StudentCourse studentCourse : selectedStudentCourses) {
            courseDAO.removeStudentFromCourse(studentCourse.getStudentCourseId());
            sb.append(String.format("\n%s", studentCourse.getCourse().getName()));
        }
        showAlert(Alert.AlertType.INFORMATION, "Uitgeschreven Succes", sb.toString());
        selectedStudentCourses.clear();
        signedInCourseTable.getSelectionModel().clearSelection();
        // set initial values after sign out
        setInitialValues();
    }
}
