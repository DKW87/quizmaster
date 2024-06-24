package database.mysql;

import model.Role;
import model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import view.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
        User user = new User("", "password123", "Lebron", "James", Student);
        User user2 = new User("", "password123", "Lebron", "James", Student);

        String username = userDAO.generateUserName(user);
        String username2 = userDAO.generateUserName(user2);
        assertEquals("jamesle", username);
        assertEquals("jamesle2", username2);
    }
}

