package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the User class.
 */
public class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        // Create a new user instance for each test
        user = new User("testUser", "testPassword");
    }

    @Test
    void testCreateUser() {
        // Test creating a user
        assertEquals("testUser", user.getName());
        assertEquals("testPassword", user.getPassword());
        // Default balance
        assertEquals(10000, user.getBalance(), 0.01);
        // Portfolio should be initialized
        assertNotNull(user.getPortfolio());
    }

    @Test
    void testLoginSuccessful() {
        // Test logging in with correct credentials
        assertTrue(user.login("testUser", "testPassword"));
    }

    @Test
    void testLoginFailure() {
        // Test logging in with incorrect credentials
        assertFalse(user.login("testUser", "wrongPassword"));
    }

    @Test
    void testUpdateProfile() {
        // Test updating user profile
        user.updateProfile("newUsername", "newPassword");
        assertEquals("newUsername", user.getName());
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    void testUniqueAccount() {
        // Create a unique user
        final User uniqueUser = User.isUniqueAccount("uniqueUser", "uniquePassword");
        assertNotNull(uniqueUser);
        assertEquals("uniqueUser", uniqueUser.getName());

        // Try to create another user with the same username
        final User duplicateUser = User.isUniqueAccount("uniqueUser", "anotherPassword");
        assertNull(duplicateUser);
    }

    @Test
    void testLogout() {
        // Test logging out
        // No return value, so just ensure no exceptions occur
        user.logout();
    }
}
