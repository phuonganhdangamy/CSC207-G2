package use_case.profit_loss;

import entity.Portfolio;
import entity.ProfitLossCalculator;
import entity.Stock;
import use_case.find_stock.FindStockDataAccessInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Interactor for the Profit Loss use case.
 */
public class ProfitLossInteractor implements ProfitLossInputBoundary {

    private final ProfitLossDataAccessInterface userDataAccess;
    private final ProfitLossOutputBoundary outputBoundary;
    private final FindStockDataAccessInterface stockDataAccess;
    // TODO: I decided to keep your original DataAccessInterface as DBUser to avoid further confusion. I added
    // FindStockDataAccessInterface stockDataAccess so that I can still access DBStock.

    public ProfitLossInteractor(
            ProfitLossDataAccessInterface userDataAccess,
            ProfitLossOutputBoundary outputBoundary, FindStockDataAccessInterface stockDataAccess
    ) {
        this.userDataAccess = userDataAccess;
        this.outputBoundary = outputBoundary;
        this.stockDataAccess = stockDataAccess;
    }

    /**
     * New execute method to calculate both total and stock-specific profit/loss,
     * and pass one unified result to the presenter.
     */
    @Override
    public void execute() {
        // Fetch the user's portfolio
        Portfolio portfolio = userDataAccess.getCurrentUser().getPortfolio();
//
//        // Build the stock prices map dynamically from the portfolio - purchase prices
        // TODO: PROBLEM - HashMap doesn't allow duplicated keys. It means that if you have 2 AAPL stock in your account,
        // then... using this map won't fully record all your owned stocks!
        // TODO: SOLUTION - I implemented an additional method in the Portfolio entity - check it out!

//        Map<String, Double> purchasePrices = new HashMap<>();
//        for (Stock stock : portfolio.getStocks()) {
//            purchasePrices.put(stock.getTickerSymbol(), stock.getCost()); // Assuming 'getCost()' gives purchase price
//        }

        Set<String> uniqueTickerSymbols = portfolio.getTickerSymbols();
        // TODO: I only care about ticker symbols because at the end of the day we won't pass in current prices into
        // methods in ProfitLossCalculator entity - they can access it themselves!

        // TODO: Once we get all the ticker symbols, we search for the current price of corresponding ticker, using
        // the instance 'stockDataAccess'.
        Map<String, Double> currentPrices = new HashMap<>();
        for (String tickerSymbol : uniqueTickerSymbols) {
            currentPrices.put(tickerSymbol, stockDataAccess.getCost(tickerSymbol));
        }
        System.out.println("Current prices: " + currentPrices);
        System.out.println("Current tickers: " + uniqueTickerSymbols);
        System.out.println();

        // Use the ProfitLossCalculator to calculate results
        ProfitLossCalculator calculator = new ProfitLossCalculator(portfolio);
//        double totalProfitLoss = calculator.calculateTotalProfitLoss(purchasePrices);

        // TODO: Then we move on to this step: calculate stock-specific profit/loss.
        // Calculate stock-specific profit/loss
        Map<String, Double> stockProfitLosses = new HashMap<>();
//        for (String ticker : purchasePrices.keySet()) {
//            stockProfitLosses.put(ticker, calculator.calculateStockProfitLoss(ticker, purchasePrices.get(ticker)));
//        }
        for (String tickerSymbol : uniqueTickerSymbols) {
            double currentPrice = currentPrices.get(tickerSymbol);
            double stockProfitLoss = calculator.calculateStockProfitLoss(tickerSymbol, currentPrice);
            stockProfitLosses.put(tickerSymbol, stockProfitLoss);
        }

        // TODO: Then we compute the total P&L:
        double totalProfitLoss = calculator.calculateTotalProfitLoss(stockProfitLosses);

        // Combine results into a single output data object
         ProfitLossOutputData outputData = new ProfitLossOutputData(totalProfitLoss, stockProfitLosses);

        System.out.println("each stock P&L: " + stockProfitLosses);
        System.out.println("total P&L: " + totalProfitLoss);

        // Send the unified result to the presenter
//        outputBoundary.success(outputData);
    }
}
