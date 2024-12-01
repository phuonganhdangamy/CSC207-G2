package use_case.login;

import entity.User;
import use_case.list_stocks.ListStocksInputBoundary;
import use_case.list_stocks.ListStocksInputData;
import use_case.profit_loss.ProfitLossInputBoundary;

/**
 * Interactor for login use case.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginOutputBoundary loginPresenter;
    private LoginUserDataAccessInterface userDataAccess;

    // Need access to the view stock use case interactor and profit loss interactor to update UI
    private ListStocksInputBoundary viewOwnedStockInteractor;
    private ProfitLossInputBoundary profitLossInteractor;

    // Interactor requires access to the database and needs to pass the result to the presenter.
    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface, LoginOutputBoundary outputBoundary) {
        this.userDataAccess = userDataAccessInterface;
        this.loginPresenter = outputBoundary;
    }

    public void setProfitLossInteractor(ProfitLossInputBoundary profitLossInteractor) {
        this.profitLossInteractor = profitLossInteractor;
    }

    public void setViewOwnedStockInteractor(ListStocksInputBoundary viewOwnedStockInteractor) {
        this.viewOwnedStockInteractor = viewOwnedStockInteractor;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        // Get the username and password entered by the user
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        // Check if the username exists in the database
        if (!userDataAccess.existsByName(username) || "".equals(username) || "".equals(password)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            // If the user is found, check if the encrypted password matches the entered password
            final User userFound = userDataAccess.get(username);
            if (!userFound.getPassword().equals(password)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {
                userDataAccess.setCurrentUsername(username);
                userDataAccess.setCurrentUser(userFound);

                viewOwnedStockInteractor.execute(new ListStocksInputData(username));
                profitLossInteractor.execute();

                // If the password is correct, indicate success
                final LoginOutputData output = new LoginOutputData(username, userFound.getBalance());
                loginPresenter.prepareSuccessView(output);
            }
        }
    }

    @Override
    public void switchToSignUpView() {
        loginPresenter.switchToSignUpView();
    }
}
