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

import java.util.ArrayList;
import java.util.List;

public class StudentSignOutController {

  SceneManager manager = SceneManager.getSceneManager();
  CourseDao courseDao = CourseDao.getInstance();
  Student student = (Student)Main.getCurrentUser();

  @FXML
  Button signoutButton;

  @FXML
  Button menuButton;

  @FXML
  ListView<String> courseList;

  public void setup() {
    List<Course> allCourses = student.getCourses();
    courseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    for (Course course: allCourses) {
      courseList.getItems().add(course.toString());
    }
  }

  public void doSignout() {
    ObservableList<Integer> selectedIndices = courseList.getSelectionModel().getSelectedIndices();
    List<Course> coursesCopy = new ArrayList<>();
    for (Course c: student.getCourses()) {
      coursesCopy.add(c);
    }
    for (int index: selectedIndices) {
      Course course = coursesCopy.get(index);
      student.signoff(course);
    }
    manager.showWelcomeScene();
  }

  public void doMenu(ActionEvent event) {
    manager.showWelcomeScene();
  }
}
