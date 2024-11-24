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

    public List<Stock> getStocks() {
        return stocks;
    }

    // Method to add a stock (which represents 1 share) to the portfolio
    public void addStock(Stock stock) {
        stocks.add(stock);
        // Deduct the cost of the stock from the user's balance when buying
        user.setBalance(user.getBalance() - stock.getCost());
    }

    // Revised method to remove a stock by ticker symbol and update balance
    public boolean removeStock(String tickerSymbol, double sellingPrice) {
        for (Stock stock : stocks) {
            if (stock.getTickerSymbol().equals(tickerSymbol)) {
                stocks.remove(stock);
                // Add the cost of the stock to the user's balance when selling
                user.setBalance(user.getBalance() + sellingPrice);
                return true;
            }
        }
        // Return false if no stock with the given ticker symbol is found
        return false;
    }


    // Method to get the number of shares for a specific ticker symbol
    public int getShareCount(String tickerSymbol) {
        int count = 0;
        for (Stock stock : stocks) {
            if (stock.getTickerSymbol().equals(tickerSymbol)) {
                count++;
            }
        }
        return count;
    }
}

