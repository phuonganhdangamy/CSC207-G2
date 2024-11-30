package use_case.buy_stock;

/**
 * The BuyStockInputData class holds the data needed to execute the "buy stock" use case.
 * This includes the username of the user making the purchase, the ticker symbol of the stock,
 * and the quantity of shares to be bought.

 * Responsibilities:
 * - Stores the data required for the "buy stock" use case.

 * Constructor(s):
 * - BuyStockInputData(String username, String tickerSymbol, int quantity): Initializes the input data
 *   for the buy stock use case with the provided username, ticker symbol, and quantity of shares.

 * Methods:
 * - getUsername(): Returns the username of the user.
 * - getTickerSymbol(): Returns the ticker symbol of the stock to be purchased.
 * - getQuantity(): Returns the quantity of shares to be bought.
 */

public class BuyStockInputData {
    private final String username;
    private final String tickerSymbol;
    private final int quantity;

    /**
     * Constructor for initializing the input data for the buy stock use case with the given parameters.
     *
     * @param username The username of the user who wants to buy the stock.
     * @param tickerSymbol The ticker symbol of the stock to be bought.
     * @param quantity The number of shares the user wants to purchase.
     */

    public BuyStockInputData(String username, String tickerSymbol, int quantity) {
        this.username = username;
        this.tickerSymbol = tickerSymbol;
        this.quantity = quantity;
    }

    /**
     * Retrieves the username of the user who wants to buy the stock.
     *
     * @return The username of the user.
     */

    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the ticker symbol of the stock the user wants to buy.
     *
     * @return The ticker symbol of the stock.
     */

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    /**
     * Retrieves the quantity of shares the user wants to buy.
     *
     * @return The number of shares to be bought.
     */

    public int getQuantity() {
        return quantity;
    }
}

