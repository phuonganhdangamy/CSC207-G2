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
        double totalProfitLoss = outputData.getTotalProfitLoss();
        Map<String, Double> stockProfitLosses = outputData.getStockProfitLosses();

        LoggedInState state = loggedInViewModel.getState();
        state.setTotalProfitLoss(totalProfitLoss);
        state.setStockProfitLoss(stockProfitLosses);

        System.out.println("Total Profit/Loss: " + totalProfitLoss);
        for (String ticker : stockProfitLosses.keySet()) {
            System.out.println("Stock [" + ticker + "] Profit/Loss: " + stockProfitLosses.get(ticker));
        }

        viewManagerModel.setState("updatedState");
    }

    public void success(double totalProfitLoss, Map<String, Double> stockProfitLosses) {
        LoggedInState state = loggedInViewModel.getState();
        state.setTotalProfitLoss(totalProfitLoss);
        state.setStockProfitLoss(stockProfitLosses);
    }
}

