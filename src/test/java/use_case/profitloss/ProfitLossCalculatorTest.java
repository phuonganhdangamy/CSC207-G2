package use_case.profitloss;

import data_access.DBStockDataAccessObject;
import entity.Portfolio;
import entity.ProfitLossCalculator;
import entity.Stock;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfitLossCalculatorTest {

    private Portfolio portfolio;
    DBStockDataAccessObject dataAccessObject = new DBStockDataAccessObject();

    @BeforeEach
    void setUp() {
        // Sample stocks and buying price
        Stock stock1 = new Stock("AAPL", 150.0);
        Stock stock2 = new Stock("GOOGL", 200.0);
        Stock stock3 = new Stock("TSLA",300.0);

        // Mock user:
        User user = new User("UserOne", "654321");

        // Creating a sample portfolio from the sample stocks:
        portfolio = new Portfolio(user);

        // Adding the mock stocks:
        portfolio.addStock(stock1);
        portfolio.addStock(stock2);
        portfolio.addStock(stock3);

    }

    @Test
    void testCalculateTotalProfitLoss() {
        // Setting up a sample ProfitLossCalculator
        ProfitLossCalculator profitLossCalculator = new ProfitLossCalculator(portfolio);

        // Sample current prices from API
        double currentPrice1 = dataAccessObject.getCost(portfolio.getStocks().get(0).getTickerSymbol());
        double currentPrice2 = dataAccessObject.getCost(portfolio.getStocks().get(1).getTickerSymbol());
        double currentPrice3 = dataAccessObject.getCost(portfolio.getStocks().get(2).getTickerSymbol());

        // Expected profit/loss
        double expectedProfitLoss =
                (currentPrice1 - 150.0) +
                        (currentPrice2 - 200.0) +
                        (currentPrice3 - 300.0);

        // Calculate using the method
        double actualProfitLoss = profitLossCalculator.calculateTotalProfitLoss();

        // Assert the values match
        assertEquals(expectedProfitLoss, actualProfitLoss, 0.01);
    }

    @Test
    void testCalculateStockProfitLoss() {
        // Setting up a sample ProfitLossCalculator
        ProfitLossCalculator profitLossCalculator = new ProfitLossCalculator(portfolio);

        // Sample current prices from API
        double currentPrice = dataAccessObject.getCost(portfolio.getStocks().get(0).getTickerSymbol());

        // Expected profit/loss for AAPL
        double expectedProfitLoss = (currentPrice - 150.0);

        // Calculate using the method
        double actualProfitLoss = profitLossCalculator.calculateStockProfitLoss("AAPL");

        // Assert the values match
        assertEquals(expectedProfitLoss, actualProfitLoss, 0.01);
    }
}