package use_case.login;

import entity.Stock;
import entity.User;
import use_case.UserDataAccessInterface;

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

    //Interactor requires access to the database and needs to pass the result to the presenter.
    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface, LoginOutputBoundary outputBoundary) {
        this.userDataAccess = userDataAccessInterface;
        this.loginPresenter = outputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        // Get the username and password entered by the user
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();

        // Check if the username exists in the database
        if (!userDataAccess.existsByName(username)){
            loginPresenter.prepareFailView(username + ": Account does not exist.");
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
                // Transform the portfolio data from the entity class to match what is required of the
                // LoginOutputData class. Portfolio data needs to be converted to a map where each key is a
                // ticker and the value is the number of shares owned
                List<Stock> portfolio = userFound.getPortfolio().getStocks();
                Map<String, Integer> portfolioData = new HashMap<>();
                for (Stock stock : portfolio) {
                    String ticker = stock.getTickerSymbol();
                    if(portfolioData.containsKey(ticker)){
                        Integer oldValue = portfolioData.get(ticker);
                        portfolioData.replace(ticker, oldValue+1);
                    }
                    else{
                        portfolioData.put(ticker, 1);
                    }
                }
                //If the password is correct, indicate success
                LoginOutputData output = new LoginOutputData(username, userFound.getBalance(), portfolioData);
                loginPresenter.prepareSuccessView(output);

            }

        }

    }
}
