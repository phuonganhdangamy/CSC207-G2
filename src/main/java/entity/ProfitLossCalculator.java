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
    public double calculateTotalProfitLoss(Map<String, Double> stockProfitLoss) {
        double totalProfitLoss = 0.0;

        // Iterate through stocks in the portfolio
//        for (String tickerSymbol : stockProfitLoss.keySet()) {
//            int shareCount = portfolio.getShareCount(tickerSymbol);
//            if (shareCount > 0) {
//                double currentPrice = stockProfitLoss.get(tickerSymbol);
//                double averageCost = calculateAverageCost(tickerSymbol);
//
//                totalProfitLoss += (currentPrice - averageCost) * shareCount;
//            }
//        }
        // TODO: Replicate the same logic from new formula below for this method. I think you can simply use
        // calculateStockProfitLoss as a helper method -> save time.
        // TODO: I decided to change the parameter (original: stockPrices) so that what I need to do is just to add
        // up everything from the map. No need to compute again!
        for (double profit : stockProfitLoss.values()) {
            totalProfitLoss += profit;
        }

        return totalProfitLoss;
    }

    // Calculate profit/loss for a specific stock
    public double calculateStockProfitLoss(String tickerSymbol, double currentPrice) {
        int shareCount = portfolio.getShareCount(tickerSymbol);
        if (shareCount == 0) {
            throw new IllegalArgumentException("No shares of " + tickerSymbol + " found in the portfolio.");
        }
        // double averageCost = calculateAverageCost(tickerSymbol);

        // return (currentPrice - averageCost) * shareCount;
        double totalCost = calculateEachTotalCost(tickerSymbol);
        return currentPrice * shareCount - totalCost;
        // TODO: Notice how to fixed the formula to calculate P&L for each kind of Stock (by this I meant all shares/
        // stocks with the same tickerSymbol). The correct formula should be: I go through each Stock, get the total
        // values -> Now I have the accumulated price for each kind of Stock.
        // Total current price = current price of 1 share * quantity. Then we subtract those 2 terms, as expected.
        // There shouldn't be anything to do with average, at least for now.
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
