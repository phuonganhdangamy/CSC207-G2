package use_case.login;

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

    void switchToSignUpView();
}
