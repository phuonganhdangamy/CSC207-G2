package use_case.sell_stock;

import java.util.Map;

/**
 * Output Data class for the selling stock use case.
 */
public class SellStockOutputData {
    // Will need to update the balance for the user.
    private double newBalance;


    public SellStockOutputData(double newBalance) {
        this.newBalance = newBalance;
    }

    public double getNewBalance() {
        return newBalance;
    }

}
