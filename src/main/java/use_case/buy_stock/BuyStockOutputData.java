package use_case.buy_stock;

public class BuyStockOutputData {

    private final double remainingBalance;
    private final String tickerSymbol;
    private final int numberOfShares;

    public BuyStockOutputData(double remainingBalance, String tickerSymbol, int numberOfShares) {
        this.remainingBalance = remainingBalance;
        this.tickerSymbol = tickerSymbol;
        this.numberOfShares = numberOfShares;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

}
