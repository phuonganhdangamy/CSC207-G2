package use_case.profit_loss;

/**
 * Output Boundary for the Profit Loss use case.
 * Declares methods for presenting calculated results to the user.
 */
public interface ProfitLossOutputBoundary {

    /**
     * Presents the combined profit/loss results for the user's portfolio and a specific stock.
     *
     * @param outputData the combined profit/loss data
     */
    void presentCombinedProfitLoss(ProfitLossOutputData outputData);

    // display the results for debugging when changing code
    default void presentTotalProfitLoss(ProfitLossOutputData outputData) {
        throw new UnsupportedOperationException("Use presentCombinedProfitLoss instead.");
    }

    // display the results for debugging when changing code
    default void presentStockProfitLoss(ProfitLossOutputData outputData, String tickerSymbol) {
        throw new UnsupportedOperationException("Use presentCombinedProfitLoss instead.");
    }
}