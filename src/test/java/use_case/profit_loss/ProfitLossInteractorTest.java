package use_case.profit_loss;

import entity.Portfolio;
import entity.Stock;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProfitLossInteractorTest {
    private TestProfitLossDataAccess dataAccess;
    private TestProfitLossOutputBoundary presenter;
    private ProfitLossInteractor profitLossInteractor;

    @BeforeEach
    void setUp() {
        dataAccess = new TestProfitLossDataAccess();
        presenter = new TestProfitLossOutputBoundary();
        profitLossInteractor = new ProfitLossInteractor(dataAccess, presenter);

        // Set up user portfolio
        User user = dataAccess.getCurrentUser();
        Portfolio portfolio = user.getPortfolio();

        portfolio.addStock(new Stock("AAPL", 150.0));
        portfolio.addStock(new Stock("AAPL", 150.0));
        portfolio.addStock(new Stock("AAPL", 150.0));
        portfolio.addStock(new Stock("AAPL", 150.0));
        portfolio.addStock(new Stock("AAPL", 150.0)); // 5 shares

        portfolio.addStock(new Stock("GOOGL", 200.0));
        portfolio.addStock(new Stock("GOOGL", 200.0));
        portfolio.addStock(new Stock("GOOGL", 200.0));
        portfolio.addStock(new Stock("GOOGL", 200.0));
        portfolio.addStock(new Stock("GOOGL", 200.0));
        portfolio.addStock(new Stock("GOOGL", 200.0));
        portfolio.addStock(new Stock("GOOGL", 200.0));
        portfolio.addStock(new Stock("GOOGL", 200.0));
        portfolio.addStock(new Stock("GOOGL", 200.0));
        portfolio.addStock(new Stock("GOOGL", 200.0)); // 10 shares

        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0));
        portfolio.addStock(new Stock("TSLA", 300.0)); // 15 shares
    }

    @Test
    void executeTotalProfitLossTest() {
        // Arrange: Mock current stock prices
        Map<String, Double> mockCurrentPrices = getMockCurrentPrices();
        dataAccess.setMockCurrentPrices(mockCurrentPrices); // Inject mock prices into the data access layer

        // Act
        profitLossInteractor.execute();

        // Expected total profit/loss calculation
        double expectedTotalProfitLoss =
                (160.0 - 150.0) * 5 +  // AAPL: (current price - purchase price) * quantity
                        (210.0 - 200.0) * 10 + // GOOGL
                        (310.0 - 300.0) * 15;  // TSLA

        // Assert
        assertFalse(presenter.useCaseFailed);
        assertEquals(expectedTotalProfitLoss, presenter.totalProfitLoss, 0.01);
    }

    @Test
    void executeEachStockProfitLossTest() {
        // Arrange: Mock current stock prices
        Map<String, Double> mockCurrentPrices = getMockCurrentPrices();
        dataAccess.setMockCurrentPrices(mockCurrentPrices); // Inject mock prices into the data access layer

        // Fetch the updated portfolio to ensure current prices are reflected
        User user = dataAccess.getCurrentUser();
        Portfolio portfolio = user.getPortfolio();

        // Act
        profitLossInteractor.execute();

        // Expected per-stock profit/loss calculation
        Map<String, Double> expectedStockProfitLosses = Map.of(
                "AAPL", (160.0 - 150.0) * 5,
                "GOOGL", (210.0 - 200.0) * 10,
                "TSLA", (310.0 - 300.0) * 15
        );

        // Assert
        assertFalse(presenter.useCaseFailed);
        assertEquals(expectedStockProfitLosses, presenter.stockProfitLosses);
    }


    // Helper method to simulate fetching mock current stock prices
    private Map<String, Double> getMockCurrentPrices() {
        Map<String, Double> stockPrices = new HashMap<>();
        stockPrices.put("AAPL", 160.0); // Current price of AAPL
        stockPrices.put("GOOGL", 210.0); // Current price of GOOGL
        stockPrices.put("TSLA", 310.0); // Current price of TSLA
        return stockPrices;
    }

    // Mock data access class for testing
    private static class TestProfitLossDataAccess implements ProfitLossDataAccessInterface {
        private final User user;
        private Map<String, Double> mockCurrentPrices;

        public TestProfitLossDataAccess() {
            this.user = new User("user123", "password");
            this.mockCurrentPrices = new HashMap<>();
        }

        @Override
        public User getCurrentUser() {
            return user;
        }

        // Simulate fetching current stock prices
        public Map<String, Double> getCurrentPrices() {
            return mockCurrentPrices;
        }

        public void setMockCurrentPrices(Map<String, Double> mockCurrentPrices) {
            this.mockCurrentPrices = mockCurrentPrices;
        }
    }

    // Mock output boundary class for testing
    private static class TestProfitLossOutputBoundary implements ProfitLossOutputBoundary {
        boolean useCaseFailed = false;
        double totalProfitLoss = 0.0;
        Map<String, Double> stockProfitLosses = new HashMap<>();

        @Override
        public void presentCombinedProfitLoss(ProfitLossOutputData outputData) {
            this.totalProfitLoss = outputData.getTotalProfitLoss();
            this.stockProfitLosses = outputData.getStockProfitLosses();
        }
    }
}