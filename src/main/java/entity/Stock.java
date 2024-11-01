package entity;

public class Stock {
    private String ticker;
    private String company;
    private double cost;

    public Stock(String ticker, String company, double cost) {
        this.ticker = ticker;
        this.company = company;
        this.cost = cost;
    }

    public String getTicker() {
        return ticker;
    }

    public String getCompany() {
        return company;
    }

    public double getCost() {
        return cost;
    }
}
