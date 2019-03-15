package controller.shared;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import model.dao.CourseDao;
import model.dao.UserDao;
import model.entity.Course;
import model.entity.user.Coordinator;
import view.SceneManager;

import java.util.List;

public class UpdateCourseController {

  protected SceneManager manager = SceneManager.getSceneManager();
  protected CourseDao courseDao = CourseDao.getInstance();
  protected UserDao userDao = UserDao.getInstance();
  protected Course course;
  protected Coordinator coordinator;

  @FXML
  protected TextField nameField;

  @FXML
  protected MenuButton coordinatorMenuButton;

  @FXML
  protected Button menuButton;

  public void setup() {
    List<Coordinator> coordinators = userDao.getAllCoordinators();
    for ( Coordinator coordinator: coordinators) {
      MenuItem item = new MenuItem();
      item.setText(coordinator.getName());
      item.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          setCoordinator(coordinator);
          coordinatorMenuButton.setText(coordinator.getName());
        }
      });
      coordinatorMenuButton.getItems().add(item);
    }
    coordinator = coordinators.get(0);
  }

  public void setup(Course course) {
    setup();
    this.course = course;
    this.coordinator = course.getCoordinator();
    nameField.setText(course.getName());
    coordinatorMenuButton.setText(course.getCoordinator().getName());
  }

  public void setCoordinator(Coordinator coordinator) {
    this.coordinator = coordinator;
  }

  public void doMenu(ActionEvent event) {
    manager.showWelcomeScene();
  }

}
