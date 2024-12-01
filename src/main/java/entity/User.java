package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the system, including their login credentials, portfolio, balance,
 * and methods for account management.
 */
public class User {
    public static final double INITIAL_BALANCE = 10000;
    private static List<User> users = new ArrayList<>();

    private String username;
    private String password;
    private Portfolio portfolio;
    private ProfitLossCalculator profitLossCalculator;
    private double balance;

    /**
     * Constructs a new user with the specified username and password, initializing a portfolio and
     * a profit/loss calculator.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.portfolio = new Portfolio(this);
        this.profitLossCalculator = new ProfitLossCalculator(portfolio);
        this.balance = INITIAL_BALANCE;
    }

    /**
     * Constructs a new user with the specified username, password, portfolio, and balance.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param portfolio The user's portfolio.
     * @param balance The user's balance.
     */
    public User(String username, String password, Portfolio portfolio, double balance) {
        this.username = username;
        this.password = password;
        this.portfolio = portfolio;
        this.profitLossCalculator = new ProfitLossCalculator(portfolio);
        this.balance = balance;
    }

    /**
     * Logs in the user by checking the provided username and password against stored credentials.
     *
     * @param inputUsername The username to log in with.
     * @param inputPassword The password to log in with.
     * @return true if the login is successful, false otherwise.
     */
    public boolean login(String inputUsername, String inputPassword) {
        final boolean isSuccessful;
        if (checkCredentials(inputUsername, inputPassword)) {
            System.out.println("Login successful!");
            isSuccessful = true;
        }
        else {
            System.out.println("Invalid username or password.");
            isSuccessful = false;
        }
        return isSuccessful;
    }

    /**
     * Checks if the provided username and password match the stored credentials.
     *
     * @param inputUsername The username to check.
     * @param inputPassword The password to check.
     * @return true if the credentials match, false otherwise.
     */
    private boolean checkCredentials(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    /**
     * Logs out the user and prints a message indicating the user has been logged out.
     */
    public void logout() {
        System.out.println("Logged out successfully.");
    }

    /**
     * Creates a new user account if the provided username is unique.
     *
     * @param username The username for the new account.
     * @param password The password for the new account.
     * @return A new {@link User} object if the username is unique, or null if the username is already taken.
     */
    public static User isUniqueAccount(String username, String password) {
        // Check for unique username
        for (User user : users) {
            if (user.getName().equals(username)) {
                System.out.println("Username already taken.");
                return null;
            }
        }
        final User newUser = new User(username, password);
        users.add(newUser);
        return newUser;
    }

    /**
     * Updates the user's profile with a new username and password.
     *
     * @param newUsername The new username.
     * @param newPassword The new password.
     */
    public void updateProfile(String newUsername, String newPassword) {
        this.username = newUsername;
        this.password = newPassword;
        System.out.println("Profile updated successfully.");
    }

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public double getBalance() {
        return balance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
