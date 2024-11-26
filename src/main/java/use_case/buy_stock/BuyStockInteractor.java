package use_case.buy_stock;

import entity.*;
import data_access.*;

/**
 * Interactor for buy stock use case.
 */
public class BuyStockInteractor implements BuyStockInputBoundary {

    private final BuyStockOutputBoundary outputBoundary;
    private final BuyStockUserDataAccessInterface userDAO;
    private final DBStockDataAccessObject stockDAO;

    public BuyStockInteractor(BuyStockOutputBoundary outputBoundary, BuyStockUserDataAccessInterface userDAO,
                              DBStockDataAccessObject stockDAO) {
        this.outputBoundary = outputBoundary;
        this.userDAO = userDAO;
        this.stockDAO = stockDAO;
    }

    @Override
    public void execute(BuyStockInputData inputData) {
        String username = inputData.getUsername();
        User user = userDAO.get(username);

        if (user == null) {
            outputBoundary.prepareFailView(username + ": Account does not exist.");
            return;
        }

        String tickerSymbol = inputData.getTickerSymbol();
        double stockCost = stockDAO.getCost(tickerSymbol);
        int numberOfShares = inputData.getNumberOfShares();
        double totalCost = stockCost * numberOfShares;
        double balance = user.getBalance();

        // Check if the stock exists
        if (stockCost == 0) {
            outputBoundary.prepareFailView("Stock does not exist.");
            return;
        }

        // Check if the user has sufficient balance to buy the stock
        if (balance < totalCost) {
            outputBoundary.prepareFailView("Insufficient balance.");
            return;
        }

        // Update user balance and portfolio
        user.setBalance(balance - totalCost);
        for (int i = 0; i < numberOfShares; i++) {
            user.getPortfolio().addStock(new Stock(tickerSymbol, stockCost));
        }

        // Save updated user info
        userDAO.saveUserInfo(user);

        // Prepare success view with updated data
        outputBoundary.prepareSuccessView(new BuyStockOutputData(balance, tickerSymbol, numberOfShares));
    }
}