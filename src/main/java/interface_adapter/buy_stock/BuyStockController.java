package interface_adapter.buy_stock;

import use_case.buy_stock.BuyStockInputBoundary;
import use_case.buy_stock.BuyStockInputData;

/**
 * The Controller for the Buy Stock Use Case.
 */
public class BuyStockController {
    private final BuyStockInputBoundary interactor;

    /**
     * Constructs a BuyStockController with the given BuyStockInputBoundary.
     * @param interactor The BuyStockInputBoundary used to execute the buy stock use case.
     */
    public BuyStockController(BuyStockInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the buy stock use case by passing the input data to the interactor.
     * @param username The username of the user who wants to buy the stock.
     * @param tickerSymbol The ticker symbol of the stock to be bought.
     * @param numberOfShares The number of shares to be bought.
     */
    public void execute(String username, String tickerSymbol, int numberOfShares) {
        final BuyStockInputData inputData = new BuyStockInputData(username, tickerSymbol, numberOfShares);
        interactor.execute(inputData);
    }
}
