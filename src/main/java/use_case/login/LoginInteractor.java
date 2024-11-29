package use_case.login;

import entity.Stock;
import entity.User;
import use_case.UserDataAccessInterface;
import use_case.list_stocks.ListStocksInputBoundary;
import use_case.list_stocks.ListStocksInputData;
import use_case.profit_loss.ProfitLossInputBoundary;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interactor for login use case.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginOutputBoundary loginPresenter;
    private LoginUserDataAccessInterface userDataAccess;

    // Need access to the view stock use case interactor and profit loss interactor to update UI
    private ListStocksInputBoundary viewOwnedStockInteractor;
    private ProfitLossInputBoundary profitLossInteractor;


    //Interactor requires access to the database and needs to pass the result to the presenter.
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
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();

        // Check if the username exists in the database
        if (!userDataAccess.existsByName(username) || username.equals("") || password.equals("")) {
            loginPresenter.prepareFailView( username + ": Account does not exist.");
        }
        else {
            //If the user is found, check if the encrypted password matches the entered password
            User userFound = userDataAccess.get(username);
            if (!userFound.getPassword().equals(password)){
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {
                userDataAccess.setCurrentUsername(username);
                userDataAccess.setCurrentUser(userFound);

                viewOwnedStockInteractor.execute(new ListStocksInputData(username));
                profitLossInteractor.execute();

                //If the password is correct, indicate success
                LoginOutputData output = new LoginOutputData(username, userFound.getBalance());
                loginPresenter.prepareSuccessView(output);

            }

        }

    }

    @Override
    public void switchToSignUpView() {
        loginPresenter.switchToSignUpView();

    }
}
