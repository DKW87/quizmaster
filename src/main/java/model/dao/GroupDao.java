package model.dao;

import model.entity.Course;
import model.entity.Group;
import model.entity.user.Teacher;

import java.util.ArrayList;
import java.util.List;

public class GroupDao {

  private static GroupDao instance = null;
  private UserDao userDao = UserDao.getInstance();
  private CourseDao courseDao = CourseDao.getInstance();
  private List<Group> groupData = new ArrayList<>();

  private GroupDao() {
    super();
    List<Teacher> allTeachers = userDao.getAllTeachers();
    List<Course> allCourses = courseDao.getAllCourses();
    int teacherIndex = 0;
    int teacherCount = allTeachers.size();
    int courseIndex = 0;
    int courseCount = allCourses.size();
    for (int i=0; i<5; i++) {
      Teacher teacher = allTeachers.get(teacherIndex);
      Course course = allCourses.get(courseIndex);
      Group group = new Group("MIW-" + i, teacher, course);
      teacherIndex = (teacherIndex + 1) % teacherCount;
      courseIndex = (courseIndex + 1) % courseCount;
      groupData.add(group);
    }
  }

  public static GroupDao getInstance() {
    if (instance == null) {
      instance = new GroupDao();
    }
    return instance;
  }

  public void addGroup(Group group) {
    groupData.add(group);
    System.out.println("Groep " + group.getName() + " toegevoegd.");
  }

  public void deleteGroup(Group group) {
    groupData.remove(group);
    System.out.println("Groep " + group.getName() + " verwijderd.");
  }

  public Group findGroupByIndex(int index) {
    if ( index >= 0 && index < groupData.size() ) {
      Group group = groupData.get(index);
      if (group != null) {
        return group;
      }
    }
    System.out.println("Groep " + index + " niet gevonden.");
    return null;
  }

  public List<Group> getAllGroups() {
    return groupData;
  }

}
