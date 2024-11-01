package entity;

import javax.sound.sampled.Port;

public class User {
    private String username;
    private String password;
    private Portfolio portfolio;
    private double balance = 10000;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        portfolio = new Portfolio();
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

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
