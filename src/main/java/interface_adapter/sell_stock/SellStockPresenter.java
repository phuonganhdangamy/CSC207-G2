package interface_adapter.sell_stock;

import interface_adapter.LoggedInState;
import interface_adapter.LoggedInViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.sell_stock.SellStockOutputBoundary;
import use_case.sell_stock.SellStockOutputData;

public class SellStockPresenter implements SellStockOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public SellStockPresenter(LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
    }


    @Override
    public void prepareSuccessView(SellStockOutputData response) {
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setBalance(response.getNewBalance());
        loggedInViewModel.setState(loggedInState);

    }

    @Override
    public void prepareFailView(String errorMessage) {
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setError(errorMessage);
        loggedInViewModel.setState(loggedInState);

        this.loggedInViewModel.firePropertyChanged();

    }
}
