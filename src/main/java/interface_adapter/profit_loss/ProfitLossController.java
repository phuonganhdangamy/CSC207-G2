package interface_adapter.profit_loss;

import use_case.profit_loss.ProfitLossInputBoundary;

/**
 * The controller for the Profit Loss Use Case.
 */
public class ProfitLossController {
    private final ProfitLossInputBoundary profitLossInteractor;

    /**
     * Creates a new ProfitLossController.
     *
     * @param profitLossInteractor the interactor for handling profit/loss calculations
     */
    public ProfitLossController(ProfitLossInputBoundary profitLossInteractor) {
        this.profitLossInteractor = profitLossInteractor;
    }

    /**
     * Unified method to calculate profit/loss for the portfolio and all stocks.
     */
    public void execute() {
        profitLossInteractor.execute();
    }
}
