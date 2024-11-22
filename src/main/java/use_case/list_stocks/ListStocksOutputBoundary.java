package use_case.list_stocks;

/**
 * The output boundary interface for the List Stocks use case.
 * Defines the contract for presenting the output data to the view or user interface.
 */
public interface ListStocksOutputBoundary {

    /**
     * Presents the output data of the List Stocks use case.
     *
     * @param outputData the output data containing the user's stock ownership details; must not be null
     * @throws IllegalArgumentException if outputData is null
     */
    void present(ListStocksOutputData outputData);
}
