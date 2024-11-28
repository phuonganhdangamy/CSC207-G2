package interface_adapter.buy_stock;

import interface_adapter.LoggedInState;
import interface_adapter.LoggedInViewModel;
import interface_adapter.ViewManagerModel;
import use_case.buy_stock.*;

/**
 * The Presenter for the Buy Stock Use Case.
 */
public class BuyStockPresenter implements BuyStockOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public BuyStockPresenter(LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(BuyStockOutputData outputData) {
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setBalance(outputData.getRemainingBalance());
        loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setError(errorMessage);
        loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChanged();
    }
}
