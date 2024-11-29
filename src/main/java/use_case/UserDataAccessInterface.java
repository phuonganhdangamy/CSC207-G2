package use_case;

import entity.User;

/**
 * Interface defining the contract for user-related data access operations.
 */
public interface UserDataAccessInterface {

    /**
     * Checks if the given username exists.
     *
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Saves the user.
     *
     * @param user the user to save
     * @throws DataAccessException if an error occurs during the save operation
     */
    void save(User user) throws DataAccessException;

    /**
     * Updates the system to record this user's password.
     *
     * @param user the user whose password is to be updated
     */
    void changePassword(User user);

    /**
     * Returns the user with the given username.
     *
     * @param username the username to look up
     * @return the user object with the given username
     */
    User get(String username);

    /**
     * Logs out the user with the specified username.
     *
     * @param username the username of the user to log out
     * @return true if the user was successfully logged out; false otherwise
     */
    boolean logoutUser(String username);
}
