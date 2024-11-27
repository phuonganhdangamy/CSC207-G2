package use_case.profit_loss;

import entity.Portfolio;
import entity.ProfitLossCalculator;

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
     * Execute the Profit/Loss calculation for both total portfolio and a specific stock.
     *
     * @param inputData    The input data containing the username and ticker (optional).
     * @param stockPrices  The current prices of stocks mapped by ticker.
     * @param tickerSymbol The specific stock ticker to calculate, or null for only total.
     */

    @Override
    public void execute(ProfitLossInputData inputData, Map<String, Double> stockPrices, String tickerSymbol) {
        // Fetch the user's portfolio
        Portfolio portfolio = dataAccess.getCurrentUser().getPortfolio();

        // Use the calculator for profit/loss
        ProfitLossCalculator calculator = new ProfitLossCalculator(portfolio);

        // Calculate total profit/loss
        double totalProfitLoss = calculator.calculateTotalProfitLoss(stockPrices);

        // Calculate specific stock profit/loss if ticker is provided
        double stockProfitLoss = 0.0;
        if (tickerSymbol != null && stockPrices.containsKey(tickerSymbol)) {
            stockProfitLoss = calculator.calculateStockProfitLoss(
                    tickerSymbol, stockPrices.get(tickerSymbol)
            );
        }

        // Create output data and send to the output boundary
        ProfitLossOutputData outputData = new ProfitLossOutputData(totalProfitLoss, stockProfitLoss, tickerSymbol);
        outputBoundary.presentProfitLoss(outputData);
    }
}