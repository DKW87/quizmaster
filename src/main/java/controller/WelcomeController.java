package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import model.Role;
import model.UserSession;
import view.Main;
import view.SceneManager;

import java.util.Optional;

import static constants.Constant.COORDINATOR_TASKS;
import static constants.Constant.STUDENT_TASKS;

public class WelcomeController {

    private  final UserSession userSession = Main.getUserSession();
    private  final SceneManager sceneManager = Main.getSceneManager();

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
        // FIXME : @Mack role name is missing (userSession.getUser().getRole() -> Role)

        // Set welcome message
        welcomeLabel.setText(String.format("Inloggen als %s. \n Welcome %s!",
                userSession.getUser().getUserName(), userSession.getUser().getUserId()));

        //TODO:  Set tasks for different roles (e.g. student and coordinator)
        // -> Docent(3),Administrator(4),Functioneel Beheerder(5)

        // Switch to role
        switchToRole(userSession.getUser().getRole());


    }

    public void doLogout() {
        Alert confirmLogOut = new Alert(Alert.AlertType.CONFIRMATION);
        confirmLogOut.setTitle("Uitloggen");
        confirmLogOut.setHeaderText(null);
        confirmLogOut.setContentText("Weet je zeker dat je wil uitloggen?");
        Optional<ButtonType> result = confirmLogOut.showAndWait();
        if (result.get() == ButtonType.OK) {
            sceneManager.showLoginScene();
            userSession.setUser(null);
        }

    }

    /**
     * Set up the student tasks menu by clearing items, adding course and quiz management items,
     * and setting actions for course and quiz management.
     *
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
        taskMenuButton.getItems().addAll(COORDINATOR_TASKS);
    }
    private void switchToRole(int roleId){
        switch(roleId) {
            case 1:
                setStudentSetup();
                break;
            case 2:
                setCoordinatorSetup();
                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            default:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Role not found");
                alert.setContentText("Role not found");
                alert.showAndWait();
                sceneManager.showLoginScene();
                break;
        }
    }
}
