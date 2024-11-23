package interface_adapter.profit_loss;

import use_case.profit_loss.ProfitLossInputBoundary;
import use_case.profit_loss.ProfitLossInputData;

public class ProfitLossController {
    private final ProfitLossInputBoundary profitLossInteractor;

    public ProfitLossController(ProfitLossInputBoundary profitLossInteractor) {
        this.profitLossInteractor = profitLossInteractor;
    }

    // Method to calculate total profit/loss
    public void calculateTotalProfitLoss(String username) {
        ProfitLossInputData inputData = new ProfitLossInputData(username);
        profitLossInteractor.calculateTotalProfitLoss(inputData);
    }

    // Method to calculate profit/loss for a specific stock
    public void calculateStockProfitLoss(String username, String tickerSymbol) {
        ProfitLossInputData inputData = new ProfitLossInputData(username);
        profitLossInteractor.calculateStockProfitLoss(inputData, tickerSymbol);
    }
}
