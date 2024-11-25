package interface_adapter.profit_loss;

import interface_adapter.LoggedInState;
import use_case.profit_loss.ProfitLossOutputBoundary;
import use_case.profit_loss.ProfitLossOutputData;

public class ProfitLossPresenter implements ProfitLossOutputBoundary {

    private final LoggedInState loggedInState;

    public ProfitLossPresenter(LoggedInState loggedInState) {
        this.loggedInState = loggedInState;
    }

    @Override
    public void presentTotalProfitLoss(ProfitLossOutputData outputData) {
        // Update the state with the new profit/loss value
        double totalProfitLoss = outputData.getProfitLoss();
        loggedInState.setTotalProfitLoss(totalProfitLoss);
    }

    @Override
    public void presentStockProfitLoss(ProfitLossOutputData outputData, String tickerSymbol) {
        // This can remain unimplemented if not needed for LoggedInView
        // Or update the state for stock-specific profit/loss if required
    }
}
