package interface_adapter.view_owned_stock;

import java.util.HashMap;
import java.util.Map;

/**
 * ViewModel for the List Stocks use case.
 * Holds the data to be displayed in the ListStocksView.
 */
public class ListStocksViewModel {
    private Map<String, Integer> stocksOwned; // Ticker -> Shares
    private Map<String, Double> profitLoss;  // Ticker -> Profit/Loss

    public ListStocksViewModel() {
        this.stocksOwned = new HashMap<>();
        this.profitLoss = new HashMap<>();
    }

    /**
     * Sets the stocks owned by the user.
     *
     * @param stocksOwned a map of stock tickers to the number of shares owned.
     */
    public void setStocksOwned(Map<String, Integer> stocksOwned) {
        this.stocksOwned = stocksOwned;
    }

    /**
     * Sets the profit/loss data for the user's stocks.
     *
     * @param profitLoss a map of stock tickers to profit/loss values.
     */
    public void setProfitLoss(Map<String, Double> profitLoss) {
        this.profitLoss = profitLoss;
    }

    /**
     * Gets the stocks owned by the user.
     *
     * @return a map of stock tickers to the number of shares owned.
     */
    public Map<String, Integer> getStocksOwned() {
        return stocksOwned;
    }

    /**
     * Gets the profit/loss data for the user's stocks.
     *
     * @return a map of stock tickers to profit/loss values.
     */
    public Map<String, Double> getProfitLoss() {
        return profitLoss;
    }
}