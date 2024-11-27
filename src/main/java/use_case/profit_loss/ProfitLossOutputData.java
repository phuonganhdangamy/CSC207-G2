package use_case.profit_loss;

/**
 * Data transfer object for the Profit Loss use case.
 * Encapsulates the profit/loss calculation results.
 */
public class ProfitLossOutputData {

    private final double totalProfitLoss;
    private final double stockProfitLoss;
    private final String tickerSymbol;

    /**
     * Creates a new ProfitLossOutputData object.
     *
     * @param totalProfitLoss the total profit/loss for the portfolio
     * @param stockProfitLoss the profit/loss for a specific stock
     * @param tickerSymbol    the ticker symbol of the specific stock
     */
    public ProfitLossOutputData(double totalProfitLoss, double stockProfitLoss, String tickerSymbol) {
        this.totalProfitLoss = totalProfitLoss;
        this.stockProfitLoss = stockProfitLoss;
        this.tickerSymbol = tickerSymbol;
    }

    /**
     * Gets the total profit/loss for the portfolio.
     *
     * @return the total profit/loss value
     */
    public double getTotalProfitLoss() {
        return totalProfitLoss;
    }

    /**
     * Gets the profit/loss for the specific stock.
     *
     * @return the specific stock profit/loss value
     */
    public double getStockProfitLoss() {
        return stockProfitLoss;
    }

    /**
     * Gets the ticker symbol of the specific stock.
     *
     * @return the ticker symbol
     */
    public String getTickerSymbol() {
        return tickerSymbol;
    }
}
