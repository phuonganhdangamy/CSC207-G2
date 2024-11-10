package use_case.logout;

/**
 * The output data for the Logout Use Case.
 */
public class LogoutOutputData {
    private final boolean success;
    private final String message;

    public LogoutOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
