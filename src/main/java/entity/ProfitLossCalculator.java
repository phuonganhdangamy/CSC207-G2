package entity;

import data_access.DBStockDataAccessObject;


public class ProfitLossCalculator {
    private Portfolio portfolio;

    public ProfitLossCalculator(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    // Method to calculate the total profit/loss of the entire portfolio
    public double calculateTotalProfitLoss() {
        double totalProfitLoss = 0.0;

        // Loop through each stock in the portfolio
        for (Stock stock : portfolio.getStocks()) {
            // Fetch the current price from the API
            double currentPrice = DBStockDataAccessObject.getCost(stock);

            // Calculate profit/loss based on the purchase price (cost) and current price
            double purchasePrice = stock.getCost(); // The price at the time of purchase
            double profitLossPerStock = currentPrice - purchasePrice;

            // Add the profit/loss for this stock (since each Stock object represents 1 share)
            totalProfitLoss += profitLossPerStock;
        }

        return totalProfitLoss;
    }

    // Method to calculate profit/loss for a specific stock by its ticker symbol
    public double calculateStockProfitLoss(String tickerSymbol) {
        double totalProfitLossForStock = 0.0;

        // Loop through each stock in the portfolio
        for (Stock stock : portfolio.getStocks()) {
            // Check if the ticker symbol matches
            if (stock.getTickerSymbol().equalsIgnoreCase(tickerSymbol)) {
                // Fetch the current price from the API
                double currentPrice = DBStockDataAccessObject.getCost(stock);

                // Calculate profit/loss for this stock
                double purchasePrice = stock.getCost(); // The price at the time of purchase
                totalProfitLossForStock = currentPrice - purchasePrice;

                break;
            }
        }

        return totalProfitLossForStock;
    }
}

