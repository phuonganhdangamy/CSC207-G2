package use_case.find_stock;

/**
 * The output data for the Find Stock Use Case.
 */
public class FindStockOutputData {
    private String tickerSymbol;
    private double currentCost;

    public FindStockOutputData(String tickerSymbol, double currentCost) {
        this.tickerSymbol = tickerSymbol;
        this.currentCost = currentCost;
    }

    public double getCurrentCost() {
        return currentCost;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }
}
