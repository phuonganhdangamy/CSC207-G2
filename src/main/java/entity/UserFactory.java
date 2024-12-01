package entity;

/**
 * A factory class responsible for creating instances of User.
 */
public class UserFactory {
    /**
     * Creates a new instance of User with the given username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A new User object with the specified username and password.
     */
    public User create(String username, String password){
        return new User(username, password);
    }
}
