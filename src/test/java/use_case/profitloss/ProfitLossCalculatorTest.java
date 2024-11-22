package use_case.profit_loss;

import entity.Portfolio;
import entity.ProfitLossCalculator;
import entity.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfitLossCalculatorTest {

    private Portfolio portfolio;
    private ProfitLossCalculator profitLossCalculator;

    @BeforeEach
    void setUp() {
        // Create sample stocks with their buying prices and number of shares
        Stock stock1 = new Stock("AAPL", 5, 150.0);   // Own 5 shares bought at $150 each
        Stock stock2 = new Stock("GOOGL", 10, 200.0); // Own 10 shares bought at $200 each
        Stock stock3 = new Stock("TSLA", 15, 300.0);  // Own 15 shares bought at $300 each

        // Create a portfolio and add the stocks
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);
        stocks.add(stock3);
        portfolio = new Portfolio(stocks);

        // Use the mock data access object for testing
        MockDBStockDataAccessObject mockDataAccess = new MockDBStockDataAccessObject();
        profitLossCalculator = new ProfitLossCalculator(portfolio, mockDataAccess);
    }

    @Test
    void testCalculateTotalProfitLoss() {
        // Expected total profit/loss calculation
        double expectedProfitLoss =
                (160.0 - 150.0) * 5 +  // (current price - purchase price) * number of shares for AAPL
                        (210.0 - 200.0) * 10 + // (current price - purchase price) * number of shares for GOOGL
                        (310.0 - 300.0) * 15;  // (current price - purchase price) * number of shares for TSLA

        // Calculate using the ProfitLossCalculator
        double actualProfitLoss = profitLossCalculator.calculateTotalProfitLoss();

        // Assert the values match
        assertEquals(expectedProfitLoss, actualProfitLoss, 0.01);
    }

    @Test
    void testCalculateStockProfitLoss() {
        // Expected profit/loss for AAPL stock
        double expectedProfitLoss = (160.0 - 150.0) * 5; // (current price - purchase price) * number of shares

        // Calculate profit/loss for AAPL using the ProfitLossCalculator
        double actualProfitLoss = profitLossCalculator.calculateStockProfitLoss("AAPL");

        // Assert the values match
        assertEquals(expectedProfitLoss, actualProfitLoss, 0.01);
    }
}
