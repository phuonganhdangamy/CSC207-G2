package use_case.sell_stock;

/**
 * The input data for the Selling Stock Use Case.
 */
public class SellStockInputData {
    private String ticker;
    private int quantity;

    public SellStockInputData(int quantity, String ticker) {
        this.quantity = quantity;
        this.ticker = ticker;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTicker() {
        return ticker;
    }
}
