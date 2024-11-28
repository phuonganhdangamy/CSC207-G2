package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Stock class.
 */
public class StockTest {
    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock("AAPL", 150.0);
    }

    @Test
    void testGetTickerSymbol() {
        assertEquals("AAPL", stock.getTickerSymbol());
    }

    @Test
    void testGetCost() {
        assertEquals(150.0, stock.getCost());
    }

    @Test
    void testSetCost() {
        stock.setCost(350.0);
        assertEquals(350.0, stock.getCost());
    }

    @Test
    void testStockCreationWithFactory() {
        StockFactory factory = new StockFactory();
        Stock newStock = factory.create("MSFT", 300.0);

        assertNotNull(newStock, "The factory should create a non-null stock object.");
        assertEquals("MSFT", newStock.getTickerSymbol(),
                "Ticker symbol should match the provided value.");
        assertEquals(300.0, newStock.getCost(), "Cost should match the provided value.");
    }
}
