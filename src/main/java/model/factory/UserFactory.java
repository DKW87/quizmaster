package model.factory;

import model.entity.user.*;

public class UserFactory {

  public static User createUserForRole(String name, String password, Role role) {
    switch (role) {
      case STUDENT: return new Student(name, password, role);
      case TEACHER: return new Teacher(name, password, role);
      case COORDINATOR: return new Coordinator(name, password, role);
      case ADMIN: return new Admin(name, password, role);
      case TECHSUPPORT: return new Support(name, password, role);
      case CAPO: return new Capo(name, password, role);
    }
    return null; // Should never happen, all cases are handled in the switch...
  }
}
