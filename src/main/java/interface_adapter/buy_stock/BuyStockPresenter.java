package interface_adapter.buy_stock;

import use_case.buy_stock.*;

/**
 * The Presenter for the Buy Stock Use Case.
 */
public class BuyStockPresenter implements BuyStockOutputBoundary {
    @Override
    public void prepareSuccessView(BuyStockOutputData outputData) {
        System.out.println("Stock purchase successful!");
        System.out.println("Ticker: " + outputData.getTickerSymbol());
        System.out.println("Shares Bought: " + outputData.getNumberOfShares());
        System.out.println("Remaining Balance: $" + outputData.getRemainingBalance());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("Error: " + errorMessage);
    }
}
