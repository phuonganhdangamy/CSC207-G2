package use_case.list_stocks;

import java.util.HashMap;
import java.util.Map;

import entity.Portfolio;
import entity.User;

/**
 * The interactor for the List Stocks use case.
 * It handles the business logic of retrieving a user's stock ownership details.
 */
public class ListStocksInteractor implements ListStocksInputBoundary {

    private final ListStocksOutputBoundary outputBoundary;
    private final ListStocksUserDataAccessInterface userDataAccess;

    /**
     * Constructs a ListStocksInteractor.
     *
     * @param outputBoundary the output boundary interface for presenting the use case results; must not be null
     * @param userDataAccess the data access interface for retrieving user information; must not be null
     */
    public ListStocksInteractor(ListStocksOutputBoundary outputBoundary,
                                ListStocksUserDataAccessInterface userDataAccess) {
        this.outputBoundary = outputBoundary;
        this.userDataAccess = userDataAccess;
    }

    /**
     * Executes the List Stocks use case, retrieving a list of stocks and their counts for a user.
     *
     * @param inputData the input data containing the username; must not be null
     * @throws IllegalArgumentException if the user is not found
     */
    @Override
    public void execute(ListStocksInputData inputData) {
        final User user = userDataAccess.getCurrentUser();
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        final Portfolio portfolio = user.getPortfolio();
        final Map<String, Integer> stockCounts = new HashMap<>();
        portfolio.getStocks().forEach(stock -> {
            stockCounts.put(stock.getTickerSymbol(),
                    stockCounts.getOrDefault(stock.getTickerSymbol(), 0) + 1);
        });

        final ListStocksOutputData outputData = new ListStocksOutputData(stockCounts);
        outputBoundary.present(outputData);
    }
}

