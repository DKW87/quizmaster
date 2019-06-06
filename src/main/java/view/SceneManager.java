package view;

import controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.entity.Course;
import model.entity.Group;
import model.entity.Quiz;
import model.entity.user.User;

import java.io.IOException;

public class SceneManager {

  private Stage primaryStage;

  public static SceneManager getSceneManager() {
    return Main.getSceneManager();
  }


  public SceneManager(Stage primaryStage) {
    super();
    this.primaryStage = primaryStage;
  }

  public FXMLLoader getScene(String fxml) {
    Scene scene;
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
      Parent root = loader.load();
      scene = new Scene(root);
      primaryStage.setScene(scene);
      return loader;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void setWindowTool() {
    FXMLLoader loader = getScene("/view/fxml/windowtool.fxml");
    if ( loader != null ) {
      WindowToolController controller = loader.getController();
      controller.populateScreenMenu();
    } else{
      System.out.println("set windowTool: Loader is not initialized");
      System.out.flush();
    }
  }

  public void showLoginScene() {
    getScene("/view/fxml/login.fxml");
  }

  public void showLoginFailedScene() {
    getScene("/view/fxml/loginFailed.fxml");
  }

  public void showWelcomeScene() {
    FXMLLoader loader = getScene("/view/fxml/welcomeScene.fxml");
    WelcomeController controller = loader.getController();
    controller.setup();
  }

  public void showNewUserScene() {
    FXMLLoader loader = getScene("/view/fxml/newUser.fxml");
    NewUserController controller = loader.getController();
    controller.setup();
  }

  public void showSelectUserScene() {
    FXMLLoader loader = getScene("/view/fxml/selectUser.fxml");
    SelectUserController controller = loader.getController();
    controller.setup();
  }

  public void showChangeUserScene(User user) {
    FXMLLoader loader = getScene("/view/fxml/changeUser.fxml");
    ChangeUserController controller = loader.getController();
    controller.setup(user);
  }

  public void showManageCoursesScene() {
    FXMLLoader loader = getScene("/view/fxml/manageCourses.fxml");
    ManageCoursesController controller = loader.getController();
    controller.setup();
  }

  public void showNewCourseScene() {
    FXMLLoader loader = getScene("/view/fxml/newCourse.fxml");
    NewCourseController controller = loader.getController();
    controller.setup();
  }

  public void showChangeCourseScene(Course course) {
    FXMLLoader loader = getScene("/view/fxml/changeCourse.fxml");
    ChangeCourseController controller = loader.getController();
    controller.setup(course);
  }

  public void showManageGroupsScene() {
    FXMLLoader loader = getScene("/view/fxml/manageGroups.fxml");
    ManageGroupsController controller = loader.getController();
    controller.setup();
  }

  public void showNewGroupScene() {
    FXMLLoader loader = getScene("/view/fxml/newGroup.fxml");
    NewGroupController controller = loader.getController();
    controller.setup();
  }

  public void showChangeGroupScene(Group group) {
    FXMLLoader loader = getScene("/view/fxml/changeGroup.fxml");
    ChangeGroupController controller = loader.getController();
    controller.setup(group);
  }

  public void showStudentSignInOutScene() {
    FXMLLoader loader = getScene("/view/fxml/studentSignInOut.fxml");
    StudentSignInOutController controller = loader.getController();
    controller.setup();
  }

  public void showSelectQuizForStudent() {
    FXMLLoader loader = getScene("/view/fxml/selectQuizForStudent.fxml");
    SelectQuizForStudentController controller = loader.getController();
    controller.setup();
  }

  public void showFillOutQuiz() {
    FXMLLoader loader = getScene("/view/fxml/fillOutQuiz.fxml");
    FillOutQuizController controller = loader.getController();
    controller.setup();
  }

  public void showStudentFeedback(Quiz quiz) {
    FXMLLoader loader = getScene("/view/fxml/studentFeedback.fxml");
    StudentFeedbackController controller = loader.getController();
    controller.setup(quiz);
  }

  public void showCoordinatorDashboard() {
    FXMLLoader loader = getScene("/view/fxml/coordinatorDashboard.fxml");
    CoordinatorDashboardController controller = loader.getController();
    controller.setup();
  }
}
