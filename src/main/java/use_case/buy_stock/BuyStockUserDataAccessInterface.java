package use_case.buy_stock;

import entity.User;

/**
 * The BuyStockUserDataAccessInterface defines the methods required to interact with the user data
 * during the "buy stock" use case. It provides methods for retrieving the current user, saving user
 * information, and retrieving user data by username.

 * Responsibilities:
 * - Retrieves the current user and saves user data during the buy stock use case.

 * Method(s):
 * - get(String username): Retrieves the user object by the provided username.
 * - saveUserInfo(User user): Saves the updated user information.
 * - getCurrentUser(): Retrieves the current user object.
 */

public interface BuyStockUserDataAccessInterface {

    /**
     * Retrieves the user object for the given username.
     *
     * @param username The username of the user to retrieve.
     * @return The User object, or null if no such user exists.
     */

    User get(String username);

    /**
     * Saves the updated user information.
     *
     * @param user The user object containing the updated information to be saved.
     */

    void saveUserInfo(User user);

    /**
     * Retrieves the current user object, representing the user who is currently interacting with the system.
     *
     * @return The current user object.
     */

    User getCurrentUser();
}
