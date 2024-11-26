package view;

import use_case.list_stocks.ListStocksInputBoundary;
import use_case.list_stocks.ListStocksOutputBoundary;
import use_case.list_stocks.ListStocksInputData;
import use_case.list_stocks.ListStocksOutputData;
import use_case.profit_loss.ProfitLossInputBoundary;
import use_case.profit_loss.ProfitLossInputData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A GUI view for displaying the user's stock portfolio with Ticker, Shares, and Profit/Loss.
 */
public class ListStockView extends JPanel implements ListStocksOutputBoundary {

    private final ListStocksInputBoundary listStocksInteractor;
    private final ProfitLossInputBoundary profitLossInteractor;
    private final JTable stockTable;
    private final DefaultTableModel tableModel;
    private final Map<String, Double> profitLossData; // To store calculated profit/loss
    private Map<String, Double> currentPrices;
    private String username; // Store username for profit/loss calculations

    /**
     * Constructs the ListStockView.
     *
     * @param listStocksInteractor the input boundary for the List Stocks use case
     * @param profitLossInteractor the input boundary for the Profit/Loss use case
     */
    public ListStockView(ListStocksInputBoundary listStocksInteractor, ProfitLossInputBoundary profitLossInteractor) {
        this.listStocksInteractor = listStocksInteractor;
        this.profitLossInteractor = profitLossInteractor;
        this.profitLossData = new HashMap<>();

        setLayout(new BorderLayout());

        String[] columnNames = {"Ticker", "Shares", "Profit/Loss"};
        tableModel = new DefaultTableModel(columnNames, 0);
        stockTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(stockTable);

        JLabel title = new JLabel("Your Stock Portfolio", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(600, 400));
    }

    /**
     * Fetch and display the user's stock data.
     *
     * @param username      the username whose portfolio will be displayed
     * @param currentPrices a map of stock tickers to their current market prices
     */
    public void fetchAndDisplayStocks(String username, Map<String, Double> currentPrices) {
        this.username = username;
        this.currentPrices = currentPrices;

        // Request stock data from the interactor
        ListStocksInputData inputData = new ListStocksInputData(username);
        listStocksInteractor.execute(inputData);
    }

    /**
     * Updates the stock table based on the output data from the use case.
     *
     * @param outputData the output data containing stock information
     */
    @Override
    public void present(ListStocksOutputData outputData) {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Populate the table with stock data
        for (Map.Entry<String, Integer> entry : outputData.getStocksOwned().entrySet()) {
            String ticker = entry.getKey();
            int shares = entry.getValue();

            // Retrieve the current price
            double currentPrice = currentPrices.getOrDefault(ticker, 0.0);

            // Calculate profit/loss asynchronously
            ProfitLossInputData profitLossInputData = new ProfitLossInputData(username);
            profitLossInteractor.calculateStockProfitLoss(profitLossInputData, ticker, currentPrice);

            // Temporary placeholder until calculation is complete
            profitLossData.put(ticker, 0.0);

            // Add placeholder row to the table
            tableModel.addRow(new Object[]{ticker, shares, "Calculating..."});
        }
    }

    /**
     * Updates the profit/loss value in the table once calculation is complete.
     *
     * @param ticker      the stock ticker
     * @param profitLoss  the calculated profit/loss value
     */
    public void updateProfitLoss(String ticker, double profitLoss) {
        profitLossData.put(ticker, profitLoss);

        // Update the table row with the calculated value
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (tableModel.getValueAt(row, 0).equals(ticker)) {
                tableModel.setValueAt(String.format("%.2f", profitLoss), row, 2);
                break;
            }
        }
    }
}


