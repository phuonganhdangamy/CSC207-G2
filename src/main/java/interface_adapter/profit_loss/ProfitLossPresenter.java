package interface_adapter.profit_loss;

import interface_adapter.LoggedInViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.LoggedInState;

import use_case.profit_loss.ProfitLossOutputBoundary;
import use_case.profit_loss.ProfitLossOutputData;

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
        double totalProfitLoss = outputData.getTotalProfitLoss();
        double stockProfitLoss = outputData.getStockProfitLoss();
        String tickerSymbol = outputData.getTickerSymbol();

        // Update the view model with the total profit/loss
        loggedInViewModel.getState().setTotalProfitLoss(totalProfitLoss);

        // Update the view model with stock profit/loss
        if (tickerSymbol != null && !tickerSymbol.isEmpty()) {
            loggedInViewModel.getState().setStockProfitLoss(tickerSymbol, stockProfitLoss);
        }

        // display the results for debugging/UI
        System.out.println("Total Profit/Loss: " + totalProfitLoss);
        System.out.println("Stock [" + tickerSymbol + "] Profit/Loss: " + stockProfitLoss);

        /// Notify the view manager to refresh the display by updating its state property
        viewManagerModel.setState("updatedState");
    }

    // display the results for debugging when changing code
    public void presentTotalProfitLoss(ProfitLossOutputData outputData) {
        throw new UnsupportedOperationException("Use presentCombinedProfitLoss instead.");
    }

    @Override
    public void presentStockProfitLoss(ProfitLossOutputData outputData, String tickerSymbol) {
        throw new UnsupportedOperationException("Use presentCombinedProfitLoss instead.");
    }
}
