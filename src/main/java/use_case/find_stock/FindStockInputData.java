package use_case.find_stock;

/**
 * The input data for the Find Stock Use Case.
 */
public class FindStockInputData {
    private String tickerSymbol;

    public FindStockInputData(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }
}
