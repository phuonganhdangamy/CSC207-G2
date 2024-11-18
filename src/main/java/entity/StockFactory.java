package entity;

public class StockFactory {
    public Stock create(String ticker, String company){
        return new Stock(ticker, company);
    public Stock create(String ticker, double cost){
        return new Stock(ticker, cost);

    }
}
