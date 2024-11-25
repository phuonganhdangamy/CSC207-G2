package use_case.list_stocks;

/**
 * The input boundary for the List Stocks use case.
 * Represents the interface to be implemented by the use case interactor.
 */
public interface ListStocksInputBoundary {

    /**
     * Executes the use case with the provided input data.
     *
     * @param inputData the input data containing the username; must not be null
     * @throws IllegalArgumentException if inputData is null
     */
    void execute(ListStocksInputData inputData);
}