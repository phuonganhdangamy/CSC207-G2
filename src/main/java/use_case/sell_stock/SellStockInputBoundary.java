package use_case.sell_stock;

/**
 * Interface for actions relating to selling the stock in the portfolio.
 * Controller for the selling stock use case will call this method.
 */
public interface SellStockInputBoundary {
    /**
     * Executes the login use case.
     * @param inputData the input data
     */
    void execute(SellStockInputData inputData);
}
