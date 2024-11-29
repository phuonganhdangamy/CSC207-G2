package use_case.profit_loss;

/**
 * Data transfer object for the Profit Loss use case.
 * Encapsulates the user ID for identifying a portfolio.
 */
public class ProfitLossInputData {

    private final String username;

    /**
     * Creates a new ProfitLossInputData object.
     *
     * @param username the unique ID of the user
     */
    public ProfitLossInputData(String username) {
        this.username = username;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public String getUsername() {
        return username;
    }
    
}
