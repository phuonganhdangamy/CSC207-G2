package use_case.profit_loss;

import java.util.Map;

/**
 * Data transfer object for the Profit Loss use case.
 * Encapsulates the profit/loss calculation results.
 */
public class ProfitLossOutputData {

    private final double totalProfitLoss;
    private final Map<String, Double> stockProfitLosses;

    /**
     * Constructs a new ProfitLossOutputData object.
     *
     * @param totalProfitLoss  the total profit/loss for the portfolio.
     * @param stockProfitLosses the profit/loss for individual stocks.
     */
    public ProfitLossOutputData(double totalProfitLoss, Map<String, Double> stockProfitLosses) {
        this.totalProfitLoss = totalProfitLoss;
        this.stockProfitLosses = stockProfitLosses;
    }

    /**
     * Retrieves the total profit/loss.
     *
     * @return the total profit/loss.
     */
    public double getTotalProfitLoss() {
        return totalProfitLoss;
    }

    /**
     * Retrieves the profit/loss for each stock.
     *
     * @return a map of stock ticker symbols to their profit/loss.
     */
    public Map<String, Double> getStockProfitLosses() {
        return stockProfitLosses;
    }
}