package use_case.profit_loss;

public interface ProfitLossInputBoundary {
    void calculateTotalProfitLoss(ProfitLossInputData inputData);
    void calculateStockProfitLoss(ProfitLossInputData inputData, String tickerSymbol);
}
