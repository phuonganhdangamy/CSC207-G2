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
     * Execute the Profit/Loss calculation for both total portfolio and a specific stock.
     *
     * @param inputData    The input data containing the username and ticker (optional).
     * @param stockPrices  The current prices of stocks mapped by ticker.
     * @param tickerSymbol The specific stock ticker to calculate, or null for only total.
     */
    public void execute(ProfitLossInputData inputData, Map<String, Double> stockPrices, String tickerSymbol) {
        // Fetch the user's portfolio
        Portfolio portfolio = dataAccess.getCurrentUser().getPortfolio();

        // Create/complete the stockPrices map
        Map<String, Double> completeStockPrices = new HashMap<>();
        if (stockPrices != null) {
            completeStockPrices.putAll(stockPrices); // Start with the provided map
        }

        // Add missing stock prices using the DataAccess
        for (Stock stock : portfolio.getStocks()) {
            String ticker = stock.getTickerSymbol();
            if (!completeStockPrices.containsKey(ticker)) {
                // Retrieve current price using the DataAccess interface
                double currentPrice = dataAccess.getCost(ticker);
                completeStockPrices.put(ticker, currentPrice);
            }
        }

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