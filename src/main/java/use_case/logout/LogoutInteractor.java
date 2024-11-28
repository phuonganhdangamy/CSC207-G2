package use_case.logout;

/**
 * Interactor for logout use case.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private final LogoutOutputBoundary logoutPresenter;
    private final LogoutUserDataAccessInterface userDataAccess;

    // Constructor requires access to the presenter and data layer for logout process
    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccess, LogoutOutputBoundary logoutPresenter) {
        this.userDataAccess = userDataAccess;
        this.logoutPresenter = logoutPresenter;
    }

    @Override
    public void execute(LogoutInputData logoutInputData) {
        final String username = logoutInputData.getUsername();

        // Perform logout operation - clearing session or updating status in data layer
        // Method in data access interface
        final boolean success = userDataAccess.logoutUser(username);

        // Notify presenter of the result
        if (success) {
            final LogoutOutputData outputData = new LogoutOutputData(true, "Logout successful",
                    username);
            logoutPresenter.prepareSuccessView(outputData);
        }
        else {
            logoutPresenter.prepareFailView("Logout failed, please try again");
        }
    }
}
