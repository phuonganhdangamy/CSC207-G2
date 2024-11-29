package use_case.sell_stock;

import data_access.InMemoryStockDataAccessObject;
import entity.Portfolio;
import entity.Stock;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import use_case.find_stock.FindStockDataAccessInterface;
import use_case.list_stocks.ListStocksInputBoundary;
import use_case.list_stocks.ListStocksInputData;
import use_case.profit_loss.ProfitLossInputBoundary;
import use_case.profit_loss.ProfitLossInputData;
import use_case.profit_loss.ProfitLossInteractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class SellStockInteractorTest {
    User testUser;
    SellStockUserDataAccessInterface database;
    FindStockDataAccessInterface findStockDatabase;
    InMemoryStockDataAccessObject stockDatabase;


    @Before
    public void setUp() throws Exception {
        // Setting up a user and database
        List<String> stockTickers = new ArrayList<>(List.of("MSFT","AAPL", "GOOG", "WMT", "MSFT", "AAPL", "GOOG",
                "WMT"));
        testUser = new User("Name", "Password");
        Portfolio userPortfolio = testUser.getPortfolio();

        // Initialize the InMemory stock database
        stockDatabase = new InMemoryStockDataAccessObject();

        // Save stock information in the database
        stockDatabase.saveStock(new Stock("MSFT", 10.0));
        stockDatabase.saveStock(new Stock("AAPL", 12.0));
        stockDatabase.saveStock(new Stock("GOOG", 15.0));
        stockDatabase.saveStock(new Stock("WMT", 8.0));

        for (String ticker : stockTickers) {
            Stock stock = new Stock(ticker, 50);
            userPortfolio.addStock(stock);
        }

        stockDatabase.saveUser(testUser);

        database = new SellStockUserDataAccessInterface() {
            private User user;
            @Override
            public void saveUserInfo(User user) {
                this.user = user;

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

        database.saveUserInfo(testUser);
    }

    @Test
    public void tickerDoesNotExist() {
        SellStockInputData sellStockInputData = new SellStockInputData(2, "NotReal");

        SellStockOutputBoundary testPresenter = new SellStockOutputBoundary() {
            @Override
            public void prepareSuccessView(SellStockOutputData outputData) {fail("Use case success is unexpected.");}

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("This ticker does not exist.", errorMessage);

            }
        };
        SellStockInteractor sellStockInteractor = new SellStockInteractor(testPresenter, database, stockDatabase);
        sellStockInteractor.execute(sellStockInputData);
    }
    @Test
    public void notEnoughShares() {
        SellStockInputData sellStockInputData = new SellStockInputData(3, "MSFT");

        SellStockOutputBoundary testPresenter = new SellStockOutputBoundary() {
            @Override
            public void prepareSuccessView(SellStockOutputData outputData) {fail("Use case success is unexpected.");}

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Name, you don't have enough shares of this company to sell.", errorMessage);

            }
        };
        SellStockInteractor sellStockInteractor = new SellStockInteractor(testPresenter, database, stockDatabase);
        sellStockInteractor.execute(sellStockInputData);
    }

    @Test
    public void EnoughShares() {
        SellStockInputData sellStockInputData = new SellStockInputData(2, "MSFT");
        double oldBalance = testUser.getBalance();

        SellStockOutputBoundary testPresenter = new SellStockOutputBoundary() {
            @Override
            public void prepareSuccessView(SellStockOutputData outputData) {
                assertEquals(oldBalance + 20, outputData.getNewBalance());

            }

            @Override
            public void prepareFailView(String errorMessage) {fail("Use case failure is unexpected.");}
        };
        SellStockInteractor sellStockInteractor = new SellStockInteractor(testPresenter, database, stockDatabase);
        sellStockInteractor.setViewOwnedStockInteractor(new ListStocksInputBoundary() {
            @Override
            public void execute(ListStocksInputData inputData) {

            }
        });
        sellStockInteractor.setProfitLossInteractor(new ProfitLossInputBoundary() {
            @Override
            public void execute() {

            }

            @Override
            public void calculateStockProfitLoss(ProfitLossInputData inputData, String tickerSymbol, double currentPrice) {

            }
        });
        sellStockInteractor.execute(sellStockInputData);
    }
}
