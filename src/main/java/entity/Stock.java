package entity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;


/**
 * The Stock class represents a stock from our AlphaVantage API, where the user gives a tickerSymbol
 * and the companyName to get the current (in our case daily opening) price (currentPrice) of the stock.
 * The class also contains information on how many shares (NumberOfShares) the owner of the stock has
 * from that specific company.
 */

public class Stock {

    private final String tickerSymbol;
    private final String companyName;
    private double currentPrice;
    private int numberOfShares;

    public Stock(String tickerSymbol, String companyName) {
        this.tickerSymbol = tickerSymbol;
        this.companyName = companyName;
        setCurrentPrice();
        this.numberOfShares = 0;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public void setCurrentPrice() {
        String apiKey = "ID8RVT9J10LA48HD";
        String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + tickerSymbol + "&apikey=" + apiKey;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response.
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close connections.
            in.close();
            conn.disconnect();

            // Parse JSON to get today's open price.
            JSONObject json = new JSONObject(content.toString());
            JSONObject timeSeries = json.getJSONObject("Time Series (Daily)");

            // Get today's date in the format used by the AlphaVantage API.
            String today = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            if (timeSeries.has(today)) {
                JSONObject todayData = timeSeries.getJSONObject(today);
                String openPrice = todayData.getString("4. close");
                currentPrice = Double.parseDouble(openPrice);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }

}


