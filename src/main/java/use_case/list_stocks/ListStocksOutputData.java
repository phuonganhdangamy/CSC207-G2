package use_case.list_stocks;

import java.util.Map;

/**
 * Represents the output data for the List Stocks use case.
 * Contains a mapping of stock ticker symbols to the number of shares owned.
 */
public class ListStocksOutputData {

    /**
     * A map where the key is the stock ticker symbol,
     * and the value is the number of shares owned.
     * Cannot be null.
     */
    private final Map<String, Integer> stocksOwned;

    /**
     * Constructs an instance of ListStocksOutputData.
     *
     * @param stocksOwned a map of stock ticker symbols to the number of shares owned; must not be null
     * @throws IllegalArgumentException if stocksOwned is null
     */
    public ListStocksOutputData(Map<String, Integer> stocksOwned) {
        if (stocksOwned == null) {
            throw new IllegalArgumentException("stocksOwned cannot be null");
        }
        this.stocksOwned = stocksOwned;
    }

    /**
     * Returns the map of stocks owned.
     *
     * @return a map where the key is the stock ticker symbol and the value is the number of shares owned; never null
     */
    public Map<String, Integer> getStocksOwned() {
        return stocksOwned;
    }
}
