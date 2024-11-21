package data_access;

import entity.Stock;
import entity.User;
import use_case.buy_stock.BuyStockUserDataAccessInterface;
import use_case.find_stock.FindStockDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStockDataAccessObject implements FindStockDataAccessInterface {

    // Stores the stocks with their ticker symbol as the key
    private final Map<String, Stock> stocks = new HashMap<>();

    // Stores the user’s portfolio where the key is the username and value is the owned stocks
    private final Map<String, Map<String, Integer>> userPortfolios = new HashMap<>();

    @Override
    public double getCost(String tickerSymbol) {
        Stock stock = stocks.get(tickerSymbol);
        if (stock != null) {
            return stock.getCost();
        }
        return 0.0; // if stock doesn't exist
    }

    @Override
    public boolean isStockExist(String tickerSymbol) {
        return stocks.containsKey(tickerSymbol);
    }

    public Map<String, Integer> getUserPortfolio(User user) {
        return userPortfolios.getOrDefault(user.getName(), new HashMap<>());
    }

    // Helper method to get all stocks owned by a user
    public Map<String, Integer> getUserStocks(String username) {
        if (userPortfolios.containsKey(username)) {
            return userPortfolios.get(username);
        } else {
            System.out.println("User with username \"" + username + "\" does not exist.");
            return new HashMap<>();
        }
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
            Map<String, Integer> portfolio = userPortfolios.get(username);
            portfolio.put(tickerSymbol, portfolio.get(tickerSymbol) + quantity);
        }
    }

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
