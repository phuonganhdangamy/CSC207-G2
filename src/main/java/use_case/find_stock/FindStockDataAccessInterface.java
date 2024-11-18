package use_case.find_stock;

public interface FindStockDataAccessInterface {
    /**
     * Retrieves the current cost of a stock by its ticker symbol.
     *
     * @param tickerSymbol the stock entity containing the ticker symbol.
     * @return the current cost of the stock.
     */
    double getCost(String tickerSymbol);

    /**
     * Checks if stock information can be retrieved using the ticker symbol.
     *
     * @param tickerSymbol the unique identifier of the stock.
     * @return true if the stock exists, false otherwise.
     */
    boolean isStockExist(String tickerSymbol);
}
