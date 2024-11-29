package use_case.profit_loss;

import java.util.Map;

/**
 * Data transfer object for the Profit Loss use case.
 * Encapsulates the profit/loss calculation results.
 */
public class ProfitLossOutputData {

    private final double totalProfitLoss;
    private final Map<String, Double> stockProfitLosses;

    public ProfitLossOutputData(double totalProfitLoss, Map<String, Double> stockProfitLosses) {
        this.totalProfitLoss = totalProfitLoss;
        this.stockProfitLosses = stockProfitLosses;
    }

    public double getTotalProfitLoss() {
        return totalProfitLoss;
    }

    public Map<String, Double> getStockProfitLosses() {
        return stockProfitLosses;
    }
}