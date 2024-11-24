package interface_adapter.profit_loss;

import use_case.profit_loss.ProfitLossOutputBoundary;
import use_case.profit_loss.ProfitLossOutputData;

public class ProfitLossPresenter implements ProfitLossOutputBoundary {

    @Override
    public void presentTotalProfitLoss(ProfitLossOutputData outputData) {
        double totalProfitLoss = outputData.getProfitLoss();
        System.out.println("Total Profit/Loss: $" + String.format("%.2f", totalProfitLoss));
    }

    @Override
    public void presentStockProfitLoss(ProfitLossOutputData outputData, String tickerSymbol) {
        double stockProfitLoss = outputData.getProfitLoss();
        System.out.println("Profit/Loss for " + tickerSymbol + ": $" + String.format("%.2f", stockProfitLoss));
    }
}