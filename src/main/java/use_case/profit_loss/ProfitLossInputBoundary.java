package use_case.profit_loss;

import java.util.Map;

/**
 * Input Boundary for the Profit Loss use case.
 * Declares methods for calculating total profit/loss or stock-specific profit/loss.
 */
public interface ProfitLossInputBoundary {

    /**
     * Calculates the total profit/loss for a user's portfolio using stock prices provided as input.
     *
     * @param inputData    the input data containing the user's ID
     * @param stockPrices  a map of ticker symbols to their current prices
     * @param tickerSymbol a stock name
     */
    void execute(ProfitLossInputData inputData, Map<String, Double> stockPrices, String tickerSymbol);
}