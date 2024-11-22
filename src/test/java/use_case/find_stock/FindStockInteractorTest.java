package use_case.find_stock;

import data_access.InMemoryStockDataAccessObject;
import entity.Stock;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FindStockInteractorTest {

    @Test
    void successTest() {

        FindStockInputData inputData = new FindStockInputData("AAPL");

        String username = "Paul";
        String tickerSymbol = "AAPL";
        double stockPrice = 150.0;
        Stock stock = new Stock(tickerSymbol, stockPrice);

        InMemoryStockDataAccessObject stockRepository = new InMemoryStockDataAccessObject();

        UserFactory userFactory = new UserFactory();
        User user = userFactory.create(username, "password");
        stockRepository.saveUser(user);
        stockRepository.saveStock(stock);

        // This creates a successPresenter that tests whether the test case is as we expect.
        FindStockOutputBoundary successPresenter = new FindStockOutputBoundary() {
            @Override
            public void prepareSuccessView(FindStockOutputData outputData) {
                assertEquals("AAPL", outputData.getTickerSymbol());
                assertEquals(150.0, outputData.getCurrentCost());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        FindStockInputBoundary interactor = new FindStockInteractor(stockRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureStockDoesNotExistTest() {

        FindStockInputData inputData = new FindStockInputData("TSLA");

        InMemoryStockDataAccessObject stockRepository = new InMemoryStockDataAccessObject();

        FindStockOutputBoundary failPresenter = new FindStockOutputBoundary() {
            @Override
            public void prepareSuccessView(FindStockOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("TSLA does not exist.", errorMessage);
            }
        };

        FindStockInputBoundary interactor = new FindStockInteractor(stockRepository, failPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureEmptyStockTest() {

        FindStockInputData inputData = new FindStockInputData(null);

        InMemoryStockDataAccessObject stockRepository = new InMemoryStockDataAccessObject();

        FindStockOutputBoundary failPresenter = new FindStockOutputBoundary() {
            @Override
            public void prepareSuccessView(FindStockOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Input cannot be empty.", errorMessage);
            }
        };

        FindStockInputBoundary interactor = new FindStockInteractor(stockRepository, failPresenter);
        interactor.execute(inputData);
    }
}
