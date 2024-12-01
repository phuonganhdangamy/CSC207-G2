package data_access;

import java.io.IOException;
import java.util.List;

import entity.Portfolio;
import entity.Stock;
import entity.StockFactory;
import entity.User;
import entity.UserFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import use_case.DataAccessException;
import use_case.UserDataAccessInterface;
import use_case.buy_stock.BuyStockUserDataAccessInterface;
import use_case.list_stocks.ListStocksUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.profit_loss.ProfitLossDataAccessInterface;
import use_case.sell_stock.SellStockUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * The DBUserDataAccessObject class provides implementations for various user-related operations
 * such as signup, login, logout, buying and selling stocks, listing stocks, and managing profit/loss.
 * It communicates with a remote database via HTTP requests to perform these operations.
 */
public class DBUserDataAccessObject implements SignupUserDataAccessInterface, LoginUserDataAccessInterface,
        LogoutUserDataAccessInterface, SellStockUserDataAccessInterface, BuyStockUserDataAccessInterface,
        ListStocksUserDataAccessInterface, ProfitLossDataAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final int CREDENTIAL_ERROR = 401;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private static final String BALANCE = "balance:";
    private static final String PORTFOLIO = "portfolio:";
    private static final String TICKER = "ticker:";
    private static final String COST = "cost:";

    private final StockFactory stockFactory;
    private final UserFactory userFactory;

    private String currentUsername;
    private User currentUser;

    /**
     * Constructor to initialize DBUserDataAccessObject with the required factories.
     *
     * @param stockFactory the factory for creating stock objects.
     * @param userFactory  the factory for creating user objects.
     */
    public DBUserDataAccessObject(StockFactory stockFactory, UserFactory userFactory) {
        this.stockFactory = stockFactory;
        this.userFactory = userFactory;
    }

    /**
     * Checks if a user exists in the database by username.
     *
     * @param username the username to check.
     * @return true if the user exists, false otherwise.
     */
    @Override
    public boolean existsByName(String username) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/checkIfUserExists?username=%s", username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            return responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE;
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Saves a new user to the database.
     *
     * @param user the user to be saved.
     */
    @Override
    public void save(User user) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        // Uses the correct API method to create a new User in the database
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, user.getName());
        requestBody.put(PASSWORD, user.getPassword());
        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/user")
                .method("POST", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                // success!
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Retrieves a user from the database by username.
     *
     * @param username the username of the user to retrieve.
     * @return the User object retrieved from the database.
     */
    @Override
    public User get(String username) {
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s", username))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                final JSONObject userJSONObject = responseBody.getJSONObject("user");
                final String name = userJSONObject.getString(USERNAME);
                final String password = userJSONObject.getString(PASSWORD);
                final JSONObject userInfo = userJSONObject.getJSONObject("info");

                // Creates a user and sets balance
                final User user = userFactory.create(name, password);

                if (userInfo.isEmpty()) {
                    return user;
                }
                user.setBalance(userInfo.getDouble(BALANCE));

                // Retrieves portfolio of stocks and creates the portfolio object
                final JSONArray portfolioStocks = userInfo.getJSONArray(PORTFOLIO);
                final Portfolio userPortfolio = user.getPortfolio();

                for (int i = 0; i < portfolioStocks.length(); i++) {
                    // Retrieves JSONObject representing a single stock
                    final JSONObject jStock = portfolioStocks.getJSONObject(i);

                    // Retrieves instance variable information
                    final String ticker = jStock.getString(TICKER);
                    final double cost = jStock.getDouble(COST);

                    // Builds the stock object
                    final Stock stock = stockFactory.create(ticker, cost);

                    // Adds stock to portfolio object
                    userPortfolio.addStock(stock);
                }
                return user;
            }
            else {
                throw new DataAccessException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException | DataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Saves updated user information to the database.
     *
     * @param user the user whose information is to be updated.
     */
    @Override
    public void saveUserInfo(User user) {
        // Build JSON object
        final Portfolio portfolio = user.getPortfolio();
        final List<Stock> stocksList = portfolio.getStocks();

        final JSONObject userInfo = new JSONObject();
        final JSONArray portfolioStocks = new JSONArray();

        for (Stock stock : stocksList) {
            final JSONObject stockInfo = new JSONObject();
            stockInfo.put(TICKER, stock.getTickerSymbol());
            stockInfo.put(COST, stock.getCost());
            portfolioStocks.put(stockInfo);
        }

        userInfo.put(BALANCE, user.getBalance());
        userInfo.put(PORTFOLIO, portfolioStocks);

        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        // POST METHOD
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, user.getName());
        requestBody.put(PASSWORD, user.getPassword());

        requestBody.put("info", userInfo);
        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/modifyUserInfo")
                .method("PUT", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                // success
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Gets the username of the currently logged-in user.
     *
     * @return the current username.
     */
    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    /**
     * Sets the current user in the session.
     *
     * @param user the user to set as the current user.
     */
    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Gets the current user from the session.
     *
     * @return the current User object.
     */
    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Sets the current username in the session.
     *
     * @param username the username to set as the current username.
     */
    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    /**
     * Logs out the current user by clearing session data.
     *
     * @param username the username of the user to log out.
     * @return true if logout is successful, false otherwise.
     */
    @Override
    public boolean logoutUser(String username) {
        // Simulate clearing session data
        if (username != null && !username.isEmpty() && username.equals(this.getCurrentUsername())) {
            // Clear the current username to log out
            this.setCurrentUsername(null);
            return true;
        }
        return false;
    }
}
