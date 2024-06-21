/**
 * @author Mack Bakkum
 * @project QuizMaster
 * @created 12/06/24 - 07:15
 */

package model;

public class User {

    private int userId;
    private String userName;
    private String password;
    private String firstName;
    private String infix;
    private String lastName;
    private final Role role;

    public User(int userId,String userName, String password, String firstName, String infix, String lastName, Role Role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.infix = infix;
        this.lastName = lastName;
        this.role = Role;
    }

    public User(String userName, String password, String firstName, String infix, String lastName, Role Role) {
      this(0, userName, password, firstName, infix, lastName, Role);
    }

    public User (String userName, String password, String firstName, String lastName, Role role) {
        this(userName, password, firstName, "", lastName, role);
    }

    public String toString() {
        return userId + " " + userName + " " + firstName + " " + infix + " " + lastName + " " + role;
    }

    public String getUserFullName() {
        return firstName + " " + infix + " " + lastName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getInfix() {
        return infix;
    }

    public String getLastName() {
        return lastName;
    }

    public int getRole() {
        return role.getRoleId();
    }

    public String getRoleName() {
        return role.getName();
    }
}
