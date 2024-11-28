package use_case.list_stocks;

import data_access.InMemoryUserDataAccessObject;
import entity.Portfolio;
import entity.Stock;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ListStocksInteractor.
 */
public class ListStocksInteractorTest {
    private User testUser;
    private ListStocksUserDataAccessInterface userDataAccess;
    private ListStocksOutputBoundary outputBoundary;

    @BeforeEach
    void setUp() {
        // Setting up a user and their portfolio
        testUser = new User("Maya", "securePassword");
        Portfolio portfolio = testUser.getPortfolio();

        // Adding stocks to the portfolio
        portfolio.addStock(new Stock("AAPL", 150.0));
        portfolio.addStock(new Stock("AAPL", 150.0));
        portfolio.addStock(new Stock("GOOG", 2800.0));

        // Simulating the user database
        userDataAccess = new ListStocksUserDataAccessInterface() {
            private User user = testUser;

            @Override
            public User getCurrentUser() {
                return user;
            }
        };
    }

    @Test
    void successTest() {
        ListStocksInputData inputData = new ListStocksInputData("Maya");

        // Define a presenter to capture the output
        outputBoundary = new ListStocksOutputBoundary() {
            @Override
            public void present(ListStocksOutputData outputData) {
                Map<String, Integer> stocksOwned = outputData.getStocksOwned();
                assertNotNull(stocksOwned, "Output data should not be null.");
                assertEquals(2, stocksOwned.get("AAPL"), "User should own 2 shares of AAPL.");
                assertEquals(1, stocksOwned.get("GOOG"), "User should own 1 share of GOOG.");
            }
        };

        // Initialize and execute the interactor
        ListStocksInteractor interactor = new ListStocksInteractor(outputBoundary, userDataAccess);
        interactor.execute(inputData);
    }

    @Test
    void userNotFoundTest() {
        // Simulate a null user case in the database
        userDataAccess = new ListStocksUserDataAccessInterface() {
            @Override
            public User getCurrentUser() {
                return null;
            }
        };

        ListStocksInputData inputData = new ListStocksInputData("NonExistentUser");

        // Define a presenter to capture the failure scenario
        outputBoundary = new ListStocksOutputBoundary() {
            @Override
            public void present(ListStocksOutputData outputData) {
                fail("This should not be called for a non-existent user.");
            }
        };

        // Initialize and execute the interactor
        ListStocksInteractor interactor = new ListStocksInteractor(outputBoundary, userDataAccess);
        assertThrows(IllegalArgumentException.class, () -> interactor.execute(inputData),
                "Expected an IllegalArgumentException for a non-existent user.");
    }

    @Test
    void emptyPortfolioTest() {
        // Simulate an empty portfolio by not adding any stocks
        testUser = new User("Maya", "securePassword"); // New user with an empty portfolio

        // Simulate user data access for the new user
        userDataAccess = new ListStocksUserDataAccessInterface() {
            private User user = testUser;

            @Override
            public User getCurrentUser() {
                return user;
            }
        };

        ListStocksInputData inputData = new ListStocksInputData("Maya");

        // Define a presenter to validate the output for an empty portfolio
        outputBoundary = new ListStocksOutputBoundary() {
            @Override
            public void present(ListStocksOutputData outputData) {
                Map<String, Integer> stocksOwned = outputData.getStocksOwned();
                assertNotNull(stocksOwned, "Output data should not be null.");
                assertTrue(stocksOwned.isEmpty(), "Portfolio should be empty.");
            }
        };

        // Initialize and execute the interactor
        ListStocksInteractor interactor = new ListStocksInteractor(outputBoundary, userDataAccess);
        interactor.execute(inputData);
    }
}