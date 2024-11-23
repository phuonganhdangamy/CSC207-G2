package use_case.buy_stock;

import data_access.DBStockDataAccessObject;
import data_access.InMemoryStockDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.Stock;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuyStockInteractorTest {


    private InMemoryStockDataAccessObject stockRepository;
    private InMemoryUserDataAccessObject userRepository;
    private BuyStockOutputBoundary successPresenter;

    @BeforeEach
    void setUp() {
        // Initialize repositories
        stockRepository = new InMemoryStockDataAccessObject();
        userRepository = new InMemoryUserDataAccessObject();

        // Initialize success presenter
        successPresenter = new BuyStockOutputBoundary() {
            @Override
            public void prepareSuccessView(BuyStockOutputData outputData) {
                assertNotNull(outputData);
                assertEquals(950.0, outputData.getRemainingBalance(), 0.01); // After buying 5 shares of AAPL
                assertEquals("AAPL", outputData.getTickerSymbol());
                assertEquals(5, outputData.getNumberOfShares());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Unexpected failure: " + errorMessage);
            }
        };

        // Initialize failure presenter
        failPresenter = new BuyStockOutputBoundary() {
            @Override
            public void prepareSuccessView(BuyStockOutputData outputData) {
                fail("Unexpected success: " + outputData.getTickerSymbol());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertNotNull(errorMessage);
            }
        };
    }

    @Test
    void successTest() {
        // Prepare input data
        BuyStockInputData inputData = new BuyStockInputData("testUser", "AAPL", 5);
        Stock stock = new Stock("AAPL", 100.0);  // Stock price of 100
        UserFactory userFactory = new UserFactory();
        User user = userFactory.create("testUser", "password");
        user.setBalance(1000.0);  // User has enough balance
        userRepository.save(user);
        stockRepository.saveStock(stock);

        // Interactor for executing the use case
        BuyStockInputBoundary interactor = new BuyStockInteractor(successPresenter, (BuyStockUserDataAccessInterface)userRepository, stockRepository);
        interactor.execute(inputData);
    }

    @Test
    void insufficientBalanceTest() {
        // Prepare input data
        BuyStockInputData inputData = new BuyStockInputData("testUser", "AAPL", 15);
        Stock stock = new Stock("AAPL", 100.0);  // Stock price of 100
        UserFactory userFactory = new UserFactory();
        User user = userFactory.create("testUser", "password");
        user.setBalance(1000.0);  // User has insufficient balance for 15 shares
        userRepository.save(user);
        stockRepository.saveStock(stock);

        // Failure presenter for testing insufficient balance
        BuyStockOutputBoundary failPresenter = new BuyStockOutputBoundary() {
            @Override
            public void prepareSuccessView(BuyStockOutputData outputData) {
                fail("Unexpected success: The user should not have enough balance.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Insufficient balance.", errorMessage);
            }
        };

        // Interactor for executing the use case
        BuyStockInputBoundary interactor = new BuyStockInteractor(failPresenter, (BuyStockUserDataAccessInterface)userRepository, stockRepository);
        interactor.execute(inputData);
    }

    @Test
    void stockDoesNotExistTest() {
        // Prepare input data
        BuyStockInputData inputData = new BuyStockInputData("testUser", "XYZ", 5);  // XYZ is an invalid stock ticker
        Stock stock = new Stock("AAPL", 100.0);  // Only AAPL exists
        UserFactory userFactory = new UserFactory();
        User user = userFactory.create("testUser", "password");
        user.setBalance(1000.0);  // User has enough balance
        userRepository.save(user);
        stockRepository.saveStock(stock);

        // Failure presenter for testing non-existing stock ticker
        BuyStockOutputBoundary failPresenter = new BuyStockOutputBoundary() {
            @Override
            public void prepareSuccessView(BuyStockOutputData outputData) {
                fail("Unexpected success: Stock does not exist.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("This ticker does not exist.", errorMessage);
            }
        };

        // Interactor for executing the use case
        BuyStockInputBoundary interactor = new BuyStockInteractor(failPresenter, (BuyStockUserDataAccessInterface)userRepository, stockRepository);
        interactor.execute(inputData);
    }

    @Test
    void userDoesNotExistTest() {
        // Prepare input data
        BuyStockInputData inputData = new BuyStockInputData("nonExistentUser", "AAPL", 5);  // Non-existent user
        Stock stock = new Stock("AAPL", 100.0);
        stockRepository.saveStock(stock);

        // Failure presenter for testing non-existing user
        BuyStockOutputBoundary failPresenter = new BuyStockOutputBoundary() {
            @Override
            public void prepareSuccessView(BuyStockOutputData outputData) {
                fail("Unexpected success: User does not exist.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("nonExistentUser: Account does not exist.", errorMessage);
            }
        };

        // Interactor for executing the use case
        BuyStockInputBoundary interactor = new BuyStockInteractor(failPresenter, (BuyStockUserDataAccessInterface)userRepository, stockRepository);
        interactor.execute(inputData);
    }
}
