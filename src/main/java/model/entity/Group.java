package model.entity;

import model.entity.user.Student;
import model.entity.user.Teacher;

import java.util.ArrayList;
import java.util.List;

public class Group {

  private String name;
  private Teacher teacher;
  private Course course;
  private List<Student> participants;

  public Group(String name, Teacher teacher, Course course) {
    super();
    this.name = name;
    this.teacher = teacher;
    this.course = course;
    this.participants = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public List<Student> getParticipants() {
    return participants;
  }

  public void addParticipant(Student participant) {
    participants.add(participant);
  }

  public String toString() {
    return name + ", " + course.getName() + ", " + teacher.getName();
  }
}
