package interface_adapter;

import java.util.List;
import java.util.Map;

/**
 * The State information representing the logged-in user.
 */
public class LoggedInState {
    // set the loggedInState after the user logs in
    // Basic user information
    private String username = "";
    private double balance;
    private String tickerField;
    private String numShares;
    private double totalPurchasePrice;
    private double totalCurrentPrice;
    private double totalProfitLoss;
    private String error;

    // Refactored stock data
    // Ticker -> Number of Shares
    private Map<String, Integer> stockOwnership;
    // Ticker -> Profit/Loss
    private Map<String, Double> stockProfitLoss;

    // First index is number of shares; second index is profit/loss
    private Map<String, List<Double>> stockTable;

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoggedInState() {
    }

    // Getters and Setters for Username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getters and Setters for Balance
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Getters and Setters for Stock Ownership (Refactored)
    public Map<String, Integer> getStockOwnership() {
        return stockOwnership;
    }

    public void setStockOwnership(Map<String, Integer> stockOwnership) {
        this.stockOwnership = stockOwnership;
    }

    // Getters and Setters for Stock Profit/Loss (Refactored)
    public Map<String, Double> getStockProfitLoss() {
        return stockProfitLoss;
    }

    public void setStockProfitLoss(Map<String, Double> stockProfitLoss) {
        this.stockProfitLoss = stockProfitLoss;
    }

    // Getters and Setters for Stock Table (Legacy)
    public Map<String, List<Double>> getStockTable() {
        return stockTable;
    }

    public void setStockTable(Map<String, List<Double>> stockTable) {
        this.stockTable = stockTable;
    }

    // Getters and Setters for Ticker Field and Number of Shares
    public String getTickerField() {
        return tickerField;
    }

    public void setTickerField(String tickerField) {
        this.tickerField = tickerField;
    }

    public String getNumShares() {
        return numShares;
    }

    public void setNumShares(String numShares) {
        this.numShares = numShares;
    }

    // Getters and Setters for Total Purchase Price
    public double getTotalPurchasePrice() {
        return totalPurchasePrice;
    }

    public void setTotalPurchasePrice(double totalPurchasePrice) {
        this.totalPurchasePrice = totalPurchasePrice;
    }

    // Getters and Setters for Total Current Price
    public double getTotalCurrentPrice() {
        return totalCurrentPrice;
    }

    public void setTotalCurrentPrice(double totalCurrentPrice) {
        this.totalCurrentPrice = totalCurrentPrice;
    }

    // Getters and Setters for Total Profit/Loss
    public double getTotalProfitLoss() {
        return totalProfitLoss;
    }

    public void setTotalProfitLoss(double totalProfitLoss) {
        this.totalProfitLoss = totalProfitLoss;
    }

    // Getters and Setters for Error
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
