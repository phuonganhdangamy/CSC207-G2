package interface_adapter.buy_stock;

import use_case.buy_stock.*;

public class BuyStockPresenter implements BuyStockOutputBoundary {
    @Override
    public void presentSuccess(BuyStockOutputData outputData) {
        System.out.println("Stock purchase successful!");
        System.out.println("Ticker: " + outputData.getTickerSymbol());
        System.out.println("Shares Bought: " + outputData.getNumberOfShares());
        System.out.println("Remaining Balance: $" + outputData.getRemainingBalance());
    }

    @Override
    public void presentError(String errorMessage) {
        System.out.println("Error: " + errorMessage);
    }
}
