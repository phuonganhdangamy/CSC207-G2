package use_case.profit_loss;

/**
 * Output Boundary for the Profit Loss use case.
 * Declares methods for presenting calculated results to the user.
 */
public interface ProfitLossOutputBoundary {

    /**
     * Presents the total profit/loss for the user's portfolio.
     *
     * @param outputData the data containing the total profit/loss
     */
    void presentTotalProfitLoss(ProfitLossOutputData outputData);

    /**
     * Presents the profit/loss for a specific stock in the user's portfolio.
     *
     * @param outputData    the data containing the profit/loss
     * @param tickerSymbol the ticker symbol of the stock
     */
    void presentStockProfitLoss(ProfitLossOutputData outputData, String tickerSymbol);
}
