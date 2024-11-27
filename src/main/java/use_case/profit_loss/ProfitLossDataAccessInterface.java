package use_case.profit_loss;

import entity.Portfolio;
import entity.User;

/**
 * Data Access Interface for the Profit Loss use case.
 * Declares methods for retrieving portfolio data.
 */
public interface ProfitLossDataAccessInterface {

    Portfolio getCurrentUser(String username);

    /**
     * Returns the current user of the application.
     * @return the current user; null indicates that no one is logged into the application.
     */
    User getCurrentUser();

    /**
     * Retrieve the cost of a stock given its ticker symbol.
     *
     * @param ticker The ticker symbol of the stock.
     * @return The cost of the stock.
     */
    double getCost(String ticker);
}
