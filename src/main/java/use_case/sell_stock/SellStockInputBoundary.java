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

    public void setProfitLossInteractor(ProfitLossInputBoundary profitLossInteractor);

    public void setViewOwnedStockInteractor(ListStocksInputBoundary viewOwnedStockInteractor);

    }
