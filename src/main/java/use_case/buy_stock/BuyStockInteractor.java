package use_case.buy_stock;

import entity.*;
import data_access.*;

public class BuyStockInteractor implements BuyStockInputBoundary {

    private final BuyStockOutputBoundary outputBoundary;
    private final BuyStockUserDataAccessInterface userDAO;
    private final DBStockDataAccessObject stockDAO;

    public BuyStockInteractor(BuyStockOutputBoundary outputBoundary, BuyStockUserDataAccessInterface userDAO, DBStockDataAccessObject stockDAO) {
        this.outputBoundary = outputBoundary;
        this.userDAO = userDAO;
        this.stockDAO = stockDAO;
    }

    @Override
    public void execute(BuyStockInputData inputData) {
        User user = userDAO.getUserByUsername(inputData.getUsername());
        if (user == null) {
            outputBoundary.prepareFailView("User not found.");
            return;
        }

        double stockCost = stockDAO.setCost(new Stock(inputData.getTickerSymbol(), 0));
        double totalCost = stockCost * inputData.getNumberOfShares();

        if (user.getBalance() < totalCost) {
            outputBoundary.prepareFailView("Insufficient balance.");
            return;
        }

        user.setBalance(user.getBalance() - totalCost);
        for (int i = 0; i < inputData.getNumberOfShares(); i++) {
            user.getPortfolio().addStock(new Stock(inputData.getTickerSymbol(), stockCost));
        }

        userDAO.saveUser(user);
        outputBoundary.prepareSuccessView(new BuyStockOutputData(user.getBalance(), inputData.getTickerSymbol(), inputData.getNumberOfShares()));
    }
}
