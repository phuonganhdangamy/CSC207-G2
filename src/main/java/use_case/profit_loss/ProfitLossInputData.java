package use_case.profit_loss;

public class ProfitLossInputData {
    private String userId; // User's unique ID

    public ProfitLossInputData(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
