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

    /**
     * Sets up the test environment before each test method is run.
     * It initializes a test user, an in-memory user database, and a mock implementation
     * of the BuyStockUserDataAccessInterface to simulate user data retrieval and saving.

     * This method is annotated with @BeforeEach to ensure it runs before every test case.
     */

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

    /**
     * Tests the scenario where the user tries to buy stock for a ticker symbol that does not exist.
     * The test ensures that the appropriate error message "This ticker does not exist." is presented
     * when the stock ticker is invalid or unavailable in the database.

     * This test simulates a failed stock purchase where the ticker symbol is incorrect.

     * Assertions:
     * - Verifies that the error message returned is "This ticker does not exist."
     */

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

    /**
     * Tests the scenario where the user does not have enough balance to buy the requested stock.
     * In this test, a user with an initial balance is checked against a stock price for which
     * the user cannot afford the requested quantity. The test ensures that the correct error message
     * "The balance is not sufficient." is returned.

     * This test simulates a failed stock purchase due to insufficient funds in the user's account.

     * Assertions:
     * - Verifies that the error message returned is "The balance is not sufficient."
     */


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

    /**
     * Tests the scenario where a user tries to buy stock, but the provided username does not exist in the database.
     * The test ensures that the appropriate error message "This user does not exist." is presented
     * when the user attempts to purchase stock without being registered.

     * This test simulates a failed stock purchase due to a non-existent user.

     * Assertions:
     * - Verifies that the error message returned is "This user does not exist."
     */

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


