package use_case.profit_loss;

import entity.Portfolio;

/**
 * Data Access Interface for the Profit Loss use case.
 * Declares methods for retrieving portfolio data.
 */
public interface ProfitLossDataAccessInterface {

    /**
     * Retrieves the portfolio of a user by their ID.
     *
     * @param userId the unique ID of the user
     * @return the user's portfolio
     */
    Portfolio getPortfolio(String userId);
}
