package use_case.profit_loss;

/**
 * Data transfer object for the Profit Loss use case.
 * Encapsulates the profit/loss calculation results.
 */
public class ProfitLossOutputData {
    private final double totalProfitLoss;
    private final double stockProfitLoss;
    private final String tickerSymbol;

    public ProfitLossOutputData(double totalProfitLoss, double stockProfitLoss, String tickerSymbol) {
        this.totalProfitLoss = totalProfitLoss;
        this.stockProfitLoss = stockProfitLoss;
        this.tickerSymbol = tickerSymbol;
    }

    // Getters for totalProfitLoss, stockProfitLoss, and tickerSymbol
    public double getTotalProfitLoss() {
        return totalProfitLoss;
    }

    public double getStockProfitLoss() {
        return stockProfitLoss;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }
}