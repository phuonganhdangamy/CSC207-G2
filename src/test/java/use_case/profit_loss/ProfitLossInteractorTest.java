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
    }

    @Test
    void calculateTotalProfitLossSuccessTest() {
        // Arrange: Input data for user
        ProfitLossInputData inputData = new ProfitLossInputData("user123");

        // Fetch mock current stock prices
        Map<String, Double> stockPrices = getMockCurrentPrices();

        // Act: Call the interactor execute method for total profit/loss
        profitLossInteractor.execute(inputData, stockPrices, null);

        // Expected total profit/loss
        double expectedProfitLoss =
                (160.0 - 150.0) * 5 +  // AAPL: (current price - cost price) * quantity
                        (210.0 - 200.0) * 10 + // GOOGL: (current price - cost price) * quantity
                        (310.0 - 300.0) * 15;  // TSLA: (current price - cost price) * quantity

        // Assert: Verify the results
        assertFalse(presenter.useCaseFailed);
        assertEquals(expectedProfitLoss, presenter.totalProfitLoss, 0.01);
    }

    @Test
    void calculateStockProfitLossSuccessTest() {
        // Arrange: Input data for user
        ProfitLossInputData inputData = new ProfitLossInputData("user123");

        // Mock current price for the specific stock
        double currentPrice = 160.0;

        // Act: Call the interactor method for specific stock
        profitLossInteractor.execute(inputData, getMockCurrentPrices(), "AAPL");

        // Expected profit/loss for AAPL
        double expectedProfitLoss = (160.0 - 150.0) * 5; // (current price - cost price) * quantity

        // Assert: Verify the results
        assertFalse(presenter.useCaseFailed);
        assertEquals(expectedProfitLoss, presenter.stockProfitLoss, 0.01);
        assertEquals("AAPL", presenter.stockTicker);
    }

    // Helper method to fetch mock current stock prices
    private Map<String, Double> getMockCurrentPrices() {
        Map<String, Double> stockPrices = new HashMap<>();
        stockPrices.put("AAPL", 160.0); // Current price of AAPL
        stockPrices.put("GOOGL", 210.0); // Current price of GOOGL
        stockPrices.put("TSLA", 310.0); // Current price of TSLA
        return stockPrices;
    }

    // Mock data access class for testing
    private static class TestProfitLossDataAccess implements ProfitLossDataAccessInterface {
        @Override
        public Portfolio getCurrentUser(String username) {
            // Mock user and portfolio creation
            User user = new User(username, "password");dd
            user.setBalance(10000.0); // Set an initial balance

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

            return portfolio;
        }
    }

    // Mock output boundary class for testing
    private static class TestProfitLossOutputBoundary implements ProfitLossOutputBoundary {
        boolean useCaseFailed = false;
        double totalProfitLoss = 0.0;
        double stockProfitLoss = 0.0;
        String stockTicker = null;

        @Override
        public void presentProfitLoss(ProfitLossOutputData outputData) {
            this.totalProfitLoss = outputData.getTotalProfitLoss();
            this.stockProfitLoss = outputData.getStockProfitLoss();
            this.stockTicker = outputData.getTickerSymbol();
        }
    }
}
