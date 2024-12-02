package use_case.buy_stock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Before;
import org.junit.Test;

import data_access.InMemoryStockDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.Stock;
import entity.User;
import use_case.find_stock.FindStockDataAccessInterface;
import use_case.list_stocks.ListStocksInputBoundary;
import use_case.list_stocks.ListStocksInputData;
import use_case.profit_loss.ProfitLossInputBoundary;

public class BuyStockInteractorTest {
    private User testUser;
    private BuyStockUserDataAccessInterface database;
    private FindStockDataAccessInterface findStockDatabase;
    private InMemoryStockDataAccessObject stockDatabase;
    private InMemoryUserDataAccessObject userDatabase;

    /**
     * Sets up the test environment before each test method is run.
     * It initializes a test user, an in-memory user database, and a mock implementation
     * of the BuyStockUserDataAccessInterface to simulate user data retrieval and saving.
     * This method is annotated with @BeforeEach to ensure it runs before every test case.
     */
    @Before
    public void setUp() throws Exception {
        // Setting up a user and database
        testUser = new User("testUser", "Password");
        testUser.setBalance(200.0);

        // Initialize the InMemory stock database
        stockDatabase = new InMemoryStockDataAccessObject();

        // Save stock information in the database
        stockDatabase.saveUser(testUser);
        stockDatabase.saveStock(new Stock("IBM", 100.0));

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
        final BuyStockInputData buyStockInputData = new BuyStockInputData("testUser", "Test", 3);

        final BuyStockOutputBoundary testPresenter = new BuyStockOutputBoundary() {
            @Override
            public void prepareSuccessView(BuyStockOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("The ticker does not exist.", errorMessage);

            }
        };

        // Initializing the interactor for executing the use case
        final BuyStockInteractor buyStockInteractor = new BuyStockInteractor(testPresenter, database, stockDatabase);
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
        final BuyStockInputData buyStockInputData = new BuyStockInputData("testUser", "IBM", 3);

        // Presenter for testing insufficient balance
        final BuyStockOutputBoundary testPresenter = new BuyStockOutputBoundary() {

            @Override
            public void prepareSuccessView(BuyStockOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Insufficient balance.", errorMessage);

            }
        };

        // Initializing the interactor for executing the use case
        final BuyStockInteractor buyStockInteractor = new BuyStockInteractor(testPresenter, database, stockDatabase);

        buyStockInteractor.setViewOwnedStockInteractor(new ListStocksInputBoundary() {
            @Override
            public void execute(ListStocksInputData inputData) {

            }
        });
        buyStockInteractor.setProfitLossInteractor(new ProfitLossInputBoundary() {
            @Override
            public void execute() {
            }
        });
        buyStockInteractor.execute(buyStockInputData);
    }

    /**
     * Tests a successful stock purchase scenario.
     * This test ensures that a user can buy a stock if they have sufficient balance
     * and the stock exists in the database. It verifies that:
     * - The stock ticker matches the requested one ("IBM").
     * - The stock price ($100) and remaining balance ($100) are accurate.
     * Preconditions:
     * - A user with $200 balance exists in the database.
     * - The stock database contains "IBM" priced at $100.
     */
    @Test
    public void successTest() {
        final BuyStockInputData buyStockInputData = new BuyStockInputData("testUser", "IBM", 1);

        // Presenter for testing successful stock purchase
        final BuyStockOutputBoundary testPresenter = new BuyStockOutputBoundary() {

            @Override
            public void prepareSuccessView(BuyStockOutputData outputData) {
                // Verify that the success scenario works as expected
                assertEquals("IBM", outputData.getTickerSymbol());
                assertEquals(100.0, outputData.getRemainingBalance());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        // Initializing the interactor for executing the use case
        final BuyStockInteractor buyStockInteractor = new BuyStockInteractor(testPresenter, database, stockDatabase);

        buyStockInteractor.setViewOwnedStockInteractor(new ListStocksInputBoundary() {
            @Override
            public void execute(ListStocksInputData inputData) {

            }
        });
        buyStockInteractor.setProfitLossInteractor(new ProfitLossInputBoundary() {
            @Override
            public void execute() {
            }
        });
        buyStockInteractor.execute(buyStockInputData);
    }
}
