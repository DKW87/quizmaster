package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.UserSession;
import view.Main;
import view.SceneManager;

import static constants.Constant.*;
import static utils.Util.confirmMessage;
import static utils.Util.showAlert;

public class WelcomeController {

    private final UserSession userSession = Main.getUserSession();
    private final SceneManager sceneManager = Main.getSceneManager();


    @FXML
    private Label welcomeLabel;
    @FXML
    private MenuButton taskMenuButton;

    public void setup() {
        // Check if user is logged in
        if (userSession.getUser() == null) {
            sceneManager.showLoginScene();
            return;
        }
        // TODO: Set tasks for different roles , e.g. student and coordinator


        // Set welcome message
        welcomeLabel.setText(String.format("Ingelogd als %s, welkom %s!", userSession.getUser().getRoleName(),
                userSession.getUser().getFirstName()));

        //TODO:  Set tasks for different roles (e.g. student and coordinator)
        // -> Docent(3),Administrator(4),Functioneel Beheerder(5)

        // Switch to role
        switchToRole(userSession.getUser().getRole());


    }

    public void doLogout() {
        if (confirmMessage("Uitloggen", "Weet je zeker dat je wil uitloggen?")) {
            sceneManager.showLoginScene();
            userSession.setUser(null);
        }

    }

    /**
     * Set up the student tasks menu by clearing items, adding course and quiz management items,
     * and setting actions for course and quiz management.
     */
    private void setStudentSetup() {
        taskMenuButton.getItems().clear();
        var courseManagement = STUDENT_TASKS.get(0);
        var quizManagement = STUDENT_TASKS.get(1);
        taskMenuButton.getItems().addAll(STUDENT_TASKS);
        courseManagement.setOnAction(event -> sceneManager.showStudentSignInOutScene());
        quizManagement.setOnAction(event -> sceneManager.showSelectQuizForStudent());
    }

    private void setCoordinatorSetup() {
        taskMenuButton.getItems().clear();
        var dashboard = COORDINATOR_TASKS.get(0);
        var questionManagement = COORDINATOR_TASKS.get(1);
        var quizManagement = COORDINATOR_TASKS.get(2);
        taskMenuButton.getItems().addAll(COORDINATOR_TASKS);
        dashboard.setOnAction(event -> sceneManager.showCoordinatorDashboard());
        questionManagement.setOnAction(event -> sceneManager.showManageQuestionsScene());
        quizManagement.setOnAction(event -> sceneManager.showManageQuizScene());
    }

    private void setDocentSetup() {
        taskMenuButton.getItems().clear();
        taskMenuButton.setText("Geen beschikbare taken.");
    }

    private void setFBSetup() {
        taskMenuButton.getItems().clear();
        var userManagement = FB_TASKS.get(0);
        taskMenuButton.getItems().addAll(FB_TASKS);
        userManagement.setOnAction(event -> sceneManager.showManageUserScene());
    }

    private void setAdminSetup() {
        taskMenuButton.getItems().clear();
        var courseManagement = ADMINISTRATOR_TASKS.get(0);
        var exportResults = ADMINISTRATOR_TASKS.get(1);
        taskMenuButton.getItems().addAll(ADMINISTRATOR_TASKS);
        courseManagement.setOnAction(event -> sceneManager.showManageCoursesScene());
        exportResults.setOnAction(event -> sceneManager.showWelcomeScene());
    }

    // temporarily added all manage views to all roles for testing purposes
    private void switchToRole(int roleId) {
        switch (roleId) {
            case 1:
                setStudentSetup();
                break;
            case 2:
                setCoordinatorSetup();
                break;
            case 3:
                setDocentSetup();
                break;
            case 4:
                setAdminSetup();
                break;
            case 5:
                setFBSetup();
                break;
            default:
                showAlert(Alert.AlertType.ERROR, "Fout", "Deze rol bestaat niet");
                sceneManager.showLoginScene();
                break;
        }
    }
}

