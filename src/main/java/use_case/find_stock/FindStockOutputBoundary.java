package use_case.find_stock;

/**
 * Output boundary for the Find Stock Use Case.
 * Interactor will call these methods after execution.
 * Presenter for find stock use case needs to implement these methods.
 */
public interface FindStockOutputBoundary {
    /**
     * Prepares the success view for the Find Stock Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(FindStockOutputData outputData);

    /**
     * Prepares the failure view for the Find Stock Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
