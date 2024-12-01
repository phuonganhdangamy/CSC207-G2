package entity;

import java.util.Map;

/**
 * Calculator for profit/loss.
 */
public class ProfitLossCalculator {
    private final Portfolio portfolio;

    /**
     * Constructs a ProfitLossCalculator with the specified portfolio.
     *
     * @param portfolio the portfolio to calculate profit/loss for
     */
    public ProfitLossCalculator(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    /**
     * Calculates the total profit/loss for the portfolio based on a map of stock profit/loss values.
     *
     * @param stockProfitLoss a map where the key is the stock ticker symbol and the value is the profit/loss
     * @return the total profit/loss
     */
    public double calculateTotalProfitLoss(Map<String, Double> stockProfitLoss) {
        double totalProfitLoss = 0.0;

        // Sum up all profit/loss values from the map
        for (double profit : stockProfitLoss.values()) {
            totalProfitLoss += profit;
        }

        return totalProfitLoss;
    }

    /**
     * Calculates the profit/loss for a specific stock.
     *
     * @param tickerSymbol the stock ticker symbol
     * @param currentPrice the current price of the stock
     * @return the profit/loss for the specific stock
     * @throws IllegalArgumentException if the portfolio has no shares of the given stock
     */
    public double calculateStockProfitLoss(String tickerSymbol, double currentPrice) {
        final int shareCount = portfolio.getShareCount(tickerSymbol);
        if (shareCount == 0) {
            throw new IllegalArgumentException("No shares of " + tickerSymbol + " found in the portfolio.");
        }

        final double totalCost = calculateEachTotalCost(tickerSymbol);
        return currentPrice * shareCount - totalCost;
    }

    /**
     * Helper method to calculate the total cost of stocks for a specific ticker symbol.
     *
     * @param tickerSymbol the stock ticker symbol
     * @return the total cost of stocks with the given ticker symbol
     * @throws IllegalArgumentException if no stocks with the given ticker symbol are found
     */
    private double calculateEachTotalCost(String tickerSymbol) {
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
        return totalCost;
    }
}
