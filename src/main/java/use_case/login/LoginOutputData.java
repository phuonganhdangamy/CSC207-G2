package use_case.login;

/**
 * The output data for the Login Use Case.
 */
public class LoginOutputData {
    private String username;
    private double balance;

    public LoginOutputData(String username, double balance) {
        this.username = username;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }
}
