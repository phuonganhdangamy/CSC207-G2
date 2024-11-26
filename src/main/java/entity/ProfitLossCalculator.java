package entity;

import data_access.DBStockDataAccessObject;

import java.util.Map;

/**
 * Calculator for profit/loss.
 */
public class ProfitLossCalculator {
    private final Portfolio portfolio;

    public ProfitLossCalculator(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    // Calculate total profit/loss for the portfolio
    public double calculateTotalProfitLoss() {
        double totalProfitLoss = 0.0;

        for (Stock stock : portfolio.getStocks()) {
            String tickerSymbol = stock.getTickerSymbol();
            double currentPrice = new DBStockDataAccessObject().getCost(tickerSymbol);
            double purchasePrice = stock.getCost();
            int shareCount = portfolio.getShareCount(tickerSymbol);

            double profitLossPerStock = (currentPrice - purchasePrice) * shareCount;
            totalProfitLoss += profitLossPerStock;
        }

        return totalProfitLoss;
    }

    // Calculate profit/loss for a specific stock
    public double calculateStockProfitLoss(String tickerSymbol, double currentPrice) {
        double totalProfitLossForStock = 0.0;

        for (Stock stock : portfolio.getStocks()) {
            if (stock.getTickerSymbol().equalsIgnoreCase(tickerSymbol)) {
                double purchasePrice = stock.getCost();
                int shareCount = portfolio.getShareCount(tickerSymbol);

                totalProfitLossForStock = (currentPrice - purchasePrice) * shareCount;
                break;
            }
        }

        return totalProfitLossForStock;
    }
}
