package use_case.profit_loss;

/**
 * Data transfer object for the Profit Loss use case.
 * Encapsulates the profit/loss calculation results.
 */
public class ProfitLossOutputData {

    private final double profitLoss;

    /**
     * Creates a new ProfitLossOutputData object.
     *
     * @param profitLoss the calculated profit/loss
     */
    public ProfitLossOutputData(double profitLoss) {
        this.profitLoss = profitLoss;
    }

    /**
     * Gets the calculated profit/loss.
     *
     * @return the profit/loss value
     */
    public double getProfitLoss() {
        return profitLoss;
    }
}
