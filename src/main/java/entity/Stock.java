package entity;

public class Stock {
    private String ticker;
    private double cost;

    public Stock(String ticker, double cost) {
        this.ticker = ticker;
        this.cost = cost;
    }

    public String getTicker() {
        return ticker;
    }

    public double getCost() {
        return cost;
    }
}
