package entity;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private User user;
    private List<StockEntry> stocksOwned;

    public Portfolio(User user) {
        this.user = user;
        this.stocksOwned = new ArrayList<>();
    }

    // Method to add stocks to the portfolio
    public void addStock(Stock stock, int numShares, double purchasePrice) {
        for (StockEntry entry : stocksOwned) {
            if (entry.getStock().getTickerSymbol().equals(stock.getTickerSymbol())) {
                // If the stock already exists, update the number of shares and exit
                entry.setNumShares(entry.getNumShares() + numShares);
                System.out.println(numShares + " shares of " + stock.getCompanyName() + " added to your portfolio.");
                return;
            }
        }
        // If the stock does not exist, add a new entry with the purchase price
        stocksOwned.add(new StockEntry(stock, numShares, purchasePrice));
        System.out.println(numShares + " shares of " + stock.getCompanyName() + " added to your portfolio.");
    }

    // Method to remove stocks from the portfolio -- still need to think of a way to implement it
    public void removeStock(Stock stock, int numShares) {

    }

    // Method to view all stocks in the portfolio
    public void viewStocks() {
        if (stocksOwned.isEmpty()) {
            System.out.println("Your portfolio is empty.");
            return;
        }

        System.out.println("Stocks in your portfolio:");
        for (StockEntry entry : stocksOwned) {
            System.out.println("Ticker: " + entry.getStock().getTickerSymbol() +
                    ", Company: " + entry.getStock().getCompanyName() +
                    ", Shares: " + entry.getNumShares() +
                    ", Current Price: $" + entry.getStock().getCurrentPrice() +
                    ", Purchase Price: $" + entry.getPurchasePrice());
        }
    }

    // Method to get the balance of the portfolio
    public double getBalance() {
        double balance = 0.0;
        for (StockEntry entry : stocksOwned) {
            balance += entry.getStock().getCurrentPrice() * entry.getNumShares();
        }
        return balance;
    }

    public List<StockEntry> getStocksOwned() {
        return stocksOwned;
    }

    public static class StockEntry {
        private Stock stock;
        private int numShares;
        private double purchasePrice;

        public StockEntry(Stock stock, int numShares, double purchasePrice) {
            this.stock = stock;
            this.numShares = numShares;
            this.purchasePrice = purchasePrice;
        }

        public Stock getStock() {
            return stock;
        }

        public int getNumShares() {
            return numShares;
        }

        public void setNumShares(int numShares) {
            this.numShares = numShares;
        }

        public double getPurchasePrice() {
            return purchasePrice;
        }
    }
}