package interface_adapter.profit_loss;

import java.util.Map;

import interface_adapter.LoggedInState;
import interface_adapter.LoggedInViewModel;
import interface_adapter.ViewManagerModel;
import use_case.profit_loss.ProfitLossOutputBoundary;
import use_case.profit_loss.ProfitLossOutputData;

/**
 * The Presenter for the Profit Loss Use Case.
 */
public class ProfitLossPresenter implements ProfitLossOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Creates a new ProfitLossPresenter.
     *
     * @param loggedInViewModel the view model representing the logged-in user's state
     * @param viewManagerModel  the view manager model to control UI updates
     */
    public ProfitLossPresenter(LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Presents the combined profit/loss results to the user interface.
     *
     * @param outputData the combined profit/loss data
     */
    @Override
    public void presentCombinedProfitLoss(ProfitLossOutputData outputData) {
        // Update the logged-in state
        updateLoggedInState(outputData);
    }

    /**
     * Sends the unified profit/loss results to the presenter.
     *
     * @param outputData the profit/loss calculation results.
     */
    @Override
    public void success(ProfitLossOutputData outputData) {
        // Delegate to presentCombinedProfitLoss for consistency
        presentCombinedProfitLoss(outputData);
    }

    /**
     * Helper method to update the LoggedInViewModel with profit/loss data.
     *
     * @param outputData the profit/loss calculation results.
     */
    private void updateLoggedInState(ProfitLossOutputData outputData) {
        final double totalProfitLoss = outputData.getTotalProfitLoss();
        final Map<String, Double> stockProfitLosses = outputData.getStockProfitLosses();

        final LoggedInState state = loggedInViewModel.getState();
        state.setTotalProfitLoss(totalProfitLoss);
        state.setStockProfitLoss(stockProfitLosses);

        loggedInViewModel.setState(state);
        loggedInViewModel.firePropertyChanged();
    }
}
