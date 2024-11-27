package interface_adapter.profit_loss;

import use_case.profit_loss.ProfitLossInputBoundary;
import use_case.profit_loss.ProfitLossInputData;

import java.util.Map;

public class ProfitLossController {
    private final ProfitLossInputBoundary profitLossInteractor;

    public ProfitLossController(ProfitLossInputBoundary profitLossInteractor) {
        this.profitLossInteractor = profitLossInteractor;
    }

    // Method to calculate total profit/loss
    public void calculateTotalProfitLoss(String username, Map<String, Double> stockPrices) {
        // Create input data for the interactor
        ProfitLossInputData inputData = new ProfitLossInputData(username);

        // Call the interactor with the input data and stock prices
        profitLossInteractor.calculateTotalProfitLoss(inputData, stockPrices);
    }

    // Method to calculate profit/loss for a specific stock
    public void calculateStockProfitLoss(String username, String tickerSymbol, double currentPrice) {
        // Create input data for the interactor
        ProfitLossInputData inputData = new ProfitLossInputData(username);

        // Call the interactor with the input data, stock ticker, and current price
        profitLossInteractor.calculateStockProfitLoss(inputData, tickerSymbol, currentPrice);
    }
}