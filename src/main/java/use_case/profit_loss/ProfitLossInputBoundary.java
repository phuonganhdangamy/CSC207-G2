package use_case.profit_loss;

/**
 * Input Boundary for the Profit Loss use case.
 * Declares methods for calculating total profit/loss or stock-specific profit/loss.
 */
public interface ProfitLossInputBoundary {

    /**
     * Calculates the total profit/loss for a user's portfolio.
     *
     * @param inputData the input data containing the user's ID
     */
    void calculateTotalProfitLoss(ProfitLossInputData inputData);

    /**
     * Calculates the profit/loss for a specific stock in the user's portfolio.
     *
     * @param inputData    the input data containing the user's ID
     * @param tickerSymbol the ticker symbol of the stock to calculate profit/loss for
     */
    void calculateStockProfitLoss(ProfitLossInputData inputData, String tickerSymbol);
}

