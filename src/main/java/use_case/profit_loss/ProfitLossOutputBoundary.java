package use_case.profit_loss;

/**
 * Output Boundary for the Profit Loss use case.
 * Declares methods for presenting calculated results to the user.
 */
public interface ProfitLossOutputBoundary {

    /**
     * Presents the total profit/loss for the user's portfolio.
     *
     * @param outputData the data containing profit/loss
     */

    void presentProfitLoss(ProfitLossOutputData outputData);
}
