package data_access;

import entity.Stock;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DBStockDataAccessObject {

    public void setCurrentPrice(Stock stock) {
        String apiKey = "ID8RVT9J10LA48HD";
        String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + stock.getTickerSymbol() + "&apikey=" + apiKey;

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
                stock.setCurrentPrice(Double.parseDouble(openPrice));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
