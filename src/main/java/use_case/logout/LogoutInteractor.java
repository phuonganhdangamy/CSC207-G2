package use_case.logout;

import use_case.UserDataAccessInterface;

/**
 * Interactor for logout use case.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private final LogoutOutputBoundary logoutPresenter;
    private final UserDataAccessInterface userDataAccess;

    // Constructor requires access to the presenter and data layer for logout process
    public LogoutInteractor(UserDataAccessInterface userDataAccess, LogoutOutputBoundary logoutPresenter) {
        this.userDataAccess = userDataAccess;
        this.logoutPresenter = logoutPresenter;
    }

    @Override
    public void execute(LogoutInputData logoutInputData) {
        String username = logoutInputData.getUsername();

        // Perform logout operation - clearing session or updating status in data layer
        boolean success = userDataAccess.logoutUser(username); // Method in data access interface

        // Notify presenter of the result
        if (success) {
            LogoutOutputData outputData = new LogoutOutputData(true, "Logout successful");
            logoutPresenter.prepareSuccessView(outputData);
        } else {
            logoutPresenter.prepareFailView("Logout failed, please try again");
        }
    }
}


