package model.entity.user;

public enum Role {

  CAPO("Capo di tutti capi"),
  STUDENT("Student"),
  TEACHER("Docent"),
  COORDINATOR("Coördinator"),
  ADMIN("Administrator"),
  TECHSUPPORT("Systeembeheerder");

  private String roleName;

  private Role(String name) {
    roleName = name;
  }

  public String toString() {
    return roleName;
  }
}
