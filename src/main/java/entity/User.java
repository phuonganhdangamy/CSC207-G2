package entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private Portfolio portfolio;
    private ProfitLossCalculator profitLossCalculator;

    // Static list to hold users to ensure each username is unique
    private static List<User> users = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.portfolio = new Portfolio(this);
        this.profitLossCalculator = new ProfitLossCalculator(portfolio);
    }

    public User(String username, String password, Portfolio portfolio, double balance) {
        this.username = username;
        this.password = password;
        this.portfolio = portfolio;
        this.balance = balance;
    }

    // Static method to create an account
    public static User isUniqueAccount(String username, String password) {
        // Check for unique username
        for (User user : users) {
            if (user.getName().equals(username)) {
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

    // Method to view total profit or loss
    public void viewProfitLoss() {
        double profitLoss = profitLossCalculator.calculateTotalProfitLoss();
        System.out.println("Your total profit/loss: $" + profitLoss);
    }

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Portfolio getStockPortfolio() {
        return stockPortfolio;
    }
}