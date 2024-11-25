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
    public void presentTotalProfitLoss(ProfitLossOutputData outputData) {
        // Update the state with the new profit/loss value
        double totalProfitLoss = outputData.getProfitLoss();
        loggedInViewModel.getState().setTotalProfitLoss(totalProfitLoss);
    }

    @Override
    public void presentStockProfitLoss(ProfitLossOutputData outputData, String tickerSymbol) {
        // This can remain unimplemented if not needed for LoggedInView
        // Or update the state for stock-specific profit/loss if required
    }
}
