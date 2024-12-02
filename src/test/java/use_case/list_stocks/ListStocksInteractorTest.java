package use_case.list_stocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Portfolio;
import entity.Stock;
import entity.User;

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
        final Portfolio portfolio = testUser.getPortfolio();

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
        final ListStocksInputData inputData = new ListStocksInputData("Maya");

        // Define a presenter to capture the output
        outputBoundary = new ListStocksOutputBoundary() {
            @Override
            public void present(ListStocksOutputData outputData) {
                final Map<String, Integer> stocksOwned = outputData.getStocksOwned();
                assertNotNull(stocksOwned, "Output data should not be null.");
                assertEquals(2, stocksOwned.get("AAPL"), "User should own 2 shares of AAPL.");
                assertEquals(1, stocksOwned.get("GOOG"), "User should own 1 share of GOOG.");
            }
        };

        // Initialize and execute the interactor
        final ListStocksInteractor interactor = new ListStocksInteractor(outputBoundary, userDataAccess);
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

        final ListStocksInputData inputData = new ListStocksInputData("NonExistentUser");

        // Define a presenter to capture the failure scenario
        outputBoundary = new ListStocksOutputBoundary() {
            @Override
            public void present(ListStocksOutputData outputData) {
                fail("This should not be called for a non-existent user.");
            }
        };

        // Initialize and execute the interactor
        final ListStocksInteractor interactor = new ListStocksInteractor(outputBoundary, userDataAccess);
        assertThrows(IllegalArgumentException.class, () -> interactor.execute(inputData),
                "Expected an IllegalArgumentException for a non-existent user.");
    }

    @Test
    void emptyPortfolioTest() {
        // Simulate an empty portfolio by not adding any stocks
        testUser = new User("Maya", "securePassword");

        // Simulate user data access for the new user
        userDataAccess = new ListStocksUserDataAccessInterface() {
            private User user = testUser;

            @Override
            public User getCurrentUser() {
                return user;
            }
        };

        final ListStocksInputData inputData = new ListStocksInputData("Maya");

        // Define a presenter to validate the output for an empty portfolio
        outputBoundary = new ListStocksOutputBoundary() {
            @Override
            public void present(ListStocksOutputData outputData) {
                final Map<String, Integer> stocksOwned = outputData.getStocksOwned();
                assertNotNull(stocksOwned, "Output data should not be null.");
                assertTrue(stocksOwned.isEmpty(), "Portfolio should be empty.");
            }
        };

        // Initialize and execute the interactor
        final ListStocksInteractor interactor = new ListStocksInteractor(outputBoundary, userDataAccess);
        interactor.execute(inputData);
    }

    @Test
    void nullUsernameInputDataTest() {
        assertThrows(IllegalArgumentException.class, () -> new ListStocksInputData(null),
                "Expected an IllegalArgumentException for null username.");
    }

    @Test
    void getUsernameTest() {
        ListStocksInputData inputData = new ListStocksInputData("Maya");
        assertEquals("Maya", inputData.getUsername(), "Expected username to match input.");
    }

    @Test
    void nullStocksOwnedOutputDataTest() {
        assertThrows(IllegalArgumentException.class, () -> new ListStocksOutputData(null),
                "Expected an IllegalArgumentException for null stocksOwned.");
    }

    @Test
    void getStocksOwnedTest() {
        Map<String, Integer> stockMap = Map.of("AAPL", 2, "GOOG", 1);
        ListStocksOutputData outputData = new ListStocksOutputData(stockMap);

        assertEquals(stockMap, outputData.getStocksOwned(),
                "Expected the same stock map to be returned by getStocksOwned.");
    }
}