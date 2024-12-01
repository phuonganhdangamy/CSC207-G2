package interface_adapter.buy_stock;

/**
 * Represents the state of the buy stock process.
 */
public class BuyStockState {
    private String selectedStock;
    private int quantity;
    private double userBalance;

    public BuyStockState(String selectedStock, int quantity, double userBalance) {
        this.selectedStock = selectedStock;
        this.quantity = quantity;
        this.userBalance = userBalance;
    }

    // Getters and Setters
    public String getSelectedStock() {
        return selectedStock;
    }

    public void setSelectedStock(String selectedStock) {
        this.selectedStock = selectedStock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    @Override
    public String toString() {
        return "BuyStockState{"
                + "selectedStock='" + selectedStock + '\''
                + ", quantity=" + quantity
                + ", userBalance=" + userBalance
                + '}';
    }
}
