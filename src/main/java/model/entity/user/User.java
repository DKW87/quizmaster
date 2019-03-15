package model.entity.user;

public abstract class User {

  private String name;

  private String password;

  private Role role;

  public User() {
    this("John Doe", "Welcome123", Role.TECHSUPPORT);
  }

  public User(String name, String password, Role role) {
    super();
    this.name = name;
    this.password = password;
    this.role = role;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public boolean validate(String password) {
    return (this.password.equals(password));
  }

  public String toString() {
    return "<" + name + ", " + password + ", " + role.toString() + ">";
  }
}
