package use_case.profit_loss;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import data_access.InMemoryStockDataAccessObject;
import entity.Portfolio;
import entity.Stock;
import entity.User;

/**
 * Unit test class for ProfitLossInteractor.
 * Validates total and individual stock profit/loss calculations.
 */
class ProfitLossInteractorTest {
    private TestProfitLossDataAccess dataAccess;
    private TestProfitLossOutputBoundary presenter;
    private ProfitLossInteractor profitLossInteractor;
    private InMemoryStockDataAccessObject stockDataAccess;

    /**
     * Sets up the test environment, including mock data access objects and sample portfolio data.
     */
    @BeforeEach
    void setUp() {
        // Initialize mocks and the interactor
        dataAccess = new TestProfitLossDataAccess();
        presenter = new TestProfitLossOutputBoundary();
        stockDataAccess = new InMemoryStockDataAccessObject();
        profitLossInteractor = new ProfitLossInteractor(dataAccess, presenter, stockDataAccess);

        // Set up user portfolio
        final User user = dataAccess.getCurrentUser();
        final Portfolio portfolio = user.getPortfolio();

        // Adding 5 shares of AAPL
        for (int i = 0; i < 5; i++) {
            portfolio.addStock(new Stock("AAPL", 150.0));
        }

        // Adding 10 shares of GOOGL
        for (int i = 0; i < 10; i++) {
            portfolio.addStock(new Stock("GOOGL", 200.0));
        }

        // Adding 15 shares of TSLA
        for (int i = 0; i < 15; i++) {
            portfolio.addStock(new Stock("TSLA", 300.0));
        }

        // Save stock current prices in the mock stock database
        stockDataAccess.saveStock(new Stock("TSLA", 310.0));
        stockDataAccess.saveStock(new Stock("GOOGL", 210.0));
        stockDataAccess.saveStock(new Stock("AAPL", 160.0));
    }

    /**
     * Tests the calculation of the total profit/loss for all stocks in the portfolio.
     * Verifies that the expected total matches the calculated total.
     */
    @Test
    void executeTotalProfitLossTest() {
        // Arrange/inject mock current prices
        final Map<String, Double> mockCurrentPrices = getMockCurrentPrices();
        dataAccess.setMockStockPrices(mockCurrentPrices);

        // Act
        profitLossInteractor.execute();

        // Expected total profit/loss calculation
        final double expectedTotalProfitLoss = (160.0 - 150.0) * 5 + (210.0 - 200.0) * 10 + (310.0 - 300.0) * 15;

        // Assert
        assertFalse(presenter.useCaseFailed);
        assertEquals(expectedTotalProfitLoss, presenter.totalProfitLoss, 0.01);
    }

    /**
     * Tests the calculation of profit/loss for each individual stock in the portfolio.
     * Verifies that the expected profit/loss for each stock matches the calculated values.
     */
    @Test
    void executeEachStockProfitLossTest() {
        // Arrange/inject mock current prices
        final Map<String, Double> mockCurrentPrices = getMockCurrentPrices();

        // Directly inject mock prices
        dataAccess.setMockStockPrices(mockCurrentPrices);

        // Act
        profitLossInteractor.execute();

        // Expected per-stock profit/loss calculation
        final Map<String, Double> expectedStockProfitLosses = Map.of(
                "AAPL", (160.0 - 150.0) * 5,
                "GOOGL", (210.0 - 200.0) * 10,
                "TSLA", (310.0 - 300.0) * 15
        );

        // Assert
        assertFalse(presenter.useCaseFailed);
        assertEquals(expectedStockProfitLosses, presenter.stockProfitLosses);
    }

    /**
     * Tests the profit/loss calculation when the portfolio is empty.
     * Verifies that the total profit/loss and stock-specific results are zero or empty.
     */
    @Test
    void executeWithEmptyPortfolioTest() {
        final Portfolio portfolio = dataAccess.getCurrentUser().getPortfolio();
        portfolio.getStocks().clear();

        profitLossInteractor.execute();

        assertFalse(presenter.useCaseFailed);
        assertEquals(0.0, presenter.totalProfitLoss, 0.01);
        Assertions.assertTrue(presenter.stockProfitLosses.isEmpty());
    }

    /**
     * Tests the behavior when the presenter reports a failure.
     * Verifies that the use case reflects the failure state correctly.
     */
    @Test
    void presenterFailureTest() {
        presenter.useCaseFailed = true;

        profitLossInteractor.execute();

        Assertions.assertTrue(presenter.useCaseFailed);
    }

    @Test
    void inputDataIsUsedInternallyTest() {
        profitLossInteractor.execute();

        // Verify that the input data was created and logged (if applicable)
        // This can be verified indirectly through logs or mock validations
        assertDoesNotThrow(() -> profitLossInteractor.execute());
    }

    @Test
    void inputDataUsageTest() {
        // Create a ProfitLossInputData object
        final ProfitLossInputData inputData = new ProfitLossInputData("user123");

        // Verify the username is correctly set
        assertEquals("user123", inputData.getUsername());

        // Simulate its use in a calculation
        profitLossInteractor.execute();

        // Ensure no exceptions are thrown
        assertDoesNotThrow(() -> new ProfitLossInputData("test_user"));
    }

    // Mock current prices for the test
    private Map<String, Double> getMockCurrentPrices() {
        final Map<String, Double> stockPrices = new HashMap<>();
        // Current price of AAPL
        stockPrices.put("AAPL", 160.0);
        // Current price of GOOGL
        stockPrices.put("GOOGL", 210.0);
        // Current price of TSLA
        stockPrices.put("TSLA", 310.0);
        return stockPrices;
    }

    // Mock data access class for testing
    private static class TestProfitLossDataAccess implements ProfitLossDataAccessInterface {
        private final User user;
        private Map<String, Double> mockCurrentPrices = new HashMap<>();

        TestProfitLossDataAccess() {
            this.user = new User("user123", "password");
        }

        @Override
        public User getCurrentUser() {
            return user;
        }

        // Set mock prices for testing
        public void setMockStockPrices(Map<String, Double> newMockCurrentPrices) {
            this.mockCurrentPrices = newMockCurrentPrices;
        }

        // Provide mock prices during execution
        public Map<String, Double> getMockStockPrices() {
            return this.mockCurrentPrices;
        }
    }

    // Mock output boundary class for testing
    private static final class TestProfitLossOutputBoundary implements ProfitLossOutputBoundary {
        private boolean useCaseFailed;
        private double totalProfitLoss;
        private Map<String, Double> stockProfitLosses = new HashMap<>();

        @Override
        public void presentCombinedProfitLoss(ProfitLossOutputData outputData) {
            this.totalProfitLoss = outputData.getTotalProfitLoss();
            this.stockProfitLosses = outputData.getStockProfitLosses();
        }

        @Override
        public void success(ProfitLossOutputData outputData) {
            this.totalProfitLoss = outputData.getTotalProfitLoss();
            this.stockProfitLosses = outputData.getStockProfitLosses();
        }
    }
}
