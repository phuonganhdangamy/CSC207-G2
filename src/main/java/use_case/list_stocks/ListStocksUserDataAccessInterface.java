package use_case.list_stocks;

import entity.User;

/**
 * Interface for accessing user data related to the ListStocks use case.
 * This provides a method to retrieve a user by their username.
 */
public interface ListStocksUserDataAccessInterface {

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to be retrieved.
     * @return The User object associated with the given username, or null if no such user exists.
     */
    User getUserByUsername(String username);
}

