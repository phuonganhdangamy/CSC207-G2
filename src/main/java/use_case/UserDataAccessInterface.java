package use_case;
import entity.User;

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

    boolean logoutUser(String username);
}
