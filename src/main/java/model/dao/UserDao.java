package model.dao;

import model.entity.user.Coordinator;
import model.entity.user.Role;
import model.entity.user.Teacher;
import model.entity.user.User;
import model.factory.UserFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

  private static UserDao instance = null;

  private List<User> userData = new ArrayList<>();

  public static UserDao getInstance() {
    if (instance == null) {
      instance = new UserDao();
    }
    return instance;
  }

  private UserDao() {
    super();

    User huub     = UserFactory.createUserForRole("Huub", "apenoot", Role.CAPO);
    User tech     = UserFactory.createUserForRole("Foo Bar", "x", Role.TECHSUPPORT);
    User kwik     = UserFactory.createUserForRole("Kwik", "x", Role.STUDENT);
    User kwek     = UserFactory.createUserForRole("Kwek", "x", Role.STUDENT);
    User kwak     = UserFactory.createUserForRole("Kwak", "x", Role.STUDENT);
    User mickey   = UserFactory.createUserForRole("Mickey Mouse", "x", Role.STUDENT);
    User suske    = UserFactory.createUserForRole("Suske", "x", Role.STUDENT);
    User wiske    = UserFactory.createUserForRole("Wiske", "x", Role.STUDENT);
    User admin    = UserFactory.createUserForRole("Sidonia", "x", Role.ADMIN);
    User coord1   = UserFactory.createUserForRole("Dagobert Duck", "x", Role.COORDINATOR);
    User coord2   = UserFactory.createUserForRole("Guus Geluk", "x", Role.COORDINATOR);
    User teacher1 = UserFactory.createUserForRole("Bilbo Balings", "x", Role.TEACHER);
    User teacher2 = UserFactory.createUserForRole("Frodo Balings", "x", Role.TEACHER);
    User teacher3 = UserFactory.createUserForRole("Peregrijn Toek", "x", Role.TEACHER);
    User teacher4 = UserFactory.createUserForRole("Sam Gewissies", "x", Role.TEACHER);

    addUser(huub);
    addUser(tech);
    addUser(kwik);
    addUser(kwek);
    addUser(kwak);
    addUser(mickey);
    addUser(suske);
    addUser(wiske);
    addUser(admin);
    addUser(coord1);
    addUser(coord2);
    addUser(teacher1);
    addUser(teacher2);
    addUser(teacher3);
    addUser(teacher4);
  }

  public void addUser(User user) {
    userData.add(user);
    System.out.println("Gebruiker " + user + " toegevoegd.");
  }

  public void updateUser(User user, String name, String password, Role role) {
    user.setName(name);
    user.setPassword(password);
    user.setRole(role);
    System.out.println("Gebruiker " + user + " gewijzigd.");
  }

  public void deleteUser(User user) {
    userData.remove(user);
    System.out.println("Gebruiker " + user + " verwijderd.");
  }

  public User findUserByName(String name) {
    for (User user: userData) {
      if (name.equals(user.getName())) {
        return user;
      }
    }
    return null;
  }

  public User findUserByIndex(int index) {
    if ( index >= 0 && index < userData.size() ) {
      User u = userData.get(index);
      if (u != null) {
        return u;
      }
    }
    System.out.println("Gebruiker " + index + " niet gevonden");
    return null;
  }

  public List<User> getAllUsers() {
    return userData;
  }

  public List<Coordinator>  getAllCoordinators() {
    List<Coordinator> coordinators = new ArrayList<>();
    for (User u: userData) {
      if (u instanceof Coordinator) {
        coordinators.add((Coordinator)u);
      }
    }
    return coordinators;
  }

  public List<Teacher> getAllTeachers() {
    List<Teacher> teachers = new ArrayList<>();
    for (User u: userData) {
      if (u instanceof Teacher) {
        teachers.add((Teacher)u);
      }
    }
    return teachers;
  }
 }
