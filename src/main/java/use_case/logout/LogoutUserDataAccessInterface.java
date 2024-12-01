package use_case.logout;

import entity.User;

/**
 * DAO for the Logout Use Case.
 */
public interface LogoutUserDataAccessInterface {

    /**
     * Returns the username of the curren user of the application.
     * @return the username of the current user
     */
    String getCurrentUsername();

    /**
     * Sets the username indicating who is the current user of the application.
     * @param username the new current username
     */
    void setCurrentUsername(String username);

    /**
     * Log out the current user from the application.
     * @param username the new current username
     * @return if the user logout is successful.
     */
    boolean logoutUser(String username);

    /**
     * Sets the username indicating who is the current user of the application.
     * @param user the new current username; null to indicate that no one is currently logged into the application.
     */
    void setCurrentUser(User user);

    /**
     * Returns the username of the curren user of the application.
     * @return the username of the current user; null indicates that no one is logged into the application.
     */
    User getCurrentUser();
}
