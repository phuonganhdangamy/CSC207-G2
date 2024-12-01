package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.Stock;
import entity.User;
import use_case.find_stock.FindStockDataAccessInterface;

/**
 * Provides in-memory storage for stocks and user portfolios.
 * Implements the FindStockDataAccessInterface.
 */
public class InMemoryStockDataAccessObject implements FindStockDataAccessInterface {

    // Stores the stocks with their ticker symbol as the key
    private final Map<String, Stock> stocks = new HashMap<>();

    // Stores the user’s portfolio where the key is the username and value is the owned stocks
    private final Map<String, Map<String, Integer>> userPortfolios = new HashMap<>();

    /**
     * Retrieves the cost of a stock given its ticker symbol.
     *
     * @param tickerSymbol The ticker symbol of the stock.
     * @return The cost of the stock, or 0.0 if the stock does not exist.
     */
    @Override
    public double getCost(String tickerSymbol) {
        final Stock stock = stocks.get(tickerSymbol);
        // Default cost
        double cost = 0.0;
        if (stock != null) {
            cost = stock.getCost();
        }
        return cost;
    }

    /**
     * Checks if a stock exists in the system.
     *
     * @param tickerSymbol The ticker symbol of the stock.
     * @return True if the stock exists, false otherwise.
     */
    @Override
    public boolean isStockExist(String tickerSymbol) {
        return stocks.containsKey(tickerSymbol);
    }

    /**
     * Retrieves the portfolio of a user.
     *
     * @param user The user whose portfolio is to be retrieved.
     * @return A map of ticker symbols to their quantities in the user's portfolio.
     */
    public Map<String, Integer> getUserPortfolio(User user) {
        return userPortfolios.getOrDefault(user.getName(), new HashMap<>());
    }

    /**
     * Retrieves all stocks owned by a user.
     *
     * @param username The username of the user.
     * @return A map of ticker symbols to their quantities owned by the user.
     *         Returns an empty map if the user does not exist.
     */
    // Helper method to get all stocks owned by a user
    public Map<String, Integer> getUserStocks(String username) {
        // Default empty portfolio
        Map<String, Integer> userStocks = new HashMap<>();
        if (userPortfolios.containsKey(username)) {
            userStocks = userPortfolios.get(username);
        }
        else {
            System.out.println("User with username \"" + username + "\" does not exist.");
        }
        return userStocks;
    }

    /**
     * Saves or updates a user's portfolio.
     *
     * @param username The username of the user.
     * @param tickerSymbol The ticker symbol of the stock.
     * @param quantity The quantity of stocks owned by the user.
     */
    public void updateUserPortfolio(String username, String tickerSymbol, int quantity) {
        // Retrieve the user's portfolio or create a new one if it doesn't exist.
        if (userPortfolios.containsKey(username)) {
            final Map<String, Integer> portfolio = userPortfolios.get(username);
            portfolio.put(tickerSymbol, portfolio.get(tickerSymbol) + quantity);
        }
    }

    /**
     * Saves a new user in the system.
     *
     * @param user The user to be saved.
     */
    public void saveUser(User user) {
        userPortfolios.putIfAbsent(user.getName(), new HashMap<>());
    }

    /**
     * Adds a new stock to the system.
     *
     * @param stock The Stock object to be added.
     */
    public void saveStock(Stock stock) {
        if (!stocks.containsKey(stock.getTickerSymbol())) {
            stocks.put(stock.getTickerSymbol(), stock);
        }
    }
}
