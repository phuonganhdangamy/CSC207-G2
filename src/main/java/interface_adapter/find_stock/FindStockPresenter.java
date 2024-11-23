package interface_adapter.find_stock;

import use_case.find_stock.FindStockOutputBoundary;
import use_case.find_stock.FindStockOutputData;

/**
 * The Presenter for the Find Stock Use Case.
 */
public class FindStockPresenter implements FindStockOutputBoundary {

    private final FindStockViewModel viewModel;

    public FindStockPresenter(FindStockViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(FindStockOutputData response) {
        String successMessage = "The current price of \"" + response.getTickerSymbol() + " is $" +
                response.getCurrentCost() + ".";
        System.out.println(successMessage);
        viewModel.setFindStockSuccess(true);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("Error: " + errorMessage);
        viewModel.setFindStockSuccess(false);
    }
}
