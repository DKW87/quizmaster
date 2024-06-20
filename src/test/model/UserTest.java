package model;

import org.junit.jupiter.api.Test;
import model.Role;
import model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    void testConstructorAndGetters() {
        Role role = new Role("Student");
        User user = new User("Mack_Bakkum", "password123", "MackTest", "BakkumTest", role);

        assertEquals(0, user.getUserId()); // Default user ID
        assertEquals("Mack_Bakkum", user.getUserName());
        assertEquals("password123", user.getPassword());
        assertEquals("MackTest", user.getFirstName());
        assertEquals("", user.getInfix()); // Empty infix in this user.
        assertEquals("BakkumTest", user.getLastName());
        assertEquals(role.getRoleId(), user.getRole());
    }
}