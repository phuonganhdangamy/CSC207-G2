package interface_adapter;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * The State information representing the logged-in user.
 */
public class LoggedInState {
    //set the loggedInState after the user logs in
    private String username = "";
    private String tickerField;
    private String numShares;
    private double totalPurchasePrice;
    private double totalCurrentPrice;
    private double totalProfitLoss;
    private String error;

    private double balance;
    // First index is number of shares; second index is profit/loss
    private Map<String, List<Double>> stockTable;

    public Map<String, List<Double>> getStockTable() {
        return stockTable;
    }

    public void setStockTable(Map<String, List<Double>> stockTable) {
        this.stockTable = stockTable;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotalProfitLoss() {
        return totalProfitLoss;
    }

    public void setTotalProfitLoss(double totalProfitLoss) {
        this.totalProfitLoss = totalProfitLoss;
    }

    public double getTotalCurrentPrice() {
        return totalCurrentPrice;
    }

    public void setTotalCurrentPrice(double totalCurrentPrice) {
        this.totalCurrentPrice = totalCurrentPrice;
    }

    public double getTotalPurchasePrice() {
        return totalPurchasePrice;
    }

    public void setTotalPurchasePrice(double totalPurchasePrice) {
        this.totalPurchasePrice = totalPurchasePrice;
    }

    public String getNumShares() {
        return numShares;
    }

    public void setNumShares(String numShares) {
        this.numShares = numShares;
    }

    public String getTickerField() {
        return tickerField;
    }

    public void setTickerField(String tickerField) {
        this.tickerField = tickerField;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }


    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoggedInState() {

    }
    /**
     * Updates the stock-specific profit/loss in the stock table.
     * If the stock doesn't exist in the table, it initializes it.
     *
     * @param tickerSymbol    the stock ticker symbol
     * @param stockProfitLoss the profit/loss value for the specific stock
     */
    public void setStockProfitLoss(String tickerSymbol, double stockProfitLoss) {
        if (stockTable.containsKey(tickerSymbol)) {
            stockTable.get(tickerSymbol).set(1, stockProfitLoss); // Update profit/loss at index 1
        } else {
            // Initialize with 0 shares and the given profit/loss
            stockTable.put(tickerSymbol, List.of(0.0, stockProfitLoss));
        }
    }
}