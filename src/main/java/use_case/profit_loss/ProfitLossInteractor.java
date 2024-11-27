package use_case.profit_loss;

import entity.Portfolio;
import entity.ProfitLossCalculator;
import entity.Stock;

import java.util.HashMap;
import java.util.Map;

/**
 * Interactor for the Profit Loss use case.
 */
public class ProfitLossInteractor implements ProfitLossInputBoundary {

    private final ProfitLossDataAccessInterface dataAccess;
    private final ProfitLossOutputBoundary outputBoundary;

    public ProfitLossInteractor(
            ProfitLossDataAccessInterface dataAccess,
            ProfitLossOutputBoundary outputBoundary
    ) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    /**
     * New execute method to calculate both total and stock-specific profit/loss,
     * and pass one unified result to the presenter.
     */
    public void execute(String tickerSymbol, double currentPrice) {
        // Fetch the user's portfolio
        Portfolio portfolio = dataAccess.getCurrentUser().getPortfolio();

        // Build the stock prices map dynamically from the portfolio
        Map<String, Double> stockPrices = new HashMap<>();
        for (Stock stock : portfolio.getStocks()) {
            stockPrices.put(stock.getTickerSymbol(), stock.getCost()); // Assuming 'getCost()' gives purchase price
        }
        stockPrices.put(tickerSymbol, currentPrice); // Include the specific stock price

        // Use the ProfitLossCalculator to calculate results
        ProfitLossCalculator calculator = new ProfitLossCalculator(portfolio);
        double totalProfitLoss = calculator.calculateTotalProfitLoss(stockPrices);
        double stockProfitLoss = calculator.calculateStockProfitLoss(tickerSymbol, currentPrice);

        // Combine results into a single output data object
        ProfitLossOutputData outputData = new ProfitLossOutputData(totalProfitLoss, stockProfitLoss, tickerSymbol);

        // Send the unified result to the presenter
        outputBoundary.presentCombinedProfitLoss(outputData);
    }

    @Override
    @Deprecated
    public void calculateTotalProfitLoss(ProfitLossInputData inputData, Map<String, Double> stockPrices) {
        // display the results for debugging when changing code
        throw new UnsupportedOperationException("Use execute() instead.");
    }

    @Override
    @Deprecated
    public void calculateStockProfitLoss(ProfitLossInputData inputData, String tickerSymbol, double currentPrice) {
        // display the results for debugging when changing code
        throw new UnsupportedOperationException("Use execute() instead.");
    }
}