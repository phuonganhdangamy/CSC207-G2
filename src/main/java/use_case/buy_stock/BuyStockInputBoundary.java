package use_case.buy_stock;

/**
 * The BuyStockInputBoundary interface defines the contract for the actions related to buying stocks.
 * It serves as the input boundary for the "buy stock" use case and provides a method that should
 * be implemented by the corresponding interactor to perform the necessary business logic.
 * Responsibilities:
 * - Defines the contract for executing the "buy stock" use case.
 * - Accepts input data related to the stock purchase request.
 * Method(s):
 * - execute(BuyStockInputData inputData): Executes the buy stock use case by processing the input data.
 */

public interface BuyStockInputBoundary {

    /**
     * Executes the "buy stock" use case by processing the input data and performing the necessary
     * business logic for purchasing the stock.
     *
     * @param inputData The data for the buy stock use case, including username, ticker symbol,
     *                  and the quantity of shares to purchase.
     */

    void execute(BuyStockInputData inputData);

}
