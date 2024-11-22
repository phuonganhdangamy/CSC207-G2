package use_case.profit_loss;

public class ProfitLossInputData {
    private final String userId;

    public ProfitLossInputData(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}