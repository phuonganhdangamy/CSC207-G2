package entity;

public class StockFactory {
    public Stock create(String ticker, String company, double cost){
        return new Stock(ticker, company, cost);

    }
}
