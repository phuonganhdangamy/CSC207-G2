package interface_adapter.view_owned_stock;

import use_case.list_stocks.ListStocksOutputBoundary;
import use_case.list_stocks.ListStocksOutputData;
import interface_adapter.LoggedInState;

/**
 * Presenter for the View Owned Stock use case.
 * Updates the stockOwnership in the LoggedInState.
 */
public class ViewOwnedStockPresenter implements ListStocksOutputBoundary {
    private final LoggedInState loggedInState;

    /**
     * Constructs the ViewOwnedStockPresenter.
     *
     * @param loggedInState The state of the currently logged-in user.
     */
    public ViewOwnedStockPresenter(LoggedInState loggedInState) {
        this.loggedInState = loggedInState;
    }

    /**
     * Updates the stock ownership in LoggedInState based on the output data
     * provided by the list_stocks use case.
     *
     * @param outputData The data containing the stock ownership details.
     */
    @Override
    public void present(ListStocksOutputData outputData) {
        if (outputData != null && outputData.getStocksOwned() != null) {
            loggedInState.setStockOwnership(outputData.getStocksOwned());
        }
    }
}