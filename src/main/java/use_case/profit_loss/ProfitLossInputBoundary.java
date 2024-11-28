package use_case.profit_loss;

import java.util.Map;

/**
 * Input Boundary for the Profit Loss use case.
 * Declares methods for calculating total profit/loss or stock-specific profit/loss.
 */
public interface ProfitLossInputBoundary {

    /**
     * Unified method to calculate total profit/loss and profit/loss for individual stocks.
     */
    void execute();
}