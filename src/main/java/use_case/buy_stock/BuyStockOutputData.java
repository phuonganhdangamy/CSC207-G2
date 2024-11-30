package use_case.buy_stock;

/**
 * The BuyStockOutputData class holds the data to be presented to the user after the "buy stock" use case
 * has been executed. This includes the user's remaining balance, the ticker symbol of the stock, and the
 * number of shares owned by the user after the purchase.

 * Responsibilities:
 * - Stores the output data that will be presented to the user after the stock purchase.

 * Constructor(s):
 * - BuyStockOutputData(double remainingBalance, String tickerSymbol, int numberOfShares): Initializes
 *   the output data with the user's remaining balance, the stock ticker symbol, and the number of shares.

 * Methods:
 * - getRemainingBalance(): Returns the user's remaining balance after the purchase.
 * - getTickerSymbol(): Returns the ticker symbol of the purchased stock.
 * - getNumberOfShares(): Returns the number of shares the user owns after the purchase.
 */

public class BuyStockOutputData {

    private final double remainingBalance;
    private final String tickerSymbol;
    private final int numberOfShares;

    /**
     * Constructor for initializing the output data for the buy stock use case.
     *
     * @param remainingBalance The remaining balance of the user after the stock purchase.
     * @param tickerSymbol The ticker symbol of the stock purchased.
     * @param numberOfShares The number of shares the user now owns after the purchase.
     */

    public BuyStockOutputData(double remainingBalance, String tickerSymbol, int numberOfShares) {
        this.remainingBalance = remainingBalance;
        this.tickerSymbol = tickerSymbol;
        this.numberOfShares = numberOfShares;
    }

    /**
     * Retrieves the remaining balance of the user after the stock purchase.
     *
     * @return The user's remaining balance.
     */

    public double getRemainingBalance() {
        return remainingBalance;
    }

    /**
     * Retrieves the ticker symbol of the stock purchased.
     *
     * @return The ticker symbol of the stock.
     */

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    /**
     * Retrieves the number of shares the user owns after the stock purchase.
     *
     * @return The number of shares the user now owns.
     */

    public int getNumberOfShares() {
        return numberOfShares;
    }

}
