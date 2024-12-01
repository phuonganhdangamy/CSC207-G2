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

    /**
     * Sends the unified profit/loss results to the presenter.
     *
     * @param outputData the profit/loss calculation results.
     */
    void success(ProfitLossOutputData outputData);
}
