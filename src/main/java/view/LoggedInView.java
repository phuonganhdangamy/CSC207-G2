package view;

import interface_adapter.LoggedInState;
import interface_adapter.buy_stock.BuyStockController;
import interface_adapter.LoggedInViewModel;
import interface_adapter.find_stock.FindStockController;
import interface_adapter.find_stock.FindStockViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.logout.LogoutController;
import interface_adapter.profit_loss.ProfitLossController;
import interface_adapter.sell_stock.SellStockController;
import interface_adapter.view_owned_stock.ViewOwnedStockController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

public class LoggedInView extends JPanel implements PropertyChangeListener {

    private final LoggedInViewModel loggedInViewModel;
    private final BuySellStockView buySellStockView;
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
    private double balance = 0.0;
    private String viewName = "logged in";

    private final JPanel findStockPanel;
    private JPanel buySellStockPanel;

    public LoggedInView(LoggedInViewModel loggedInViewModel,  FindStockView findStockView,
                        BuySellStockView buySellStockView) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Balance and Greeting
        JLabel titleLabel = new JLabel("List of Stocks");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        balanceLabel = new JLabel();
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        updateBalanceLabel();

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(titleLabel);
        topPanel.add(balanceLabel);

        // Stock Table
        String[] columnNames = {"Ticker", "Shares", "Profit/Loss"};

        stockTable = new JTable(new Object[0][columnNames.length], columnNames);
        JScrollPane scrollPane = new JScrollPane(stockTable);


        // Transaction Panel on right side
        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.Y_AXIS));
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Transaction"));

        // Ticker Input Field
        tickerErrorLabel = new JLabel("! Ticker doesn't exist");
        tickerErrorLabel.setForeground(Color.RED);
        tickerErrorLabel.setVisible(false);

        transactionPanel.add(tickerErrorLabel);

        findStockPanel = new JPanel(new BorderLayout());
        findStockPanel.add(findStockView, BorderLayout.CENTER);
        transactionPanel.add(findStockView, BorderLayout.CENTER);


        // Buy/Sell Stock Panel
        buySellStockPanel = new JPanel(new BorderLayout());
        this.buySellStockView = buySellStockView;
        buySellStockPanel.add(buySellStockView, BorderLayout.CENTER);
        transactionPanel.add(buySellStockPanel, BorderLayout.CENTER);


        transactionPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Buttons


        Dimension buttonSize = new Dimension(150, 60);

      
        // Summary Panel: Profit/Loss
        profitLossLabel = new JLabel("Total Profit/Loss: +XX.XX");
        profitLossLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profitLossLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel summaryPanel = new JPanel(new GridLayout(1, 3));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        summaryPanel.add(profitLossLabel);

        // Adding Sections to Main Layout
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(transactionPanel, BorderLayout.EAST);
        this.add(summaryPanel, BorderLayout.SOUTH);

    }


    /**
     * Updates the balance label dynamically.
     */
    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Hi, %s your Balance is %.2f", username, balance));
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

//        //HANDLES ERROR CASE
        if (state.getError() != null && !state.getError().isEmpty()){
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
        profitLossLabel.setText(String.format("Total Profit/Loss: %.2f", state.getTotalProfitLoss()));


        // Update stock table with data from LoggedInState
        Map<String, Integer> stockOwnership = state.getStockOwnership();
        Map<String, Double> stockProfitLoss = state.getStockProfitLoss();

        if (stockOwnership != null && stockProfitLoss != null) {
            // Convert data to a 2D array for the JTable
            Object[][] tableData = new Object[stockOwnership.size()][3];
            int index = 0;

            for (Map.Entry<String, Integer> entry : stockOwnership.entrySet()) {
                String ticker = entry.getKey();
                int shares = entry.getValue();
                double profitLoss = stockProfitLoss.getOrDefault(ticker, 0.0); // Default to 0.0 if not found

                tableData[index++] = new Object[]{ticker, shares, String.format("%.2f", profitLoss)};
            }

            // Update the JTable with the new data
            String[] columnNames = {"Ticker", "Shares", "Profit/Loss"};
            stockTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));
        }
    }

    //Adding the logout use case to make the logout button functional.
    public void setLogoutController(LogoutController logoutController) {
        buySellStockView.setLogoutController (logoutController);
    }

    //Adding the sell stock use case to make the sell stock button functional.
    public void setSellStockController(SellStockController sellStockController) {
        buySellStockView.setSellStockController (sellStockController);
    }

    public void setBuyStockController(BuyStockController buyStockController) {
        buySellStockView.setBuyStockController(buyStockController);
    }

    public void setProfitLossController(ProfitLossController profitLossController) {
        this.profitLossController = profitLossController;
    }

    public JPanel getFindStockPanel() {
        return findStockPanel;
    }
}