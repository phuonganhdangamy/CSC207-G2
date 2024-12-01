package use_case.buy_stock;

/**
 * The BuyStockOutputBoundary interface defines the methods that must be implemented by the presenter
 * to prepare the output views for the "buy stock" use case. These methods allow the interactor to
 * notify the user of the result of their stock purchase, whether it's a success or failure.
 * Responsibilities:
 * - Defines the contract for presenting the result of the "buy stock" use case.
 * Method(s):
 * - prepareSuccessView(BuyStockOutputData outputData): Prepares the success view with the result of
 *   the purchase, including remaining balance and updated share count.
 * - prepareFailView(String errorMessage): Prepares the failure view with an error message if the purchase
 *   cannot be completed (e.g., insufficient balance, stock does not exist).
 */

public interface BuyStockOutputBoundary {

    /**
     * Prepares the success view to be displayed to the user after a successful stock purchase.
     *
     * @param outputData The output data that contains the remaining balance, ticker symbol,
     *                   and number of shares after the purchase.
     */
    void prepareSuccessView(BuyStockOutputData outputData);

    /**
     * Prepares the failure view to be displayed to the user if the stock purchase fails.
     *
     * @param errorMessage A message explaining the failure (e.g., insufficient balance, stock does not exist).
     */
    void prepareFailView(String errorMessage);
}
