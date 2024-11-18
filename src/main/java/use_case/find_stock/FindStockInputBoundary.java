package use_case.find_stock;

/**
 * Input Boundary for actions which are related to finding stock.
 * Controller for find stock use case will call these methods which need to be implemented in the
 * interactor.
 */
public interface FindStockInputBoundary {
    /**
     * Executes the login use case.
     * @param findStockInputData the input data
     */
    void execute(FindStockInputData findStockInputData);
}
