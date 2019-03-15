package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import model.dao.CourseDao;
import model.entity.Course;
import model.entity.user.Student;
import view.Main;
import view.SceneManager;

import java.util.List;

public class SignupController {

  SceneManager manager = SceneManager.getSceneManager();
  CourseDao courseDao = CourseDao.getInstance();
  Student student = (Student)Main.getCurrentUser();

  @FXML
  Button signupButton;

  @FXML
  Button menuButton;

  @FXML
  ListView<String> courseList;

  public void setup() {
    List<Course> allCourses = courseDao.getAllCourses();
    courseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    for (Course course: allCourses) {
      courseList.getItems().add(course.toString());
    }
  }

  public void doSignup() {
    ObservableList<Integer> selectedIndices = courseList.getSelectionModel().getSelectedIndices();
    for (int index: selectedIndices) {
      Course course = courseDao.findCourseByIndex(index);
      student.signup(course);
    }
    manager.showWelcomeScene();
  }

  public void doMenu(ActionEvent event) {
    manager.showWelcomeScene();
  }

}
