package use_case.profit_loss;

import entity.Portfolio;
import entity.ProfitLossCalculator;
import use_case.find_stock.FindStockDataAccessInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Interactor for the Profit Loss use case.
 * Handles the core logic for calculating total profit/loss and stock-specific profit/loss
 * based on the user's portfolio and current stock data.
 */
public class ProfitLossInteractor implements ProfitLossInputBoundary {

    private final ProfitLossDataAccessInterface userDataAccess;
    private final ProfitLossOutputBoundary outputBoundary;
    private final FindStockDataAccessInterface stockDataAccess;

    /**
     * Constructs a new ProfitLossInteractor.
     *
     * @param userDataAccess   the interface for accessing user and portfolio-related data.
     * @param outputBoundary   the interface for passing results to the presenter.
     * @param stockDataAccess  the interface for accessing stock-related data.
     */
    public ProfitLossInteractor(
            ProfitLossDataAccessInterface userDataAccess,
            ProfitLossOutputBoundary outputBoundary, FindStockDataAccessInterface stockDataAccess
    ) {
        this.userDataAccess = userDataAccess;
        this.outputBoundary = outputBoundary;
        this.stockDataAccess = stockDataAccess;
    }

    /**
     * Executes the profit/loss calculation use case.
     * This includes:
     * <ul>
     *     <li>Retrieving the user's portfolio and stock data.</li>
     *     <li>Calculating the profit/loss for individual stocks.</li>
     *     <li>Calculating the total profit/loss for the portfolio.</li>
     *     <li>Passing the results to the presenter.</li>
     * </ul>
     */
    @Override
    public void execute() {
        // Fetch the user's portfolio
        Portfolio portfolio = userDataAccess.getCurrentUser().getPortfolio();

        // Fetch unique stock ticker symbols from the portfolio
        Set<String> uniqueTickerSymbols = portfolio.getTickerSymbols();

        // Fetch current prices for all stocks in the portfolio using stockDataAccess
        Map<String, Double> currentPrices = new HashMap<>();
        for (String tickerSymbol : uniqueTickerSymbols) {
            currentPrices.put(tickerSymbol, stockDataAccess.getCost(tickerSymbol));
        }

        // For debug
        System.out.println("Current prices: " + currentPrices);
        System.out.println("Current tickers: " + uniqueTickerSymbols);

        // Use the ProfitLossCalculator to calculate results
        ProfitLossCalculator calculator = new ProfitLossCalculator(portfolio);

        Map<String, Double> stockProfitLosses = new HashMap<>();
        for (String tickerSymbol : uniqueTickerSymbols) {
            double currentPrice = currentPrices.get(tickerSymbol);
            double stockProfitLoss = calculator.calculateStockProfitLoss(tickerSymbol, currentPrice);
            stockProfitLosses.put(tickerSymbol, stockProfitLoss);
        }

        // Calculate the total profit/loss for the portfolio
        double totalProfitLoss = calculator.calculateTotalProfitLoss(stockProfitLosses);

        // Combine results into a single output data object
        ProfitLossOutputData outputData = new ProfitLossOutputData(totalProfitLoss, stockProfitLosses);

        // For debug
        System.out.println("each stock P&L: " + stockProfitLosses);
        System.out.println("total P&L: " + totalProfitLoss);

        // Send the unified result to the presenter
        outputBoundary.success(outputData);
    }
}
