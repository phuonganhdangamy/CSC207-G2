package use_case.buy_stock;

/**
 * Output boundary for the Buy Stock Use Case.
 * Interactor will call these methods after execution.
 * Presenter for find stock use case needs to implement these methods.
 */
public interface BuyStockOutputBoundary {

    /**
     * Prepares the success view for the Buy Stock Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(BuyStockOutputData outputData);

    /**
     * Prepares the failure view for the Buy Stock Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

}
