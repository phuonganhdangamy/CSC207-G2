package entity;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private List<Stock> stocks;

    public Portfolio() {
        stocks = new ArrayList<Stock>();
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }
}
