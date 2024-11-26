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
     */
    void calculateTotalProfitLoss(ProfitLossInputData inputData);

    /**
     * Calculates the profit/loss for a specific stock in the user's portfolio using the current price.
     *
     * @param inputData    the input data containing the user's ID
     * @param tickerSymbol the ticker symbol of the stock to calculate profit/loss for
     * @param currentPrice the current price of the stock
     */
    void calculateStockProfitLoss(ProfitLossInputData inputData, String tickerSymbol, double currentPrice);
}
