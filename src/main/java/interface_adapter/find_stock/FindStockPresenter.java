package interface_adapter.find_stock;

import interface_adapter.ViewManagerModel;
import use_case.find_stock.FindStockOutputBoundary;
import use_case.find_stock.FindStockOutputData;

/**
 * The Presenter for the Find Stock Use Case.
 */
public class FindStockPresenter implements FindStockOutputBoundary {

    private final FindStockViewModel findStockViewModel;
    private final ViewManagerModel viewManager;

    public FindStockPresenter(FindStockViewModel findStockViewModel, ViewManagerModel viewManager) {
        this.findStockViewModel = findStockViewModel;
        this.viewManager = viewManager;
    }

    @Override
    public void prepareSuccessView(FindStockOutputData response) {
        String successMessage = "The current price of \"" + response.getTickerSymbol() + " is $" +
                response.getCurrentCost() + ".";
        System.out.println(successMessage);
        findStockViewModel.setFindStockSuccess("true");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("Error: " + errorMessage);
        findStockViewModel.setFindStockSuccess("false");
    }
}
