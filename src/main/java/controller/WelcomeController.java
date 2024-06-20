package controller;

import database.mysql.RoleDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.User;
import model.UserSession;
import view.Main;
import view.SceneManager;

import static constants.Constant.COORDINATOR_TASKS;
import static constants.Constant.STUDENT_TASKS;
import static utils.Util.confirmMessage;
import static utils.Util.showAlert;

public class WelcomeController {

    private final UserSession userSession = Main.getUserSession();
    private final SceneManager sceneManager = Main.getSceneManager();
    private final RoleDAO roleDAO = new RoleDAO(Main.getdBaccess());

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
        welcomeLabel.setText(String.format("Ingelogd als %s, welkom %s!", roleDAO.getById(userSession.getRoleId()).getName(),
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

    // temporarily added all manage views to all roles for testing purposes
    private void switchToRole(int roleId){
        switch(roleId) {
            case 1:
                setAllManageViews();
                break;
            case 2:
                setAllManageViews();
                break;
            case 3:
                setAllManageViews();
                break;
            case 4:
                setAllManageViews();
                break;
            case 5:
                setAllManageViews();
                break;
            default:
                showAlert(Alert.AlertType.ERROR,"Fout", "Deze rol bestaat niet");
                sceneManager.showLoginScene();
                break;
        }
    }

    // temporary set all manage views for all roles for testing purposes
    private void setAllManageViews() {
        MenuItem manageUser = new MenuItem("Beheer gebruikers");
        manageUser.setOnAction(event -> sceneManager.showManageUserScene());
        taskMenuButton.getItems().add(manageUser);
        MenuItem manageCourse = new MenuItem("Beheer cursussen");
        manageCourse.setOnAction(event -> sceneManager.showManageCoursesScene());
        taskMenuButton.getItems().add(manageCourse);
        MenuItem manageQuiz = new MenuItem("Beheer quizzen");
        manageQuiz.setOnAction(event -> sceneManager.showManageQuizScene());
        taskMenuButton.getItems().add(manageQuiz);
        MenuItem manageQuestion = new MenuItem("Beheer vragen");
        manageQuestion.setOnAction(event -> sceneManager.showManageQuestionsScene());
        taskMenuButton.getItems().add(manageQuestion);
    }

}
