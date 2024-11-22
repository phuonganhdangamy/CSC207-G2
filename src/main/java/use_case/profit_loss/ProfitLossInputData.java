package use_case.profit_loss;

/**
 * Data transfer object for the Profit Loss use case.
 * Encapsulates the user ID for identifying a portfolio.
 */
public class ProfitLossInputData {

    private final String userId;

    /**
     * Creates a new ProfitLossInputData object.
     *
     * @param userId the unique ID of the user
     */
    public ProfitLossInputData(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }
}
