package interface_adapter.profit_loss;

import interface_adapter.LoggedInViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.LoggedInState;

import use_case.profit_loss.ProfitLossOutputBoundary;
import use_case.profit_loss.ProfitLossOutputData;

import java.util.Map;

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
     * Default implementation to display the total profit/loss.
     *
     * @param outputData the profit/loss calculation results.
     */
    @Override
    public void presentTotalProfitLoss(ProfitLossOutputData outputData) {
        System.out.println("Total Profit/Loss: " + outputData.getTotalProfitLoss());
    }

    /**
     * Default implementation to display stock-specific profit/loss.
     *
     * @param outputData    the profit/loss calculation results.
     * @param tickerSymbol  the ticker symbol of the specific stock.
     */
    @Override
    public void presentStockProfitLoss(ProfitLossOutputData outputData, String tickerSymbol) {
        Double profitLoss = outputData.getStockProfitLosses().get(tickerSymbol);
        if (profitLoss == null) {
            System.out.println("No profit/loss data for stock: " + tickerSymbol);
        } else {
            System.out.println("Profit/Loss for " + tickerSymbol + ": " + profitLoss);
        }
    }

    /**
     * Helper method to update the LoggedInViewModel with profit/loss data.
     *
     * @param outputData the profit/loss calculation results.
     */
    private void updateLoggedInState(ProfitLossOutputData outputData) {
        double totalProfitLoss = outputData.getTotalProfitLoss();
        Map<String, Double> stockProfitLosses = outputData.getStockProfitLosses();

        LoggedInState state = loggedInViewModel.getState();
        state.setTotalProfitLoss(totalProfitLoss);
        state.setStockProfitLoss(stockProfitLosses);

        loggedInViewModel.setState(state);
        loggedInViewModel.firePropertyChanged();
    }
}