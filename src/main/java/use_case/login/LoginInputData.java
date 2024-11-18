package use_case.login;

/**
 * The input data for the Login Use Case.
 */
public class LoginInputData {
    private String username;
    private String password;

    public LoginInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
