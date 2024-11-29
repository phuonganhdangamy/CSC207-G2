package use_case.buy_stock;

import data_access.InMemoryStockDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.Portfolio;
import entity.Stock;
import entity.User;
import entity.UserFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.find_stock.FindStockDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class BuyStockInteractorTest {
    User testUser;
    BuyStockUserDataAccessInterface database;
    // FindStockDataAccessInterface findStockDatabase;
    InMemoryStockDataAccessObject stockDatabase;
    private InMemoryUserDataAccessObject userDatabase;

    @BeforeEach
    public void setUp() throws Exception {
        // Setting up a user and database
        testUser = new User("Name", "Password");

        database = new BuyStockUserDataAccessInterface() {
            private User user;

            @Override
            public User get(String username) {
                return null;
            }

            @Override
            public void saveUserInfo(User user) {
                this.user = user;
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
        BuyStockInputData buyStockInputData = new BuyStockInputData("testUser", "Test", 3);

        BuyStockOutputBoundary testPresenter = new BuyStockOutputBoundary() {
            @Override
            public void prepareSuccessView(BuyStockOutputData outputData) {fail("Use case success is unexpected.");}

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("This ticker does not exist.", errorMessage);

            }
        };

        // Initializing the interactor for executing the use case
        BuyStockInteractor buyStockInteractor = new BuyStockInteractor(testPresenter, database, stockDatabase);
        buyStockInteractor.execute(buyStockInputData);
    }

    @Test
    public void notEnoughBalance() {

        BuyStockInputData buyStockInputData = new BuyStockInputData("testUser", "IBM", 3);

        Stock stock = new Stock("IBM", 100.0);  // Stock price of 100
        UserFactory userFactory = new UserFactory();
        User user = userFactory.create("testUser", "654321");
        user.setBalance(10000.0);  // User has insufficient balance for 15 shares
        userDatabase.save(user);
        stockDatabase.saveStock(stock);

        // Presenter for testing insufficient balance
        BuyStockOutputBoundary testPresenter = new BuyStockOutputBoundary() {

            @Override
            public void prepareSuccessView(BuyStockOutputData outputData) {fail("Use case success is unexpected.");}

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("The balance is not sufficient.", errorMessage);

            }
        };

        // Initializing the interactor for executing the use case
        BuyStockInteractor buyStockInteractor = new BuyStockInteractor(testPresenter, database, stockDatabase);
        buyStockInteractor.execute(buyStockInputData);
    }

    @Test
     public void notExistingUser() {
        // Preparing input data
        BuyStockInputData buyStockInputData = new BuyStockInputData("nonExistentUser", "IBM", 5);  // Non-existent user
        Stock stock = new Stock("IBM", 100.0);
        stockDatabase.saveStock(stock);

        // Failure presenter for testing non-existing user
        BuyStockOutputBoundary testPresenter = new BuyStockOutputBoundary() {

            @Override
            public void prepareSuccessView(BuyStockOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("This user does not exist.", errorMessage);

            }
        };

        //  Initializing the interactor for executing the use case
        BuyStockInteractor buyStockInteractor = new BuyStockInteractor(testPresenter, database, stockDatabase);
        buyStockInteractor.execute(buyStockInputData);
    }
}


