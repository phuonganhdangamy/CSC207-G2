package entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a portfolio of stocks owned by a user.
 * Manages stock addition, removal, and provides details on stocks held.
 */
public class Portfolio {
    private User user;
    private List<Stock> stocks;

    /**
     * Creates a portfolio for a specific user.
     *
     * @param user The user to whom this portfolio belongs.
     */
    public Portfolio(User user) {
        this.user = user;
        this.stocks = new ArrayList<>();
    }

    /**
     * Retrieves the list of stocks in the portfolio.
     *
     * @return A list of Stock objects in the portfolio.
     */
    public List<Stock> getStocks() {
        return stocks;
    }

    /**
     * Adds a stock (representing one share) to the portfolio.
     * Deducts the cost of the stock from the user's balance.
     *
     * @param stock The Stock to be added.
     */
    // Method to add a stock (which represents 1 share) to the portfolio
    public void addStock(Stock stock) {
        stocks.add(stock);
        // Deduct the cost of the stock from the user's balance when buying
        user.setBalance(user.getBalance() - stock.getCost());
    }

    /**
     * Removes a stock from the portfolio based on its ticker symbol.
     * Adds the selling price to the user's balance when the stock is sold.
     *
     * @param tickerSymbol The ticker symbol of the stock to be removed.
     * @param sellingPrice The price at which the stock is being sold.
     * @return true if the stock was found and removed; false otherwise.
     */
    // Revised method to remove a stock by ticker symbol and update balance
    public boolean removeStock(String tickerSymbol, double sellingPrice) {
        // Track if the stock was removed to follow CheckStyle (1 return statement)
        boolean stockRemoved = false;
        for (Stock stock : stocks) {
            if (stock.getTickerSymbol().equals(tickerSymbol)) {
                stocks.remove(stock);
                user.setBalance(user.getBalance() + sellingPrice);
                stockRemoved = true;
                // Exit loop as the stock has been removed
                break;
            }
        }
        return stockRemoved;
    }

    /**
     * Counts the number of shares owned for a specific stock ticker symbol.
     *
     * @param tickerSymbol The ticker symbol of the stock.
     * @return The number of shares owned for the given ticker symbol.
     */
    // Method to get the number of shares for a specific ticker symbol
    public int getShareCount(String tickerSymbol) {
        int count = 0;
        for (Stock stock : stocks) {
            if (stock.getTickerSymbol().equals(tickerSymbol)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves all unique ticker symbols for the stocks in the portfolio.
     *
     * @return A Set of unique ticker symbols for stocks in the portfolio.
     */
    public Set<String> getTickerSymbols() {
        final Set<String> symbols = new HashSet<>();
        for (Stock stock : stocks) {
            symbols.add(stock.getTickerSymbol());
        }
        return symbols;
    }
}
