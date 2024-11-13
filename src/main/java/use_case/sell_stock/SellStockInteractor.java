package use_case.sell_stock;

import entity.Portfolio;
import entity.User;

public class SellStockInteractor implements SellStockInputBoundary{
    // Needs access to the database
    // Needs to send results to the view
    SellStockUserDataAccessInterface database;
    SellStockOutputBoundary sellStockPresenter;

    public SellStockInteractor(SellStockOutputBoundary sellStockPresenter, SellStockUserDataAccessInterface database) {
        this.sellStockPresenter = sellStockPresenter;
        this.database = database;
    }

    @Override
    public void execute(SellStockInputData inputData) {
        String ticker = inputData.getTicker();
        int quantity = inputData.getQuantity();
        // Get the current user
        User user = database.getCurrentUser();
        Portfolio userPortfolio = user.getPortfolio();

        // Check the number of shares owned associated with the ticker
        int numSharesOwned = userPortfolio.getShareCount();

        // If we own more than the number of shares that the user wishes to sell,
        // this can be executed
        if (numSharesOwned >= quantity){
            for(int i = 0; i < quantity; i++){
                userPortfolio.removeStock(ticker);
            }

            database.save(user);

        }


    }
}
