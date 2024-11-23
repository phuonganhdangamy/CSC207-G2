package use_case.buy_stock;

import entity.User;

public interface BuyStockUserDataAccessInterface {
    /**
     * Retrieves a user by username.
     *
     * @param username the username of the user to retrieve
     * @return the User object, or null if the user does not exist
     */
    User get(String username);

    /**
     * Saves the updated user information.
     *
     * @param user the user object to save
     */
    void saveUserInfo(User user);
}
