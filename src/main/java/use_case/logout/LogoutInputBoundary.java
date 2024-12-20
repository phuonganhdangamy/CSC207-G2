package use_case.logout;

/**
 * Input Boundary for actions which are related to logging out.
 */
public interface LogoutInputBoundary {

    /**
     * Executes the Logout use case.
     * @param logoutInputData the input data
     */
    void execute(LogoutInputData logoutInputData);
}
