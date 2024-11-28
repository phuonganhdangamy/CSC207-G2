package entity;

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
    public double calculateTotalProfitLoss(Map<String, Double> stockPrices) {
        double totalProfitLoss = 0.0;

        // Iterate through stocks in the portfolio
        for (String tickerSymbol : stockPrices.keySet()) {
            int shareCount = portfolio.getShareCount(tickerSymbol);
            if (shareCount > 0) {
                double currentPrice = stockPrices.get(tickerSymbol);
                double averageCost = calculateAverageCost(tickerSymbol);

                totalProfitLoss += (currentPrice - averageCost) * shareCount;
            }
        }

        return totalProfitLoss;
    }

    // Calculate profit/loss for a specific stock
    public double calculateStockProfitLoss(String tickerSymbol, double currentPrice) {
        int shareCount = portfolio.getShareCount(tickerSymbol);
        if (shareCount == 0) {
            throw new IllegalArgumentException("No shares of " + tickerSymbol + " found in the portfolio.");
        }

        double averageCost = calculateAverageCost(tickerSymbol);

        return (currentPrice - averageCost) * shareCount;
    }

    // Helper method to calculate the average cost of stocks for a specific ticker symbol
    private double calculateAverageCost(String tickerSymbol) {
        double totalCost = 0.0;
        int count = 0;

        for (Stock stock : portfolio.getStocks()) {
            if (stock.getTickerSymbol().equals(tickerSymbol)) {
                totalCost += stock.getCost();
                count++;
            }
        }

        if (count == 0) {
            throw new IllegalArgumentException("No stocks with ticker symbol " + tickerSymbol + " found.");
        }

        return totalCost / count;
    }
}
