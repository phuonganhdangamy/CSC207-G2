package data_access;

import org.json.JSONObject;
import use_case.find_stock.FindStockDataAccessInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class DBStockDataAccessObject implements FindStockDataAccessInterface {

    @Override
    public double getCost(String tickerSymbol) {
        String apiKey = "ID8RVT9J10LA48HD"; // Your API key
        String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + tickerSymbol + "&apikey=" + apiKey;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Check the response code first
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Error: API request failed with response code " + responseCode);
                return -1;
            }

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close connections
            in.close();
            conn.disconnect();

            // Parse JSON to get the daily time series
            JSONObject json = new JSONObject(content.toString());
            if (!json.has("Time Series (Daily)")) {
                System.out.println("Error: No Time Series (Daily) data available.");
                return 0;
            }

            JSONObject timeSeries = json.getJSONObject("Time Series (Daily)");

            // Get the most recent date from the time series
            Iterator<String> keys = timeSeries.keys();
            String latestDate = null;
            while (keys.hasNext()) {
                String currentKey = keys.next();
                if (latestDate == null || currentKey.compareTo(latestDate) > 0) {
                    latestDate = currentKey; // Get the most recent date
                }
            }

            if (latestDate != null) {
                JSONObject latestData = timeSeries.getJSONObject(latestDate);
                String openPrice = latestData.getString("4. close"); // Get the open price (use "4. close" for close price)
                return Double.parseDouble(openPrice);
            } else {
                System.out.println("Error: No valid data found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Return a special value for error or no data
    }

    @Override
    public boolean isStockExist(String tickerSymbol) {
        return getCost(tickerSymbol) > 0;
    }

    public static void main(String[] args) {

        DBStockDataAccessObject stockDataAccess = new DBStockDataAccessObject();

        String testTicker = "AAPL";

        // Test getCost method
        System.out.println("Testing getCost method:");
        double cost = stockDataAccess.getCost(testTicker);
        if (cost != -1) {
            System.out.println("The cost of " + testTicker + " is: $" + cost);
        } else {
            System.out.println("Failed to retrieve the cost for " + testTicker);
        }

        // Test isStockExist method
        System.out.println("\nTesting isStockExist method:");
        boolean exists = stockDataAccess.isStockExist(testTicker);
        if (exists) {
            System.out.println("The stock " + testTicker + " exists.");
        } else {
            System.out.println("The stock " + testTicker + " does not exist.");
        }
    }
}