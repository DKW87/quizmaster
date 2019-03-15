package model.entity;

import model.entity.user.Coordinator;
import model.entity.user.Student;

import java.util.ArrayList;
import java.util.List;

public class Course {

  private String name;
  private Coordinator coordinator;

  private List<Student> students;

  public Course(String name, Coordinator coordinator) {
    super();
    this.name = name;
    this.coordinator = coordinator;
    this.students = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Coordinator getCoordinator() {
    return coordinator;
  }

  public void setCoordinator(Coordinator coordinator) {
    this.coordinator = coordinator;
  }

  public void addStudent(Student student) {
    if (!students.contains(student)) {
      students.add(student);
    }
  }

  public void removeStudent(Student student) {
    if (students.contains(student)) {
      students.remove(student);
    }
  }

  public String toString() {
    return "[Course: " + name + ", coord: " + coordinator.getName() + "]";
  }
}
