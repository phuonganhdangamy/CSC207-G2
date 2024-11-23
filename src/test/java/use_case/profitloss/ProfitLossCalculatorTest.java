package use_case.profitloss;

import entity.Portfolio;
import entity.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfitLossCalculatorTest {

    private Portfolio portfolio;

    @BeforeEach
    void setUp() {
        // Sample stocks and buying price
        Stock stock1 = new Stock("AAPL", 5, 150.0);
        Stock stock2 = new Stock("GOOGL", 10, 200.0);
        Stock stock3 = new Stock("TSLA", 15, 300.0);

        // Create a portfolio and add stocks
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);
        stocks.add(stock3);
        portfolio = new Portfolio(stocks);
    }

    @Test
    void testCalculateTotalProfitLoss() {
        // Sample current prices
        portfolio.getStocks().get(0).setCurrentPrice(160.0);
        portfolio.getStocks().get(1).setCurrentPrice(210.0);
        portfolio.getStocks().get(2).setCurrentPrice(310.0);

        // Expected profit/loss
        double expectedProfitLoss =
                (160.0 - 150.0) * 5 +
                        (210.0 - 200.0) * 10 +
                        (310.0 - 300.0) * 15;

        // Calculate using the method
        double actualProfitLoss = portfolio.calculateTotalProfitLoss();

        // Assert the values match
        assertEquals(expectedProfitLoss, actualProfitLoss, 0.01);
    }

    @Test
    void testCalculateStockProfitLoss() {
        // Mock current prices
        portfolio.getStocks().get(0).setCurrentPrice(160.0);

        // Expected profit/loss for AAPL
        double expectedProfitLoss = (160.0 - 150.0) * 5;

        // Calculate using the method
        double actualProfitLoss = portfolio.calculateStockProfitLoss("AAPL");

        // Assert the values match
        assertEquals(expectedProfitLoss, actualProfitLoss, 0.01);
    }
}