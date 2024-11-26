package use_case.buy_stock;

import entity.*;
import data_access.*;
import use_case.list_stocks.ListStocksInputBoundary;
import use_case.list_stocks.ListStocksInputData;
import use_case.list_stocks.ListStocksInteractor;
import use_case.profit_loss.ProfitLossInputBoundary;
import use_case.profit_loss.ProfitLossInputData;

/**
 * Interactor for buy stock use case.
 */
public class BuyStockInteractor implements BuyStockInputBoundary {

    private final BuyStockOutputBoundary buyStockPresenter;
    private final BuyStockUserDataAccessInterface database;
    private final DBStockDataAccessObject stockDatabase;

    // Need access to the view stock use case interactor and profit loss interactor to update UI
    private final ListStocksInputBoundary viewOwnedStockInteractor;
    private final ProfitLossInputBoundary profitLossInteractor;

    public BuyStockInteractor(BuyStockOutputBoundary buyStockPresenter, BuyStockUserDataAccessInterface database,
                              DBStockDataAccessObject stockDatabase, ListStocksInteractor viewOwnedStockInteractor, ProfitLossInputBoundary profitLossInteractor) {
        this.buyStockPresenter = buyStockPresenter;
        this.database = database;
        this.stockDatabase = stockDatabase;
        this.viewOwnedStockInteractor = viewOwnedStockInteractor;
        this.profitLossInteractor = profitLossInteractor;
    }

    @Override
    public void execute(BuyStockInputData inputData) {
        String ticker = inputData.getTickerSymbol();
        int quantity = inputData.getQuantity();
        User user = database.getCurrentUser();
        Portfolio userPortfolio = user.getPortfolio();

        // Checking the number of shares already owned by the user
        int numberOfShares = userPortfolio.getShareCount(ticker);

        // Check if the stock exists
        if (!stockDatabase.isStockExist(ticker)) {
            buyStockPresenter.prepareFailView("The ticker does not exist.");
            return;
        }

        // Getting price of the stocks according to the number of the stocks and their current price
        double stockCost = stockDatabase.getCost(ticker);
        double totalCost = stockCost * quantity;
        double balance = user.getBalance();


        // Check if the user has sufficient balance to buy the stock
        if (balance < totalCost) {
            buyStockPresenter.prepareFailView("Insufficient balance.");
            return;
        } else {
            // Update user balance and portfolio
            user.setBalance(balance - totalCost);

            for (int i = 0; i < quantity; i++) {
                user.getPortfolio().addStock(new Stock(ticker, stockCost));
            }

            // Save the updated user info
            database.saveUserInfo(user);

            viewOwnedStockInteractor.execute(new ListStocksInputData(user.getName()));
            profitLossInteractor.calculateTotalProfitLoss(new ProfitLossInputData(user.getName()));

            // Prepare success view with updated data
            buyStockPresenter.prepareSuccessView(new BuyStockOutputData(balance, ticker, numberOfShares));
        }


    }
}