package use_case.sell_stock;

import entity.Portfolio;
import entity.User;
import use_case.find_stock.FindStockDataAccessInterface;
import use_case.list_stocks.ListStocksInputBoundary;
import use_case.list_stocks.ListStocksInputData;
import use_case.profit_loss.ProfitLossInputBoundary;

/**
 * Interactor for the Sell Stock use case.
 */
public class SellStockInteractor implements SellStockInputBoundary {
    // Needs access to the database
    // Needs to send results to the view
    private final SellStockUserDataAccessInterface database;
    private final SellStockOutputBoundary sellStockPresenter;
    private final FindStockDataAccessInterface stockDatabase;

    // Need access to the view stock use case interactor and profit loss interactor to update UI
    private ListStocksInputBoundary viewOwnedStockInteractor;
    private ProfitLossInputBoundary profitLossInteractor;

    public SellStockInteractor(SellStockOutputBoundary sellStockPresenter, SellStockUserDataAccessInterface database,
                               FindStockDataAccessInterface stockDatabase) {
        this.sellStockPresenter = sellStockPresenter;
        this.database = database;
        this.stockDatabase = stockDatabase;
    }

    public void setProfitLossInteractor(ProfitLossInputBoundary profitLossInteractor) {
        this.profitLossInteractor = profitLossInteractor;
    }

    public void setViewOwnedStockInteractor(ListStocksInputBoundary viewOwnedStockInteractor) {
        this.viewOwnedStockInteractor = viewOwnedStockInteractor;
    }

    @Override
    public void execute(SellStockInputData inputData) {
        final String ticker = inputData.getTicker();
        final int quantity = inputData.getQuantity();
        // Get the current user
        final User user = database.getCurrentUser();
        final Portfolio userPortfolio = user.getPortfolio();

        // Check the number of shares owned associated with the ticker
        final int numSharesOwned = userPortfolio.getShareCount(ticker);

        // Check if the company exists.
        if (!stockDatabase.isStockExist(ticker)) {
            sellStockPresenter.prepareFailView("This ticker does not exist.");
        }

        // If we own more than the number of shares that the user wishes to sell,
        // this can be executed
        else if (numSharesOwned >= quantity) {
            // Get current market price for the stock
            final double marketPrice = stockDatabase.getCost(ticker);
            // removes the stock and updates the balance
            for (int i = 0; i < quantity; i++) {
                userPortfolio.removeStock(ticker, marketPrice);
            }

            // Save the user's information in the online database
            database.saveUserInfo(user);

            // define the output needed by the presenter
            final double newBalance = user.getBalance();

            final SellStockOutputData output = new SellStockOutputData(newBalance);
            sellStockPresenter.prepareSuccessView(output);

            // Update UI by calling the view owned stock use case and profit loss use case after
            // selling shares

            viewOwnedStockInteractor.execute(new ListStocksInputData(user.getName()));
            profitLossInteractor.execute();

        }
        else {
            sellStockPresenter.prepareFailView(user.getName()
                    + ", you don't have enough shares of this company to sell.");
        }

    }
}
