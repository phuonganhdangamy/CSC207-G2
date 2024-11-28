package use_case.login;

/**
 * Output boundary for the Login Use Case.
 * Interactor will call these methods after execution.
 * Presenter for login use case needs to implement these methods.
 */
public interface LoginOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(LoginOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the signup view for the Login Use Case.
     */
    void switchToSignUpView();
}
