package use_case.profit_loss;

import entity.Portfolio;
import entity.User;

/**
 * Data Access Interface for the Profit Loss use case.
 * Declares methods for retrieving portfolio data.
 */
public interface ProfitLossDataAccessInterface {

    /**
     * Returns the current user of the application.
     * @return the current user; null indicates that no one is logged into the application.
     */
    User getCurrentUser();

}
