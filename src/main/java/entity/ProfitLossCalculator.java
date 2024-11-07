package entity;

import data_access.DBStockDataAccessObject;

public class ProfitLossCalculator {
    private Portfolio portfolio;

    public ProfitLossCalculator(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    // Placeholder method to retrieve purchase price of a stock
    private double getPurchasePrice(Stock stock) {
        // Placeholder logic - needs to be implemented later with actual data
        return stock.getCost();
    }

    // Placeholder method to retrieve the number of shares owned for a stock
    private int getNumShares(Stock stock) {
        // Placeholder - return 1 share for simplicity; needs to be implemented later with actual data
        return 1;
    }

    // Method to calculate total profit or loss
    public double calculateTotalProfitLoss() {
        double totalProfitLoss = 0.0;

        // Loop through each stock in the portfolio
        for (Stock stock : portfolio.getStocks()) {
            double purchasePrice = getPurchasePrice(stock);
            double currentPrice = DBStockDataAccessObject.getCurrentPrice(stock.getTickerSymbol());
            int numShares = getNumShares(stock);

            // Calculate profit/loss for this stock and add to total
            totalProfitLoss += (currentPrice - purchasePrice) * numShares;
        }

        return totalProfitLoss;
    }

    // Method to calculate profit or loss for a specific stock
    public double calculateStockProfitLoss(String tickerSymbol) {
        for (Stock stock : portfolio.getStocks()) {
            if (stock.getTickerSymbol().equalsIgnoreCase(tickerSymbol)) {
                double purchasePrice = getPurchasePrice(stock);
                double currentPrice = DBStockDataAccessObject.getCurrentPrice(tickerSymbol);
                int numShares = getNumShares(stock);

                return (currentPrice - purchasePrice) * numShares;
            }
        }
        System.out.println("Stock with ticker " + tickerSymbol + " not found in portfolio.");
        return 0.0;
    }
}