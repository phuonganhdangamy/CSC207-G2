package use_case.sell_stock;

import entity.User;

/**
 * DAO for the Sell Stock Use Case.
 */
public interface SellStockUserDataAccessInterface {

    /**
     * Saves the user.
     * @param user the user to save
     */
    void saveUserInfo(User user);

    /**
     * Returns the username of the curren user of the application.
     * @return the username of the current user; null indicates that no one is logged into the application.
     */
    String getCurrentUsername();

    /**
     * Returns the current user of the application.
     * @return the current user; null indicates that no one is logged into the application.
     */
    User getCurrentUser();
}
