package interface_adapter.profit_loss;

import interface_adapter.LoggedInViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.LoggedInState;

import use_case.profit_loss.ProfitLossOutputBoundary;
import use_case.profit_loss.ProfitLossOutputData;

public class ProfitLossPresenter implements ProfitLossOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public ProfitLossPresenter(LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentProfitLoss(ProfitLossOutputData outputData) {
        // Retrieve the current state from the ViewModel
        LoggedInState state = loggedInViewModel.getState();

        // Update the state with total profit/loss
        state.setTotalProfitLoss(outputData.getTotalProfitLoss());

        // If a specific ticker is provided, update stock table
        String tickerSymbol = outputData.getTickerSymbol();
        if (tickerSymbol != null) {
            state.getStockTable().computeIfPresent(tickerSymbol, (ticker, values) -> {
                values.set(1, outputData.getStockProfitLoss()); // Update profit/loss index
                return values;
            });
        }

        // Notify the ViewModel of the updated state
        loggedInViewModel.setState(state);
    }
}
