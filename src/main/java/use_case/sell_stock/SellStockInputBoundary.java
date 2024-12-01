package use_case.sell_stock;

import use_case.list_stocks.ListStocksInputBoundary;
import use_case.profit_loss.ProfitLossInputBoundary;

/**
 * Interface for actions relating to selling the stock in the portfolio.
 * Controller for the selling stock use case will call this method.
 */
public interface SellStockInputBoundary {
    /**
     * Executes the login use case.
     * @param inputData the input data
     */
    void execute(SellStockInputData inputData);

    /**
     * Sets the interactor responsible for calculating the user's profit/loss after selling a stock.
     * @param profitLossInteractor The interactor that handles profit/loss calculations.
     */
    void setProfitLossInteractor(ProfitLossInputBoundary profitLossInteractor);

    /**
     * Sets the interactor responsible for viewing owned stocks after the sale.
     * @param viewOwnedStockInteractor The interactor that handles the viewing of owned stocks.
     */
    void setViewOwnedStockInteractor(ListStocksInputBoundary viewOwnedStockInteractor);

}
