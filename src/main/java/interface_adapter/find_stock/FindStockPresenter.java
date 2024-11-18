package interface_adapter.find_stock;

import use_case.find_stock.FindStockOutputBoundary;
import use_case.find_stock.FindStockOutputData;

/**
 * The Presenter for the Find Stock Use Case.
 */
public class FindStockPresenter implements FindStockOutputBoundary {

    @Override
    public void prepareSuccessView(FindStockOutputData response) {
        String successMessage = "The current price of \"" + response.getTickerSymbol() + " is $" +
                response.getCurrentCost() + ".";

    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("Error: " + errorMessage);
    }
}
