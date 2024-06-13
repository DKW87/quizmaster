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

    public User(int userId, String userName, String password, String firstName, String infix, String lastName, Role Role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.infix = infix;
        this.lastName = lastName;
        this.role = Role;
    }

    public User (int userId, String userName, String password, String firstName, String lastName, Role role) {
        this(userId, userName, password, firstName, "", lastName, role);
    }

    public String toString() {
        return "User info: " + userId + " " + userName
                + "\n firstName: " + firstName + " lastName: " + infix + " " + lastName
                + "\n role: " + role;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRole() {
        return role.getRoleId();
    }
}
