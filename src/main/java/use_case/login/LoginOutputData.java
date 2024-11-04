package use_case.login;
/**
 * The output data for the Login Use Case.
 */
public class LoginOutputData {
    private String username;


    public LoginOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}