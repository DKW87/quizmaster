package model.dao;

import model.entity.Course;
import model.entity.user.Coordinator;

import java.util.ArrayList;
import java.util.List;

public class CourseDao {

  private static CourseDao instance = null;
  private UserDao userDao = UserDao.getInstance();

  private List<Course> courseData = new ArrayList<>();

  private CourseDao() {
    super();
    Coordinator coord1 = (Coordinator)userDao.findUserByName("Dagobert Duck");
    Coordinator coord2 = (Coordinator)userDao.findUserByName("Guus Geluk");
    Course course1 = new Course("Inleiding in de Uitbuiting", coord1);
    Course course2 = new Course("Magnetisme en Tunnelbouw", coord2);
    Course course3 = new Course("Quantummechanica 1", coord1);
    Course course4 = new Course("Inleiding eschatologie", coord2);
    Course course5 = new Course("Internet voor dummies", coord1);

    addCourse(course1);
    addCourse(course2);
    addCourse(course3);
    addCourse(course4);
    addCourse(course5);
  }

  public static CourseDao getInstance() {
    if (instance == null) {
      instance = new CourseDao();
    }
    return instance;
  }

  public void addCourse(Course course) {
    courseData.add(course);
    System.out.println("Vak " + course.getName() + " toegevoegd.");
  }

  public void deleteCourse(Course course) {
    courseData.remove(course);
    System.out.println("Vak " + course.getName() + " verwijderd.");
  }

  public Course findCourseByIndex(int index) {
    if ( index >= 0 && index < courseData.size() ) {
      Course course = courseData.get(index);
      if (course != null) {
        return course;
      }
    }
      System.out.println("Vak " + index + " niet gevonden.");
      return null;
  }

  public List<Course> getAllCourses() {
    return courseData;
  }
}
