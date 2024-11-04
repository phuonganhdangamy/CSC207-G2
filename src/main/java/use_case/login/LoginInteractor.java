package use_case.login;

import entity.User;
import use_case.UserDataAccessInterface;

/**
 * Interactor for login use case.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginOutputBoundary loginPresenter;
    private UserDataAccessInterface userDataAccess;

    //Interactor requires access to the database and needs to pass the result to the presenter.
    public LoginInteractor(UserDataAccessInterface userDataAccessInterface, LoginOutputBoundary outputBoundary) {
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
            loginPresenter.prepareFailView("User not found");
        }
        else {
            //If the user is found, check if the encrypted password matches the entered password
            User userFound = userDataAccess.get(username);
            if (!userFound.getPassword().equals(password)){
                loginPresenter.prepareFailView("Incorrect password");
            }
            else {
                LoginOutputData output = new LoginOutputData(username);
                loginPresenter.prepareSuccessView(output);

            }

        }

    }
}
