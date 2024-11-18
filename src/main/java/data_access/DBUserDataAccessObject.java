package data_access;

import java.io.IOException;
import java.util.List;

import entity.*;
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
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.sell_stock.SellStockUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

public class DBUserDataAccessObject implements SignupUserDataAccessInterface, LoginUserDataAccessInterface,
        LogoutUserDataAccessInterface, SellStockUserDataAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final int CREDENTIAL_ERROR = 401;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private final StockFactory stockFactory;
    private final UserFactory userFactory;

    private final String BALANCE = "balance:";
    private final String PORTFOLIO = "portfolio:";
    private final String TICKER = "ticker:";
    private final String COST = "cost:";

    private String currentUsername;
    private User currentUser;


    public DBUserDataAccessObject(StockFactory stockFactory, UserFactory userFactory) {
        this.stockFactory = stockFactory;
        this.userFactory = userFactory;
    }


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
        } catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

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

    public void changePassword(User user) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        // POST METHOD
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, user.getName());
        requestBody.put(PASSWORD, user.getPassword());
        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/user")
                .method("PUT", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                // success!
            } else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        } catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }

    }

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

                //Creates a user and sets balance
                User user = userFactory.create(name, password);
                if (userInfo.isEmpty()){
                    return user;
                }
                user.setBalance(userInfo.getDouble(BALANCE));

                //Retrieves portfolio of stocks and creates the portfolio object
                JSONArray portfolioStocks = userInfo.getJSONArray(PORTFOLIO);
                Portfolio userPortfolio = user.getPortfolio();

                for (int i = 0; i < portfolioStocks.length(); i++) {
                    //Retrieves JSONObject representing a single stock
                    JSONObject jStock = portfolioStocks.getJSONObject(i);

                    //Retrieves instance variable information
                    String ticker = jStock.getString(TICKER);
                    double cost = jStock.getDouble(COST);

                    //Builds the stock object
                    Stock stock = stockFactory.create(ticker, cost);

                    //Adds stock to portfolio object
                    userPortfolio.addStock(stock);
                }

                return user;
            } else {
                throw new DataAccessException(responseBody.getString(MESSAGE));
            }
        } catch (IOException | JSONException | DataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void saveUserInfo(User user) {
        //Build JSON object
        Portfolio portfolio = user.getPortfolio();
        List<Stock> stocksList = portfolio.getStocks();

        final JSONObject userInfo = new JSONObject();
        final JSONArray portfolioStocks = new JSONArray();

        for (Stock stock : stocksList) {
            JSONObject stockInfo = new JSONObject();
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
                //success
            } else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        } catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }


    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    @Override
    public boolean logoutUser(String username) {
        // Simulate clearing session data
        if (username != null && !username.isEmpty() && username.equals(this.getCurrentUsername())) {
            this.setCurrentUsername(null); // Clear the current username to log out
            return true;
        }
        return false;
    }
}
