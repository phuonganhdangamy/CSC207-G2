package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import interface_adapter.LoggedInState;
import interface_adapter.LoggedInViewModel;
import interface_adapter.buy_stock.BuyStockController;
import interface_adapter.find_stock.FindStockController;
import interface_adapter.logout.LogoutController;
import interface_adapter.profit_loss.ProfitLossController;
import interface_adapter.sell_stock.SellStockController;
import interface_adapter.list_stocks.ViewOwnedStockController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 * The LoggedInView class represents the main UI for a user who is logged in.
 * It displays balance, stock information, and transaction options.
 * The class implements PropertyChangeListener to handle dynamic updates.
 */
public class LoggedInView extends JPanel implements PropertyChangeListener {

    private static final int BORDER_PADDING = 10;
    private static final int TITLE_FONT_SIZE = 16;
    private static final int LABEL_FONT_SIZE = 14;
    private static final String DEFAULT_FONT = "Arial";
    private static final int STOCK_TABLE_COLUMNS = 3;

    private final LoggedInViewModel loggedInViewModel;
    private BuySellStockView buySellStockView;
    private JLabel balanceLabel;
    private JTable stockTable;
    private JTextField tickerInputField;
    private JLabel tickerErrorLabel;
    private JLabel totalValueLabel;
    private JLabel profitLossLabel;

    private JButton searchButton;
    private FindStockController findStockController;
    private ViewOwnedStockController viewOwnedStockController;
    private ProfitLossController profitLossController;

    private String username = "<username>";
    private double balance;
    private String viewName = "logged in";

    private JPanel findStockPanel;
    private JPanel buySellStockPanel;

    public LoggedInView(LoggedInViewModel loggedInViewModel, FindStockView findStockView,
                        BuySellStockView buySellStockView) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.setLayout(new BorderLayout(BORDER_PADDING, BORDER_PADDING));
        this.setBorder(new EmptyBorder(BORDER_PADDING, BORDER_PADDING, BORDER_PADDING, BORDER_PADDING));

        this.findStockPanel = new JPanel(new BorderLayout());
        this.buySellStockView = buySellStockView;

        // Initialize UI Components
        initTopPanel();
        initStockTable();
        initTransactionPanel(findStockView, buySellStockView);
        initSummaryPanel();
    }

    private void initTopPanel() {
        final JLabel titleLabel = new JLabel("List of Stocks");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font(DEFAULT_FONT, Font.BOLD, TITLE_FONT_SIZE));

        balanceLabel = new JLabel();
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balanceLabel.setFont(new Font(DEFAULT_FONT, Font.PLAIN, LABEL_FONT_SIZE));
        updateBalanceLabel();

        final JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(titleLabel);
        topPanel.add(balanceLabel);
        this.add(topPanel, BorderLayout.NORTH);
    }

    private void initStockTable() {
        final String[] columnNames = {"Ticker", "Shares", "Profit/Loss"};
        stockTable = new JTable(new Object[0][columnNames.length], columnNames);
        final JScrollPane scrollPane = new JScrollPane(stockTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void initTransactionPanel(FindStockView findStockView, BuySellStockView buySellStockViewParam) {
        final JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.Y_AXIS));
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Transaction"));

        tickerErrorLabel = new JLabel("! Ticker doesn't exist");
        tickerErrorLabel.setForeground(Color.RED);
        tickerErrorLabel.setVisible(false);
        transactionPanel.add(tickerErrorLabel);

        findStockPanel = new JPanel(new BorderLayout());
        findStockPanel.add(findStockView, BorderLayout.CENTER);
        transactionPanel.add(findStockPanel);

        buySellStockPanel = new JPanel(new BorderLayout());
        this.buySellStockView = buySellStockViewParam;
        buySellStockPanel.add(buySellStockView, BorderLayout.CENTER);
        transactionPanel.add(buySellStockPanel);

        transactionPanel.add(Box.createRigidArea(new Dimension(0, BORDER_PADDING)));
        this.add(transactionPanel, BorderLayout.EAST);
    }

    private void initSummaryPanel() {
        profitLossLabel = new JLabel("Total Profit/Loss: +XX.XX$");
        profitLossLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profitLossLabel.setFont(new Font(DEFAULT_FONT, Font.PLAIN, LABEL_FONT_SIZE));

        final JPanel summaryPanel = new JPanel(new GridLayout(1, 3));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        summaryPanel.add(profitLossLabel);

        this.add(summaryPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the balance label dynamically.
     */
    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Hi, %s your Balance is %.2f$", username, balance));
    }

    /**
     * Sets the username and updates the balance label.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
        buySellStockView.setUsername(username);
        updateBalanceLabel();
    }

    /**
     * Sets the balance and updates the balance label.
     *
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
        updateBalanceLabel();
    }

    public String getViewName() {
        return this.viewName;
    }

    // Detects changes in the logged in state and updates UI accordingly
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoggedInState state = (LoggedInState) evt.getNewValue();
        setFields(state);

        // HANDLES ERROR CASE
        if (state.getError() != null && !state.getError().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getError(),
                    "Error", JOptionPane.ERROR_MESSAGE);

            state.setError("");
        }
    }

    /**
     * Updates the UI fields based on the state.
     *
     * @param state the updated logged-in state.
     */
    private void setFields(LoggedInState state) {
        // Update balance and username
        setBalance(state.getBalance());
        updateBalanceLabel();
        setUsername(state.getUsername());
        setUsername(state.getUsername());

        // Clear error messages
        tickerErrorLabel.setVisible(false);
        tickerErrorLabel.repaint();
        tickerErrorLabel.revalidate();

        // Update profit/loss summary
        profitLossLabel.setText(String.format("Total Profit/Loss: %.2f$", state.getTotalProfitLoss()));

        // Update stock table with data from LoggedInState
        final Map<String, Integer> stockOwnership = state.getStockOwnership();
        final Map<String, Double> stockProfitLoss = state.getStockProfitLoss();

        if (stockOwnership != null && stockProfitLoss != null) {
            // Convert data to a 2D array for the JTable
            final Object[][] tableData = new Object[stockOwnership.size()][STOCK_TABLE_COLUMNS];
            int index = 0;

            for (Map.Entry<String, Integer> entry : stockOwnership.entrySet()) {
                final String ticker = entry.getKey();
                final int shares = entry.getValue();
                // Default to 0.0 if not found
                final double profitLoss = stockProfitLoss.getOrDefault(ticker, 0.0);

                tableData[index++] = new Object[]{ticker, shares, String.format("%.2f$", profitLoss)};
            }

            // Update the JTable with the new data
            final String[] columnNames = {"Ticker", "Shares", "Profit/Loss"};
            stockTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));
        }
    }

    /**
     * Sets the controller for handling logout functionality.
     *
     * @param logoutController The controller responsible for managing logout operations.
     */
    public void setLogoutController(LogoutController logoutController) {
        buySellStockView.setLogoutController(logoutController);
    }

    /**
     * Sets the controller for handling the sell stock functionality.
     *
     * @param sellStockController The controller responsible for managing stock selling operations.
     */
    public void setSellStockController(SellStockController sellStockController) {
        buySellStockView.setSellStockController(sellStockController);
    }

    /**
     * Sets the controller for handling the buy stock functionality.
     *
     * @param buyStockController The controller responsible for managing stock buying operations.
     */
    public void setBuyStockController(BuyStockController buyStockController) {
        buySellStockView.setBuyStockController(buyStockController);
    }

    /**
     * Sets the controller for managing profit and loss operations.
     *
     * @param profitLossController The controller responsible for profit and loss calculations.
     */
    public void setProfitLossController(ProfitLossController profitLossController) {
        this.profitLossController = profitLossController;
    }

    /**
     * Retrieves the panel for displaying stock search functionality.
     *
     * @return The JPanel used for finding stocks.
     */
    public JPanel getFindStockPanel() {
        return findStockPanel;
    }
}
