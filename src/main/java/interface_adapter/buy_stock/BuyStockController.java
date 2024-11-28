package interface_adapter.buy_stock;

import use_case.buy_stock.*;

/**
 * The Controller for the Buy Stock Use Case.
 */
public class BuyStockController {
    private final BuyStockInputBoundary interactor;

    public BuyStockController(BuyStockInputBoundary interactor) {
        this.interactor = interactor;
    }


    public void execute(String username, String tickerSymbol, int numberOfShares) {
        BuyStockInputData inputData = new BuyStockInputData(username, tickerSymbol, numberOfShares);
        interactor.execute(inputData);
    }

}
