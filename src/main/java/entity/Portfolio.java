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

    // Method to add stocks to the portfolio
       public void addStock(Stock stock) {
        stocks.add(stock);
    }

    // Method to remove stocks from the portfolio -- still need to think of a way to implement it
    public void removeStock(Stock stock, int numShares) {

    }



 
}
