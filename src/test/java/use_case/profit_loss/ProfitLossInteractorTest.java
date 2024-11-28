package use_case.profit_loss;

import entity.Portfolio;
import entity.ProfitLossCalculator;
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
    void executeSingleStockProfitLossTest() {
        // Arrange: Mock current price for a specific stock
        String tickerSymbol = "AAPL";
        double currentPrice = 160.0;

        // Act: Call the interactor method for the specific stock
        profitLossInteractor.execute(tickerSymbol, currentPrice);

        // Expected profit/loss for AAPL
        double expectedProfitLoss = (currentPrice - 150.0) * 5; // (current price - cost price) * quantity

        // Assert: Verify the results
        assertFalse(presenter.useCaseFailed);
        assertEquals(expectedProfitLoss, presenter.stockProfitLoss, 0.01);
        assertEquals("AAPL", presenter.stockTicker);
    }

    @Test
    void calculateTotalProfitLossTest() {
        // Arrange: Create a portfolio with multiple stocks
        User user = new User("user123", "password");
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

        // Mock current prices
        Map<String, Double> stockPrices = Map.of(
                "AAPL", 160.0,
                "GOOGL", 210.0,
                "TSLA", 310.0
        );

        // Act: Calculate total profit/loss
        ProfitLossCalculator calculator = new ProfitLossCalculator(portfolio);
        double totalProfitLoss = calculator.calculateTotalProfitLoss(stockPrices);

        // Expected total profit/loss
        double expectedProfitLoss =
                (160.0 - 150.0) * 5 +  // AAPL: (current price - cost price) * quantity
                        (210.0 - 200.0) * 10 + // GOOGL: (current price - cost price) * quantity
                        (310.0 - 300.0) * 15;  // TSLA: (current price - cost price) * quantity

        // Assert: Verify the result
        assertEquals(expectedProfitLoss, totalProfitLoss, 0.01);
    }

    // Mock data access class for testing
    private static class TestProfitLossDataAccess implements ProfitLossDataAccessInterface {
        @Override
        public User getCurrentUser() {
            // Mock user and portfolio creation
            User user = new User("user123", "password");
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

            return user;
        }
    }

    // Mock output boundary class for testing
    private static class TestProfitLossOutputBoundary implements ProfitLossOutputBoundary {
        boolean useCaseFailed = false;
        double totalProfitLoss = 0.0;
        double stockProfitLoss = 0.0;
        String stockTicker = null;

        @Override
        public void presentCombinedProfitLoss(ProfitLossOutputData outputData) {
            this.totalProfitLoss = outputData.getTotalProfitLoss();
            this.stockProfitLoss = outputData.getStockProfitLoss();
            this.stockTicker = outputData.getTickerSymbol();
        }
    }
}
