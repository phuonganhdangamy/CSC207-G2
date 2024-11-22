package use_case.profit_loss;

public class ProfitLossOutputData {
    private final double totalProfitLoss;

    public ProfitLossOutputData(double totalProfitLoss) {
        this.totalProfitLoss = totalProfitLoss;
    }

    public double getTotalProfitLoss() {
        return totalProfitLoss;
    }
}
