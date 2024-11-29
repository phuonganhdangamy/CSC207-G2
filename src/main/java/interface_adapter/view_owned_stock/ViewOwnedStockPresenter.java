package interface_adapter.view_owned_stock;

import interface_adapter.LoggedInState;
import use_case.list_stocks.ListStocksOutputBoundary;
import use_case.list_stocks.ListStocksOutputData;
import interface_adapter.LoggedInViewModel;

/**
 * Presenter for the View Owned Stock use case.
 * Updates the stockOwnership in the LoggedInState through the LoggedInViewModel.
 */
public class ViewOwnedStockPresenter implements ListStocksOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;

    /**
     * Constructs the ViewOwnedStockPresenter.
     *
     * @param loggedInViewModel The view model of the logged-in user.
     */
    public ViewOwnedStockPresenter(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
    }

    /**
     * Updates the stock ownership in the LoggedInState through the LoggedInViewModel.
     *
     * @param outputData The data containing the stock ownership details.
     */
    @Override
    public void present(ListStocksOutputData outputData) {
        if (outputData != null && outputData.getStocksOwned() != null) {
            // Retrieve the current state from the ViewModel
            LoggedInState currentState = loggedInViewModel.getState();

            // Update the stock ownership in the LoggedInState
            currentState.setStockOwnership(outputData.getStocksOwned());

            // Notify the ViewModel about the state change
            loggedInViewModel.setState(currentState);
            loggedInViewModel.firePropertyChanged();

        }
    }
}