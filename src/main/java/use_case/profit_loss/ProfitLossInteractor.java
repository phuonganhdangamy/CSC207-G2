package use_case.profit_loss;

import entity.Portfolio;
import entity.ProfitLossCalculator;

public class ProfitLossInteractor implements ProfitLossInputBoundary {
    private final ProfitLossDataAccessInterface dataAccess;
    private final ProfitLossOutputBoundary outputBoundary;

    public ProfitLossInteractor(ProfitLossDataAccessInterface dataAccess, ProfitLossOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void calculateTotalProfitLoss(ProfitLossInputData inputData) {
        // Fetch the Portfolio for the given user
        Portfolio portfolio = dataAccess.getPortfolio(inputData.getUserId());

        // Use the ProfitLossCalculator to calculate total profit/loss
        ProfitLossCalculator calculator = new ProfitLossCalculator(portfolio);
        double totalProfitLoss = calculator.calculateTotalProfitLoss();

        // Pass the result to the output boundary
        ProfitLossOutputData outputData = new ProfitLossOutputData(totalProfitLoss);
        outputBoundary.presentTotalProfitLoss(outputData);
    }

    @Override
    public void calculateStockProfitLoss(ProfitLossInputData inputData, String tickerSymbol) {
        // Fetch the Portfolio for the given user
        Portfolio portfolio = dataAccess.getPortfolio(inputData.getUserId());

        // Use the ProfitLossCalculator to calculate profit/loss for a specific stock
        ProfitLossCalculator calculator = new ProfitLossCalculator(portfolio);
        double stockProfitLoss = calculator.calculateStockProfitLoss(tickerSymbol);

        // Pass the result to the output boundary
        ProfitLossOutputData outputData = new ProfitLossOutputData(stockProfitLoss);
        outputBoundary.presentStockProfitLoss(outputData, tickerSymbol);
    }
}
