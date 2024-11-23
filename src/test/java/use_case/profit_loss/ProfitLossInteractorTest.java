package use_case.profit_loss;

import entity.Stock;
import entity.Portfolio;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        ProfitLossInputData inputData = new ProfitLossInputData("user123");

        profitLossInteractor.calculateTotalProfitLoss(inputData);

        // Expected total profit/loss
        double expectedProfitLoss =
                (160.0 - 150.0) * 5 +  // AAPL: (current price - cost) * shares
                        (210.0 - 200.0) * 10 + // GOOGL: (current price - cost) * shares
                        (310.0 - 300.0) * 15;  // TSLA: (current price - cost) * shares

        // Assertions
        assertFalse(presenter.useCaseFailed); // Check success view was prepared
        assertEquals(expectedProfitLoss, presenter.totalProfitLoss, 0.01); // Check the calculated total profit/loss
    }

    @Test
    void calculateStockProfitLossSuccessTest() {
        ProfitLossInputData inputData = new ProfitLossInputData("user123");

        profitLossInteractor.calculateStockProfitLoss(inputData, "AAPL");

        // Expected profit/loss
        double expectedProfitLoss = (160.0 - 150.0) * 5; // (current price - cost) * shares

        // Assertions
        assertFalse(presenter.useCaseFailed); // Check success view was prepared
        assertEquals(expectedProfitLoss, presenter.stockProfitLoss, 0.01); // Check the calculated stock profit/loss
        assertEquals("AAPL", presenter.stockTicker); // Check the stock ticker symbol
    }

    private static class TestProfitLossDataAccess implements ProfitLossDataAccessInterface {
        @Override
        public Portfolio getPortfolio(String username) {
            // Mock user and portfolio
            User user = new User(username, "password"); // Create a User object
            Portfolio portfolio = user.getPortfolio();

            // Add predefined stocks to the portfolio
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

    private static class TestProfitLossOutputBoundary implements ProfitLossOutputBoundary {
        boolean useCaseFailed = false;
        double totalProfitLoss = 0.0;
        double stockProfitLoss = 0.0;
        String stockTicker = null;

        @Override
        public void presentTotalProfitLoss(ProfitLossOutputData outputData) {
            this.useCaseFailed = false;
            this.totalProfitLoss = outputData.getProfitLoss();
        }

        @Override
        public void presentStockProfitLoss(ProfitLossOutputData outputData, String tickerSymbol) {
            this.useCaseFailed = false;
            this.stockProfitLoss = outputData.getProfitLoss();
            this.stockTicker = tickerSymbol;
        }
    }
}

