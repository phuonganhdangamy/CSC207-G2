package entity;

/**
 * A factory class responsible for creating instances of {@link Stock}.
 */
public class StockFactory {
    /**
     * Creates a new instance of {@link Stock} with the given ticker symbol and cost.
     *
     * @param ticker The ticker symbol of the stock.
     * @param cost   The cost of the stock.
     * @return A new Stock object with the specified ticker and cost.
     */
    public Stock create(String ticker, double cost) {
        return new Stock(ticker, cost);
    }
}
