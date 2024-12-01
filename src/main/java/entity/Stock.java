package entity;

import data_access.DBStockDataAccessObject;

/**
 * The Stock class represents a stock from an external API, such as AlphaVantage, where the user provides
 * a ticker symbol (e.g., "IBM") and the class retrieves the current stock price (daily opening price).
 * The Stock class holds the information about the stock ticker symbol and its current cost, as well as
 * the ability to retrieve the current price from the stock database using an external data access object.
 * Responsibilities:
 * - Stores the ticker symbol and current cost of a stock.
 * - Retrieves the stock's current price from an external data source (AlphaVantage API).
 * Constructor(s):
 * - Stock(String tickerSymbol, double cost): Initializes a Stock object with the provided ticker symbol and cost.
 * - Stock(String tickerSymbol): Retrieves the current stock price for the given ticker symbol from the API.
 * Methods:
 * - getTickerSymbol(): Returns the ticker symbol of the stock.
 * - getCost(): Returns the current cost (price) of the stock.
 * - setCost(double cost): Sets the cost of the stock.
 * The class adheres to the Clean Architecture approach by only using the DBStockDataAccessObject in the
 * interactor layer, keeping the entity layer free from external dependencies.
 */

public class Stock {
    private final String tickerSymbol;
    private double cost;

    /**
     * Constructor for initializing a Stock object with the given ticker symbol and cost.
     *
     * @param tickerSymbol The ticker symbol of the stock.
     * @param cost The current price (cost) of the stock.
     */
    public Stock(String tickerSymbol, double cost) {
        this.tickerSymbol = tickerSymbol;
        this.cost = cost;
    }

    /**
     * Constructor for initializing a Stock object with the given ticker symbol by fetching
     * the current price (cost) from an external data source (API).
     *
     * @param tickerSymbol The ticker symbol of the stock.
     */

    public Stock(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
        final DBStockDataAccessObject access = new DBStockDataAccessObject();
        this.cost = access.getCost(tickerSymbol);
    }

    /**
     * Retrieves the ticker symbol of the stock.
     *
     * @return The ticker symbol of the stock.
     */

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    /**
     * Retrieves the current cost (price) of the stock.
     *
     * @return The current cost of the stock.
     */

    public double getCost() {
        return cost;
    }

    /**
     * Sets the current cost (price) of the stock.
     *
     * @param cost The new cost of the stock.
     */

    public void setCost(double cost) {
        this.cost = cost;
    }
}
