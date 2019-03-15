package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.dao.CourseDao;
import model.entity.Course;
import view.SceneManager;

import java.util.List;

public class ManageCoursesController {

  @FXML
  Button deleteCourseButton;

  @FXML
  Button changeCourseButton;

  @FXML
  Button newCourseButton;

  @FXML
  Button menuButton;

  @FXML
  ListView<String> courseList;

  private CourseDao courseDao = CourseDao.getInstance();
  private SceneManager manager = SceneManager.getSceneManager();

  public ManageCoursesController() {
    super();
  }

  public void setup() {
    List<Course> courses = courseDao.getAllCourses();
    for (Course c: courses) {
      courseList.getItems().add(c.getName() + " (" + c.getCoordinator().getName() + ")");
    }
  }

  public void doCreateCourse(ActionEvent event) {
    manager.showNewCourseScene();
  }

  public void doChangeCourse(ActionEvent event) {
    int courseIndex = Math.max(courseList.getSelectionModel().getSelectedIndex(), 0);
    Course course = courseDao.findCourseByIndex(courseIndex);
    manager.showChangeCourseScene(course);
  }

  public void doDeleteCourse(ActionEvent event) {
    int courseIndex = courseList.getSelectionModel().getSelectedIndex();
    Course course = courseDao.findCourseByIndex(courseIndex);
    System.out.println("Verwijder cursus: " + course.getName());
    courseDao.deleteCourse(course);
    manager.showManageCoursesScene();
  }

  public void doMenu(ActionEvent event) {
    manager.showWelcomeScene();
  }
}
