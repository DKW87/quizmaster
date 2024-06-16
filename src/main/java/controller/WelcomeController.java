package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import model.UserSession;
import view.Main;
import view.SceneManager;

import static constants.Constant.COORDINATOR_TASKS;
import static constants.Constant.STUDENT_TASKS;

public class WelcomeController {

    private static final UserSession userSession = Main.getUserSession();
    private static final SceneManager sceneManager = Main.getSceneManager();



    @FXML
    private Label welcomeLabel;
    @FXML
    private MenuButton taskMenuButton;

    public void setup() {
        // Set welcome message
        if (userSession.getUser() == null) {
            sceneManager.showLoginScene();
            return;
        }
        // TODO: Set tasks for different roles , e.g. student and coordinator
        // FIXME : @Mack role name is missing (userSession.getUser().getRole() -> Role)
        welcomeLabel.setText(String.format("Inloggen als %s. \n Welcome %s!",
                userSession.getUser().getUserName(), userSession.getUser().getUserId()));

        //TODO:  Set tasks for different roles (e.g. student and coordinator)
        // -> Docent(3),Administrator(4),Functioneel Beheerder(5)

        switch (userSession.getUser().getRole()) {
            case 1:
                setStudentSetup();
                break;
            case 2:
                setCoordinatorSetup();
                break;
        }

    }

    public void doLogout() {
        sceneManager.showLoginScene();
        userSession.setUser(null);
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
}
