package use_case.buy_stock;

/**
 * The input data for the Buy Stock Use Case.
 */
public class BuyStockInputData {
    private final String username;
    private final String tickerSymbol;
    private final int numberOfShares;

    public BuyStockInputData(String username, String tickerSymbol, int numberOfShares) {
        this.username = username;
        this.tickerSymbol = tickerSymbol;
        this.numberOfShares = numberOfShares;
    }

    public String getUsername() {
        return username;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }
}
