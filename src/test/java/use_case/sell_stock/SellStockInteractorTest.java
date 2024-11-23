package use_case.sell_stock;

import entity.Portfolio;
import entity.Stock;
import entity.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class SellStockInteractorTest {


    @Test
    public void tickerDoesNotExist() {
        SellStockInputData sellStockInputData = new SellStockInputData(2, "NotReal");

        // Setting up a user and database
        String[] stockTickers = {"MSFT","AAPL", "GOOG", "WMT", "MSFT", "AAPL", "GOOG", "WMT"};
        User testUser = new User("Name", "Password");
        Portfolio userPortfolio = testUser.getPortfolio();

        for (String ticker : stockTickers) {
            Stock stock = new Stock(ticker, 50);
            userPortfolio.addStock(stock);
        }

        SellStockUserDataAccessInterface database = new SellStockUserDataAccessInterface() {
            private User user = testUser;
            @Override
            public void saveUserInfo(User user) {

            }

            @Override
            public String getCurrentUsername() {
                return user.getName();
            }

            @Override
            public User getCurrentUser() {
                return user;
            }
        };

        SellStockOutputBoundary testPresenter = new SellStockOutputBoundary() {
            @Override
            public void prepareSuccessView(SellStockOutputData outputData) {fail("Use case failure is unexpected.");}

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("This ticker does not exist.", errorMessage);

            }
        };
        SellStockInteractor sellStockInteractor = new SellStockInteractor(testPresenter, database);
        sellStockInteractor.execute(sellStockInputData);
    }
}
