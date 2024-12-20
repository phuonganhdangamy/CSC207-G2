package data_access;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONObject;

import use_case.find_stock.FindStockDataAccessInterface;

/**
 * The DBStockDataAccessObject class provides access to stock data from an external API, such as AlphaVantage,
 * by retrieving the daily opening price for a given ticker symbol. It also verifies whether a stock exists
 * based on the availability of price data.
 * Responsibilities:
 * - Retrieves the current price of a stock using the AlphaVantage API.
 * - Checks if the stock exists by validating the price data.
 * Methods:
 * - getCost(String tickerSymbol): Retrieves the current cost (price) of the stock for the provided ticker symbol.
 * - isStockExist(String tickerSymbol): Checks if the stock exists by verifying if the price data can be retrieved.
 */

public class DBStockDataAccessObject implements FindStockDataAccessInterface {

    /**
     * Retrieves the current cost (price) of the stock for the given ticker symbol by querying the
     * AlphaVantage API.
     *
     * @param tickerSymbol The ticker symbol of the stock to get the price for.
     * @return The current cost of the stock, or 0 if an error occurs or the stock data is unavailable.
     */

    @Override
    public double getCost(String tickerSymbol) {
        // Our API key
        final String apiKey = "ID8RVT9J10LA48HD";
        final String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + tickerSymbol
                + "&apikey=" + apiKey;

        try {
            final URL url = new URL(urlString);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Check the response code first
            final int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Error: API request failed with response code " + responseCode);
                return 0;
            }

            // Read the response
            final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close connections
            in.close();
            conn.disconnect();

            // Parse JSON to get the daily time series
            final JSONObject json = new JSONObject(content.toString());
            if (!json.has("Time Series (Daily)")) {
                System.out.println("Error: No Time Series (Daily) data available.");
                return 0;
            }

            final JSONObject timeSeries = json.getJSONObject("Time Series (Daily)");

            // Get the most recent date from the time series
            final Iterator<String> keys = timeSeries.keys();
            String latestDate = null;
            while (keys.hasNext()) {
                final String currentKey = keys.next();
                if (latestDate == null || currentKey.compareTo(latestDate) > 0) {
                    // Get the most recent date
                    latestDate = currentKey;
                }
            }

            if (latestDate != null) {
                final JSONObject latestData = timeSeries.getJSONObject(latestDate);
                // Get the open price (use "4. close" for close price)
                final String openPrice = latestData.getString("4. close");
                return Double.parseDouble(openPrice);
            }
            else {
                System.out.println("Error: No valid data found.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // Return a special value for error or no data
        return 0;
    }

    /**
     * Checks whether a stock exists by verifying if the price data can be retrieved from the API.
     *
     * @param tickerSymbol The ticker symbol of the stock to check.
     * @return true if the stock exists, false otherwise.
     */

    @Override
    public boolean isStockExist(String tickerSymbol) {
        return getCost(tickerSymbol) > 0;
    }

    // public static void main(String[] args) {

    //    DBStockDataAccessObject stockDataAccess = new DBStockDataAccessObject();

    //    String testTicker = "IBM";

    // Test getCost method
    //    System.out.println("Testing getCost method:");
    //    double cost = stockDataAccess.getCost(testTicker);
    //    if (cost != 0) {
    //        System.out.println("The cost of " + testTicker + " is: $" + cost);
    //    } else {
    //        System.out.println("Failed to retrieve the cost for " + testTicker);
    //    }
    // Test isStockExist method
    //    System.out.println("\nTesting isStockExist method:");
    //    boolean exists = stockDataAccess.isStockExist(testTicker);
    //    if (exists) {
    //        System.out.println("The stock " + testTicker + " exists.");
    //    } else {
    //        System.out.println("The stock " + testTicker + " does not exist.");
    //    }
    // }
}
