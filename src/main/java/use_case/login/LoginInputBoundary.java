package use_case.login;

import use_case.list_stocks.ListStocksInputBoundary;
import use_case.profit_loss.ProfitLossInputBoundary;

/**
 * Input Boundary for actions which are related to logging in.
 * Controller for login use case will call these methods which need to be implemented in the
 * interactor.
 */
public interface LoginInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(LoginInputData loginInputData);

    public void setViewOwnedStockInteractor(ListStocksInputBoundary viewOwnedStockInteractor);
    public void setProfitLossInteractor(ProfitLossInputBoundary profitLossInteractor);

    void switchToSignUpView();
}
