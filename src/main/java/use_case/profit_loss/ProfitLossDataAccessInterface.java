package use_case.profit_loss;

import entity.Portfolio;
import entity.User;

import java.util.Map;

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
//    /**
//     * Retrieves the current cost of a stock by its ticker symbol.
//     *
//     * @param tickerSymbol the stock entity containing the ticker symbol.
//     * @return the current cost of the stock.
//     */
//    double getCost(String tickerSymbol);
//
//    /**
//     * Checks if stock information can be retrieved using the ticker symbol.
//     *
//     * @param tickerSymbol the unique identifier of the stock.
//     * @return true if the stock exists, false otherwise.
//     */
//    boolean isStockExist(String tickerSymbol);
}