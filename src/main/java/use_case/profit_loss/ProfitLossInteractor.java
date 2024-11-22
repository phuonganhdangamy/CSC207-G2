package use_case.profit_loss;

import entity.Portfolio;
import entity.ProfitLossCalculator;

/**
 * Interactor for the Profit Loss use case.
 */
public class ProfitLossInteractor implements ProfitLossInputBoundary {

    private final ProfitLossDataAccessInterface dataAccess;
    private final ProfitLossOutputBoundary outputBoundary;

    public ProfitLossInteractor(ProfitLossDataAccessInterface dataAccess, ProfitLossOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void calculateTotalProfitLoss(ProfitLossInputData inputData) {
        Portfolio portfolio = dataAccess.getPortfolio(inputData.getUserId());
        ProfitLossCalculator calculator = new ProfitLossCalculator(portfolio);
        double totalProfitLoss = calculator.calculateTotalProfitLoss();
        ProfitLossOutputData outputData = new ProfitLossOutputData(totalProfitLoss);
        outputBoundary.presentTotalProfitLoss(outputData);
    }

    @Override
    public void calculateStockProfitLoss(ProfitLossInputData inputData, String tickerSymbol) {
        Portfolio portfolio = dataAccess.getPortfolio(inputData.getUserId());
        ProfitLossCalculator calculator = new ProfitLossCalculator(portfolio);
        double stockProfitLoss = calculator.calculateStockProfitLoss(tickerSymbol);
        ProfitLossOutputData outputData = new ProfitLossOutputData(stockProfitLoss);
        outputBoundary.presentStockProfitLoss(outputData, tickerSymbol);
    }
}

