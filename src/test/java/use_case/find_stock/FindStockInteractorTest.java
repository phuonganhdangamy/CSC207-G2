package use_case.find_stock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import data_access.InMemoryStockDataAccessObject;
import entity.Stock;
import entity.User;
import entity.UserFactory;

public class FindStockInteractorTest {

    @Test
    void successTest() {

        final FindStockInputData inputData = new FindStockInputData("AAPL");

        final String username = "Paul";
        final String tickerSymbol = "AAPL";
        final double stockPrice = 150.0;
        final Stock stock = new Stock(tickerSymbol, stockPrice);

        final InMemoryStockDataAccessObject stockRepository = new InMemoryStockDataAccessObject();

        final UserFactory userFactory = new UserFactory();
        final User user = userFactory.create(username, "password");
        stockRepository.saveUser(user);
        stockRepository.saveStock(stock);

        // This creates a successPresenter that tests whether the test case is as we expect.
        final FindStockOutputBoundary successPresenter = new FindStockOutputBoundary() {
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

        final FindStockInputBoundary interactor = new FindStockInteractor(stockRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureStockDoesNotExistTest() {

        final FindStockInputData inputData = new FindStockInputData("TSLA");

        final InMemoryStockDataAccessObject stockRepository = new InMemoryStockDataAccessObject();

        final FindStockOutputBoundary failPresenter = new FindStockOutputBoundary() {
            @Override
            public void prepareSuccessView(FindStockOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("TSLA does not exist.", errorMessage);
            }
        };

        final FindStockInputBoundary interactor = new FindStockInteractor(stockRepository, failPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureEmptyStockTest() {

        final FindStockInputData inputData = new FindStockInputData(null);

        final InMemoryStockDataAccessObject stockRepository = new InMemoryStockDataAccessObject();

        final FindStockOutputBoundary failPresenter = new FindStockOutputBoundary() {
            @Override
            public void prepareSuccessView(FindStockOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Input cannot be empty.", errorMessage);
            }
        };

        final FindStockInputBoundary interactor = new FindStockInteractor(stockRepository, failPresenter);
        interactor.execute(inputData);
    }
}
