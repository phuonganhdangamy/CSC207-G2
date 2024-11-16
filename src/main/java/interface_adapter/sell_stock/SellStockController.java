package interface_adapter.sell_stock;

import use_case.sell_stock.SellStockInputBoundary;
import use_case.sell_stock.SellStockInputData;

/**
 * The Controller for the Sell Stock Use Case.
 */
public class SellStockController {
    private SellStockInputBoundary sellStockInteractor;

    // Needs access to the interactor to execute the use case
    public SellStockController(SellStockInputBoundary sellStockInteractor) {
        this.sellStockInteractor = sellStockInteractor;
    }

    /**
     * Executes the Sell Stock Use Case.
     * @param ticker - the share to sell
     * @param quantity - the quantity to sell
     */
    public void execute(int quantity, String ticker) {
        SellStockInputData outputData = new SellStockInputData(quantity, ticker);
        sellStockInteractor.execute(outputData);

    }
}
