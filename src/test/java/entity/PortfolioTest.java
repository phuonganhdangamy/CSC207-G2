package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Portfolio class.
 */
public class PortfolioTest {
    private User testUser;
    private Portfolio portfolio;

    @BeforeEach
    void setUp() {
        testUser = new User("testUser", "password");
        portfolio = new Portfolio(testUser);
    }

    @Test
    void testAddStock() {
        Stock stock = new Stock("AAPL", 150.0);
        portfolio.addStock(stock);

        List<Stock> stocks = portfolio.getStocks();
        assertTrue(stocks.contains(stock));
        assertEquals(9850.0, testUser.getBalance());
    }

    @Test
    void testGetStocks() {
        Stock stock1 = new Stock("AAPL", 150.0);
        Stock stock2 = new Stock("GOOGL", 2800.0);

        portfolio.addStock(stock1);
        portfolio.addStock(stock2);

        List<Stock> stocks = portfolio.getStocks();
        assertEquals(2, stocks.size());
        assertTrue(stocks.contains(stock1));
        assertTrue(stocks.contains(stock2));
    }
    @Test
    void testRemoveStock() {
        Stock stock = new Stock("AAPL", 150.0);
        portfolio.addStock(stock);

        boolean removed = portfolio.removeStock("AAPL", 160.0);

        assertTrue(removed);
        assertFalse(portfolio.getStocks().contains(stock));
        assertEquals(10010.0, testUser.getBalance());
    }

    @Test
    void testRemoveNonexistentStock() {
        boolean removed = portfolio.removeStock("GOOGL", 2800.0);

        assertFalse(removed); // Removing a stock that doesn't exist in user's portfolio should return false
        assertEquals(10000.0, testUser.getBalance()); // User balance should remain unchanged
    }

    @Test
    void testGetShareCount() {
        portfolio.addStock(new Stock("AAPL", 150.0));
        portfolio.addStock(new Stock("AAPL", 150.0));
        portfolio.addStock(new Stock("GOOGL", 2800.0));

        assertEquals(2, portfolio.getShareCount("AAPL"));
        assertEquals(1, portfolio.getShareCount("GOOGL"));
        assertEquals(0, portfolio.getShareCount("MSFT"));
    }
}
