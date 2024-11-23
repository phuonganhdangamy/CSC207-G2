package use_case.sell_stock;

import data_access.DBStockDataAccessObject;
import entity.Portfolio;
import entity.Stock;
import entity.User;

import java.util.HashMap;
import java.util.Map;

public class SellStockInteractor implements SellStockInputBoundary{
    // Needs access to the database
    // Needs to send results to the view
    SellStockUserDataAccessInterface database;
    SellStockOutputBoundary sellStockPresenter;

    // Need access to the view stock use case interactor and profit loss interactor to update UI
    // ViewOwnedStockInputBoundary viewOwnedStockInteractor;
    // ProfitLossInputBoundary profitLossInteractor;

    public SellStockInteractor(SellStockOutputBoundary sellStockPresenter, SellStockUserDataAccessInterface database ) {
       // Add to parameters: ViewOwnedStockInputBoundary viewOwnedStockInteractor,  ProfitLossInputBoundary profitLossInteractor
        this.sellStockPresenter = sellStockPresenter;
        this.database = database;
        // this.viewOwnedStockInteractor = viewOwnedStockInteractor;
        // this.profitLossInteractor = profitLossInteractor
    }

    @Override
    public void execute(SellStockInputData inputData) {
        String ticker = inputData.getTicker();
        int quantity = inputData.getQuantity();
        // Get the current user
        User user = database.getCurrentUser();
        Portfolio userPortfolio = user.getPortfolio();

        // Check the number of shares owned associated with the ticker
        int numSharesOwned = userPortfolio.getShareCount(ticker);

        //New stock database to see if the company exists.
        DBStockDataAccessObject stockDataAccessObject = new DBStockDataAccessObject();


        if (!stockDataAccessObject.isStockExist(ticker)) {
            sellStockPresenter.prepareFailView( "This ticker does not exist.");

        }


        // If we own more than the number of shares that the user wishes to sell,
        // this can be executed
        else if (numSharesOwned >= quantity){
            // removes the stock and updates the balance
            for(int i = 0; i < quantity; i++){
                userPortfolio.removeStock(ticker);
            }

            // Save the user's information in the online database
            database.saveUserInfo(user);

            // define the output needed by the presenter
            double newBalance = user.getBalance();

            SellStockOutputData output = new SellStockOutputData(newBalance);
            sellStockPresenter.prepareSuccessView(output);

            // Update UI by calling the view owned stock use case and profit loss use case after
            // selling shares

            // viewOwnedStockInteractor.execute();
            // profitLossInteractor.execute();

        }else{
            sellStockPresenter.prepareFailView(user.getName()+ ", you don't have enough shares of this company to sell.");

        }


    }
}
