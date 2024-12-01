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

    /**
     * Sets the interactor responsible for viewing owned stocks after successful login.
     * @param viewOwnedStockInteractor The interactor that handles the view owned stocks functionality.
     */
    void setViewOwnedStockInteractor(ListStocksInputBoundary viewOwnedStockInteractor);

    /**
     * Sets the interactor responsible for calculating the user's profit/loss after login.
     * @param profitLossInteractor The interactor that handles the profit/loss calculation functionality.
     */
    void setProfitLossInteractor(ProfitLossInputBoundary profitLossInteractor);

    /**
     * Switches to the sign-up view.
     */
    void switchToSignUpView();
}
