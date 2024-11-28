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

        // Reset the state so that the label can appear again
        findStockViewModel.setFindStockState("");
        findStockViewModel.setMessage("");
        findStockViewModel.setError("");

        String successMessage = "The current price of " + response.getTickerSymbol() + " is $" +
                response.getCurrentCost() + ".";
        findStockViewModel.setMessage(successMessage);
        // System.out.println(successMessage);

        findStockViewModel.setFindStockState("true");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // System.out.println("Error: " + errorMessage);
//        FindStockState errorState = new FindStockState("", "", "");
//        errorState.setError(errorMessage);

        // Reset the state so that the label can appear again
        findStockViewModel.setFindStockState("");
        findStockViewModel.setMessage("");
        findStockViewModel.setError("");

        System.out.println(errorMessage);
        findStockViewModel.setError(errorMessage);
        findStockViewModel.setFindStockState("false");
    }
}
