package model;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 14 June 2024 - 20:07
 */
public class UserSession {
    private  User user;
    public UserSession() {
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getRoleId() {
        return this.user.getRole();
    }
}
