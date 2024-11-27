package interface_adapter.profit_loss;

import use_case.profit_loss.ProfitLossInputBoundary;
import use_case.profit_loss.ProfitLossInputData;

import java.util.Map;

public class ProfitLossController {
    private final ProfitLossInputBoundary profitLossInteractor;

    public ProfitLossController(ProfitLossInputBoundary profitLossInteractor) {
        this.profitLossInteractor = profitLossInteractor;
    }

    /**
     * Calculates the total profit/loss for the user's portfolio.
     *
     * @param username    the user's username
     * @param stockPrices a map of ticker symbols to their current prices
     */
    public void calculateTotalProfitLoss(String username, Map<String, Double> stockPrices) {
        // Create input data for the interactor
        ProfitLossInputData inputData = new ProfitLossInputData(username);

        // Call the interactor with input data and stock prices (tickerSymbol is null for total profit/loss)
        profitLossInteractor.execute(inputData, stockPrices, null);
    }

    /**
     * Calculates the profit/loss for a specific stock.
     *
     * @param username      the user's username
     * @param tickerSymbol  the ticker symbol of the stock
     * @param stockPrices   a map of ticker symbols to their current prices
     */
    public void calculateStockProfitLoss(String username, String tickerSymbol, Map<String, Double> stockPrices) {
        // Create input data for the interactor
        ProfitLossInputData inputData = new ProfitLossInputData(username);

        // Call the interactor with input data, stock prices, and the specific ticker symbol
        profitLossInteractor.execute(inputData, stockPrices, tickerSymbol);
    }
}
