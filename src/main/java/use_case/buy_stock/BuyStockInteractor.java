package use_case.buy_stock;

import entity.Portfolio;
import entity.Stock;
import entity.User;
import use_case.find_stock.FindStockDataAccessInterface;
import use_case.list_stocks.ListStocksInputBoundary;
import use_case.list_stocks.ListStocksInputData;
import use_case.profit_loss.ProfitLossInputBoundary;

/**
 * The BuyStockInteractor class contains the business logic for handling the "buy stock" use case.
 * It processes the user's request to purchase stocks, including verifying stock availability, checking
 * the user's balance, and updating the portfolio. It also interacts with other components to update the UI
 * and calculate profit or loss after the purchase.
 * Responsibilities:
 * - Executes the business logic for purchasing stock, including validation and updates.
 * - Checks if the stock exists, whether the user has enough balance, and updates the portfolio.
 * - Interacts with the user data access interface to save the updated user data.
 * - Invokes other use case interactors to update the UI and calculate profit or loss.
 * Constructor(s):
 * - BuyStockInteractor(BuyStockOutputBoundary buyStockPresenter,
 *                      BuyStockUserDataAccessInterface database,
 *                      FindStockDataAccessInterface stockDatabase): Initializes the interactor
 *                      with the necessary components for executing the use case.
 * Method(s):
 * - execute(BuyStockInputData inputData): Executes the buy stock use case, handling validation, purchase,
 *   and updating the user's portfolio and balance.
 * - setProfitLossInteractor(ProfitLossInputBoundary profitLossInteractor): Sets the interactor responsible
 *   for calculating profit and loss after the purchase.
 * - setViewOwnedStockInteractor(ListStocksInputBoundary viewOwnedStockInteractor): Sets the interactor
 *   responsible for updating the UI with the user's stock portfolio.
 */

public class BuyStockInteractor implements BuyStockInputBoundary {

    private final BuyStockOutputBoundary buyStockPresenter;
    private final BuyStockUserDataAccessInterface database;
    private final FindStockDataAccessInterface stockDatabase;

    // Need access to the view stock use case interactor and profit loss interactor to update UI
    private ListStocksInputBoundary viewOwnedStockInteractor;
    private ProfitLossInputBoundary profitLossInteractor;

    /**
     * Constructor for initializing the interactor with the necessary components for executing
     * the "buy stock" use case.
     *
     * @param buyStockPresenter The presenter that will display the results of the buy stock use case.
     * @param database The user data access interface for retrieving and saving user data.
     * @param stockDatabase The stock data access interface for checking stock availability and cost.
     */

    public BuyStockInteractor(BuyStockOutputBoundary buyStockPresenter, BuyStockUserDataAccessInterface database,
                              FindStockDataAccessInterface stockDatabase) {

        this.buyStockPresenter = buyStockPresenter;
        this.database = database;
        this.stockDatabase = stockDatabase;
    }

    /**
     * Sets the interactor responsible for calculating the user's profit or loss after a stock purchase.
     *
     * @param profitLossInteractor The interactor for calculating profit or loss.
     */

    public void setProfitLossInteractor(ProfitLossInputBoundary profitLossInteractor) {
        this.profitLossInteractor = profitLossInteractor;
    }

    /**
     * Sets the interactor responsible for updating the UI with the list of stocks owned by the user.
     *
     * @param viewOwnedStockInteractor The interactor for viewing owned stocks.
     */

    public void setViewOwnedStockInteractor(ListStocksInputBoundary viewOwnedStockInteractor) {
        this.viewOwnedStockInteractor = viewOwnedStockInteractor;
    }

    /**
     * Executes the "buy stock" use case. It performs several checks, including verifying stock availability,
     * ensuring the user has sufficient funds, updating the user's portfolio with the purchased stock, and
     * saving the updated user information.
     *
     * @param inputData The input data for the buy stock use case, including the username, ticker symbol,
     *                  and quantity of shares to purchase.
     */

    @Override
    public void execute(BuyStockInputData inputData) {
        final String ticker = inputData.getTickerSymbol();
        final int quantity = inputData.getQuantity();
        final User user = database.getCurrentUser();
        final Portfolio userPortfolio = user.getPortfolio();

        // Checking the number of shares already owned by the user

        // Check if the stock exists
        if (!stockDatabase.isStockExist(ticker)) {
            buyStockPresenter.prepareFailView("The ticker does not exist.");
            return;
        }

        // Getting price of the stocks according to the number of the stocks and their current price
        final double stockCost = stockDatabase.getCost(ticker);
        final double totalCost = stockCost * quantity;
        final double balance = user.getBalance();

        // Check if the user has sufficient balance to buy the stock
        if (balance < totalCost) {
            buyStockPresenter.prepareFailView("Insufficient balance.");
        }
        else {

            for (int i = 0; i < quantity; i++) {
                user.getPortfolio().addStock(new Stock(ticker, stockCost));
            }

            final int numberOfShares = userPortfolio.getShareCount(ticker);

            // Save the updated user info
            database.saveUserInfo(user);

            // Update UI
            viewOwnedStockInteractor.execute(new ListStocksInputData(user.getName()));

            profitLossInteractor.execute();

            // Prepare success view with updated data
            System.out.println(numberOfShares);
            buyStockPresenter.prepareSuccessView(new BuyStockOutputData(user.getBalance(), ticker, numberOfShares));
        }
    }



}
