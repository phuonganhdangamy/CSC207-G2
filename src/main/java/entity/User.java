package entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private Portfolio stockPortfolio;
    private ProfitLossCalculator profitLossCalculator;

    // Static list to hold users to ensure each username is unique
    private static List<User> users = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.stockPortfolio = new Portfolio(this);
        this.profitLossCalculator = new ProfitLossCalculator(stockPortfolio);
    }

    // Method to log in
    public boolean login(String username, String password) {
        if (checkCredentials(username, password)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    // Helper method to check credentials
    private boolean checkCredentials(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Method to log out
    public void logout() {
        System.out.println("Logged out successfully.");
    }

    // Static method to create an account
    public static User createAccount(String username, String password) {
        // Check for unique username
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already taken.");
                return null; // Or throw an exception
            }
        }
        User newUser = new User(username, password);
        users.add(newUser);
        return newUser;
    }

    // Method to update user profile
    public void updateProfile(String newUsername, String newPassword) {
        this.username = newUsername;
        this.password = newPassword;
        System.out.println("Profile updated successfully.");
    }

    // Method to view portfolio balance
    public double viewBalance() {
        return stockPortfolio.getBalance();
    }

    // Method to view portfolio stocks
    public void viewPortfolio() {
        System.out.println("Current Balance: $" + viewBalance());
        stockPortfolio.viewStocks();
    }

    // Method to view total profit or loss
    public void viewProfitLoss() {
        double profitLoss = profitLossCalculator.calculateTotalProfitLoss();
        System.out.println("Your total profit/loss: $" + profitLoss);
    }

    public String getUsername() {
        return username;
    }

    private String getPassword() {
        return password;
    }

    public Portfolio getStockPortfolio() {
        return stockPortfolio;
    }
}