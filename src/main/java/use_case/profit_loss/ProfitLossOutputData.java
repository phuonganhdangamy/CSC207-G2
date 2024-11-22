package use_case.profit_loss;

public class ProfitLossOutputData {
    private final double profitLoss;

    public ProfitLossOutputData(double profitLoss) {
        this.profitLoss = profitLoss;
    }

    public double getProfitLoss() {
        return profitLoss;
    }
}
