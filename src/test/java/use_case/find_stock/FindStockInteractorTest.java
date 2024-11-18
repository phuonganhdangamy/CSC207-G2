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
        String username = "Paul";
        String tickerSymbol = "AAPL";
        double stockPrice = 150.0;
        Stock stock = new Stock(tickerSymbol, stockPrice);

        InMemoryStockDataAccessObject stockRepository = new InMemoryStockDataAccessObject();

        UserFactory userFactory = new UserFactory();
        User user = userFactory.create(username, "password");
        stockRepository.saveUser(user);
        stockRepository.saveStock(stock);
    }
}
