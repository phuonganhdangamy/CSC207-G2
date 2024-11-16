package use_case.sell_stock;

import java.util.Map;

/**
 * Output Data class for the selling stock use case.
 */
public class SellStockOutputData {
    // Will need to update the balance for the user.
    private Double newBalance;
    // Keys for this map are of a ticker and the value is the number of shares owned
    private Map<String, Integer> newPortfolioData;

    public SellStockOutputData(Map<String, Integer> newPortfolioData, Double newBalance) {
        this.newPortfolioData = newPortfolioData;
        this.newBalance = newBalance;
    }
}
