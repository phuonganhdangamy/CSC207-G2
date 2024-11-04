package entity;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private User user;
    private List<Stock> stocks;

    public Portfolio(User user) {
        this.user = user;
        this.stocks = new ArrayList<>();
    }

    // Method to add stocks to the portfolio
       public void addStock(Stock stock) {
        stocks.add(stock);
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


        public int getNumShares() {
            return numShares;
        }

        public void setNumShares(int numShares) {
            this.numShares = numShares;
        }

        public double getPurchasePrice() {
            return purchasePrice;
        }


    public List<Stock> getStocks() {
        return stocks;
    }

 
}
