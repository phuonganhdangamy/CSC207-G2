package use_case.login;

import java.util.Map;

/**
 * The output data for the Login Use Case.
 */
public class LoginOutputData {
    private String username;
    private double balance;
    // Keys for this map are of a ticker and the value is the number of shares owned
    private Map<String, Integer> portfolioData;


    public LoginOutputData(String username, double balance, Map<String, Integer> portfolioData) {
        this.username = username;
        this.balance = balance;
        this.portfolioData = portfolioData;

    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getPortfolioData() {
        return portfolioData;
    }

    public double getBalance() {
        return balance;
    }
}
