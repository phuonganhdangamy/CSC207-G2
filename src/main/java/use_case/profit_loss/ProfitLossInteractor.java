package use_case.profit_loss;

import entity.Portfolio;
import entity.Stock;

public class ProfitLossInteractor implements ProfitLossInputBoundary {
    private final ProfitLossDataAccessInterface dataAccess;
    private final ProfitLossOutputBoundary outputBoundary;

    public ProfitLossInteractor(ProfitLossDataAccessInterface dataAccess, ProfitLossOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void calculateProfitLoss(ProfitLossInputData inputData) {
        Portfolio portfolio = dataAccess.getPortfolio(inputData.getUserId());
        double totalProfitLoss = 0.0;

        for (Stock stock : portfolio.getStocks()) {
            double currentPrice = dataAccess.getCurrentStockPrice(stock.getTickerSymbol());
            double profitLoss = currentPrice - stock.getCost();
            totalProfitLoss += profitLoss;
        }

        ProfitLossOutputData outputData = new ProfitLossOutputData(totalProfitLoss);
        outputBoundary.presentProfitLoss(outputData);
    }
}