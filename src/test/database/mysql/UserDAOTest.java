/**
 * @author Mack Bakkum
 * @project quizmaster
 * @created 22 juni 2024 - 10:00
 */

package database.mysql;

import model.Role;
import model.User;
import org.junit.jupiter.api.Test;
import view.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDAOTest {
    private UserDAO userDAO;

    public void setup() {
        userDAO = new UserDAO(Main.getdBaccess());
    }

    // Tests if a new user has a correctly generated userName (5 letters of lastName, 2 letters of firstName)
    @Test
    public void generateUserNameTest() {
        setup();
        Role Student = new Role("Student");
        User user = new User("", "password123", "lebron", "james", Student);

        String usernameTest = userDAO.generateUserName(user);
        assertEquals("jamesle", usernameTest);
    }

    // Tests if a new user with duplicate name gets an auto increment on userName.
    @Test
    public void generateUserNameDuplicateTest() {
        setup();
        Role Student = new Role("Student");
        User user = new User("", "password123", "Michael", "Jordan", Student);
        User user2 = new User("", "password123", "Michael", "Jordan", Student);

        String username = userDAO.generateUserName(user);
        String username2 = userDAO.generateUserName(user2);
        assertEquals("JordaMi", username);
        assertEquals("JordaMi", username2);
    }
}

