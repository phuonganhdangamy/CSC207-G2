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
    @Override
    public void execute() {
        // Fetch the user's portfolio
        Portfolio portfolio = dataAccess.getCurrentUser().getPortfolio();

        // Build the stock prices map dynamically from the portfolio
        Map<String, Double> stockPrices = new HashMap<>();
        for (Stock stock : portfolio.getStocks()) {
            stockPrices.put(stock.getTickerSymbol(), stock.getCost()); // Assuming 'getCost()' gives purchase price
        }

        // Use the ProfitLossCalculator to calculate results
        ProfitLossCalculator calculator = new ProfitLossCalculator(portfolio);
        double totalProfitLoss = calculator.calculateTotalProfitLoss(stockPrices);

        // Calculate stock-specific profit/loss
        Map<String, Double> stockProfitLosses = new HashMap<>();
        for (String ticker : stockPrices.keySet()) {
            stockProfitLosses.put(ticker, calculator.calculateStockProfitLoss(ticker, stockPrices.get(ticker)));
        }

        // Combine results into a single output data object
        ProfitLossOutputData outputData = new ProfitLossOutputData(totalProfitLoss, stockProfitLosses);

        // Send the unified result to the presenter
        outputBoundary.success(outputData);
    }
}
