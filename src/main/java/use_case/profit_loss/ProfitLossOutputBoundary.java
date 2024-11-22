package use_case.profit_loss;

public interface ProfitLossOutputBoundary {
    void presentTotalProfitLoss(ProfitLossOutputData outputData);
    void presentStockProfitLoss(ProfitLossOutputData outputData, String tickerSymbol);
}
