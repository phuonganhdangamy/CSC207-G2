package use_case.sell_stock;

/**
 * Interface for the output boundary of the selling stock use case.
 * Presenter will implement these methods.
 * Interactor relies on these methods.
 */
public interface SellStockOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(SellStockOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
