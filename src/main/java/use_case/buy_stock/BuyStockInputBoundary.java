package use_case.buy_stock;

/**
 * Input Boundary for actions which are related to buying stock.
 * Controller for buy stock use case will call these methods which need to be implemented in the
 * interactor.
 */
public interface BuyStockInputBoundary {

    /**
     * Executes the buy stock use case.
     * @param inputData the input data
     */
    void execute(BuyStockInputData inputData);

}
