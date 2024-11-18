package entity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;


import data_access.DBStockDataAccessObject;

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
    private final String tickerSymbol;
    private double cost;

    // Cannot pass in DBStockDataAccessObject to the entity!
    // Clean Architecture approach: only use DBStockDataAccessObject in the use case interactors (find/buy stock)
    public Stock(String tickerSymbol, double cost) {
        this.tickerSymbol = tickerSymbol;
        this.cost = cost;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public static void main(String[] args) {
        // Stock stock = new Stock("IBM", "IBM");
        // System.out.println(stock.currentPrice);
    }

    public static void main(String[] args) {
        // Stock stock = new Stock("IBM", "IBM");
        // System.out.println(stock.currentPrice);
    }
}


