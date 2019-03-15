package controller;

import controller.shared.UpdateCourseController;
import javafx.event.ActionEvent;
import model.entity.Course;

public class NewCourseController extends UpdateCourseController {

  private Course course;

  public void setup() {
    super.setup();
  }

  public void doNewCourse(ActionEvent event) {
    String name = nameField.getText();
    Course newCourse = new Course(name, coordinator);
    courseDao.addCourse(newCourse);
    manager.showManageCoursesScene();
  }
}
