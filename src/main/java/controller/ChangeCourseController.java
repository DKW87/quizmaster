package controller;

import controller.shared.UpdateCourseController;
import javafx.event.ActionEvent;
import model.entity.Course;

public class ChangeCourseController extends UpdateCourseController {

  public void setup(Course course) {
    super.setup(course);
  }

  public void doChangeCourse(ActionEvent event) {
    String name = nameField.getText();
    course.setName(name);
    course.setCoordinator(coordinator);
    manager.showManageCoursesScene();
  }
}
