package use_case.profit_loss;

import entity.Portfolio;
import entity.ProfitLossCalculator;

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

    @Override
    public void calculateTotalProfitLoss(ProfitLossInputData inputData, Map<String, Double> stockPrices) {
        // Fetch the user's portfolio
        Portfolio portfolio = dataAccess.getPortfolio(inputData.getUsername());

        // Use the calculator to calculate total profit/loss
        ProfitLossCalculator calculator = new ProfitLossCalculator(portfolio);
        double totalProfitLoss = calculator.calculateTotalProfitLoss(stockPrices);

        // Create output data and send to the output boundary
        ProfitLossOutputData outputData = new ProfitLossOutputData(totalProfitLoss);
        outputBoundary.presentTotalProfitLoss(outputData);
    }

    @Override
    public void calculateStockProfitLoss(ProfitLossInputData inputData, String tickerSymbol, double currentPrice) {
        // Fetch the user's portfolio
        Portfolio portfolio = dataAccess.getPortfolio(inputData.getUsername());

        // Use the calculator to calculate profit/loss for the specific stock
        ProfitLossCalculator calculator = new ProfitLossCalculator(portfolio);
        double stockProfitLoss = calculator.calculateStockProfitLoss(tickerSymbol, currentPrice);

        // Create output data and send to the output boundary
        ProfitLossOutputData outputData = new ProfitLossOutputData(stockProfitLoss);
        outputBoundary.presentStockProfitLoss(outputData, tickerSymbol);
    }
}
