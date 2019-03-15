package model.entity.user;

import model.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

  private List<Course> courses;

  public Student(String name, String password, Role role) {
    super(name, password, role);
    courses = new ArrayList<>();
  }

  public void signup(Course course) {
    courses.add(course);
    course.addStudent(this);
  }

  public  void signoff(Course course) {
    courses.remove(course);
    course.removeStudent(this);
  }

  public List<Course> getCourses() {
    return courses;
  }
}
