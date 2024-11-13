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
        // Simply add the stock object to the portfolio (each stock is 1 share)
        stocks.add(stock);
    }

    // Method to remove a stock (1 share) from the portfolio
    public void removeStock(Stock stock) {
        // Remove the first occurrence of the stock object from the portfolio
        stocks.remove(stock);
    }

    // Method to get the number of shares for a specific ticker symbol
    public int getShareCount(String tickerSymbol) {
        int count = 0;
        // Loop through the portfolio and count stocks with the matching ticker
        for (Stock stock : stocks) {
            if (stock.getTickerSymbol().equals(tickerSymbol)) {
                count++;
            }
        }
        return count;
    }
}

