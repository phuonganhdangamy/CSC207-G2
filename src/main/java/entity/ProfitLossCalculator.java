package entity;

import data_access.DBStockDataAccessObject;

public class ProfitLossCalculator {
    private Portfolio portfolio;
    private DBStockDataAccessObject stockDataAccess;

    public ProfitLossCalculator(Portfolio portfolio) {
        this.portfolio = portfolio;
        this.stockDataAccess = new DBStockDataAccessObject();
    }

    // Method to calculate the total profit/loss of the entire portfolio
    public double calculateTotalProfitLoss() {
        double totalProfitLoss = 0.0;

        // Loop through each stock in the portfolio
        for (Stock stock : portfolio.getStocks()) {
            // Fetch the current price from the API using the stock's ticker symbol
            double currentPrice = stockDataAccess.getCost(stock.getTickerSymbol());

            // Calculate profit/loss based on the purchase price (cost) and current price
            double purchasePrice = stock.getCost();
            double profitLossPerStock = currentPrice - purchasePrice;

            // Add the profit/loss for this stock (since each Stock object represents 1 share)
            totalProfitLoss += profitLossPerStock;
        }

        return totalProfitLoss;
    }

    // Method to calculate profit/loss for a specific stock by its ticker symbol
    public double calculateStockProfitLoss(String tickerSymbol) {
        double totalProfitLossForStock = 0.0;

        for (Stock stock : portfolio.getStocks()) {
            if (stock.getTickerSymbol().equalsIgnoreCase(tickerSymbol)) {
                double currentPrice = stockDataAccess.getCost(stock.getTickerSymbol());

                double purchasePrice = stock.getCost();
                totalProfitLossForStock = currentPrice - purchasePrice;

                break;
            }
        }

        return totalProfitLossForStock;
    }
}
