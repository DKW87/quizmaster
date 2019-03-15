package controller.shared;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import model.dao.CourseDao;
import model.dao.GroupDao;
import model.dao.UserDao;
import model.entity.Course;
import model.entity.Group;
import model.entity.user.Teacher;
import view.SceneManager;

import java.util.List;

public class UpdateGroupController {

  protected SceneManager manager = SceneManager.getSceneManager();
  protected CourseDao courseDao = CourseDao.getInstance();
  protected UserDao userDao = UserDao.getInstance();
  protected GroupDao groupDao = GroupDao.getInstance();
  protected Teacher teacher;
  protected Course course;
  protected Group group;

  @FXML
  protected TextField nameField;

  @FXML
  protected MenuButton courseMenuButton;

  @FXML
  protected MenuButton teacherMenuButton;

  @FXML
  protected Button menuButton;

  public void setup() {
    List<Teacher> allTeachers = userDao.getAllTeachers();
    for (Teacher teacher: allTeachers) {
      MenuItem item = new MenuItem();
      item.setText(teacher.getName());
      item.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          setTeacher(teacher);
          teacherMenuButton.setText(teacher.getName());
        }
      });
      teacherMenuButton.getItems().add(item);
    }
    this.teacher = allTeachers.get(0);

    List<Course> allCourses = courseDao.getAllCourses();
    for (Course course: allCourses) {
      MenuItem item = new MenuItem();
      item.setText(course.getName());
      item.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          setCourse(course);
          courseMenuButton.setText(course.getName());
        }
      });
      courseMenuButton.getItems().add(item);
    }
    this.course = allCourses.get(0);
  }

  public void setup(Group group) {
    setup();
    this.group = group;
    this.teacher = group.getTeacher();
    this.course = group.getCourse();
    nameField.setText(group.getName());
    courseMenuButton.setText(course.getName());
    teacherMenuButton.setText(teacher.getName());
  }

  public void doMenu(ActionEvent event) {
    manager.showWelcomeScene();
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public void setCourse(Course course) {
    this.course = course;
  }
}
