package view;

import interface_adapter.LoggedInState;
import interface_adapter.buy_stock.BuyStockController;
import interface_adapter.LoggedInViewModel;
import interface_adapter.find_stock.FindStockController;
import interface_adapter.login.LoginState;
import interface_adapter.logout.LogoutController;
import interface_adapter.sell_stock.SellStockController;
import interface_adapter.view_owned_stock.ViewOwnedStockController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoggedInView extends JPanel implements PropertyChangeListener {

    private final LoggedInViewModel loggedInViewModel;
    private JLabel balanceLabel;
    private JTable stockTable;
    private JTextField tickerInputField;
    private JTextField sharesInputField;
    private JLabel tickerErrorLabel;
    private JLabel sharesErrorLabel;
    private JLabel totalValueLabel;
    private JLabel profitLossLabel;
    private JButton buyButton;
    private JButton sellButton;
    private JButton logoutButton;
    private JButton searchButton;
    private LogoutController logoutController;
    private FindStockController findStockController;
    private BuyStockController buyStockController;
    private SellStockController sellStockController;
    private ViewOwnedStockController viewOwnedStockController;

    private String username = "<username>";
    private double balance = 0.0;
    private String viewName = "logged in";

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
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
        String[] columnNames = {"Ticker", "Shares", "Cost", "Current Price", "Total Value", "Profit/Loss"};
        stockTable = new JTable(new Object[0][columnNames.length], columnNames);
        JScrollPane scrollPane = new JScrollPane(stockTable);


        // Transaction Panel on right side
        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.Y_AXIS));
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Transaction"));

        // Ticker Input Field
        JPanel tickerPanel = new JPanel(new BorderLayout());
        JLabel tickerLabel = new JLabel("Ticker:");
        tickerInputField = new JTextField(15);
        tickerInputField.setPreferredSize(new Dimension(200, 20));
        searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 40));
        tickerPanel.add(tickerLabel, BorderLayout.NORTH);
        tickerPanel.add(tickerInputField, BorderLayout.CENTER);
        tickerPanel.add(searchButton, BorderLayout.EAST);

        tickerErrorLabel = new JLabel("! Ticker doesn't exist");
        tickerErrorLabel.setForeground(Color.RED);
        tickerErrorLabel.setVisible(false);

        transactionPanel.add(tickerPanel);
        transactionPanel.add(tickerErrorLabel);
        transactionPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Shares Input Field
        JPanel sharesPanel = new JPanel(new BorderLayout());
        JLabel sharesLabel = new JLabel("Shares:");
        sharesInputField = new JTextField(15);
        sharesInputField.setPreferredSize(new Dimension(200, 20));

        sharesPanel.add(sharesLabel, BorderLayout.NORTH);
        sharesPanel.add(sharesInputField, BorderLayout.CENTER);

        sharesErrorLabel = new JLabel("! Balance is not enough");
        sharesErrorLabel.setForeground(Color.RED);
        sharesErrorLabel.setVisible(false);

        transactionPanel.add(sharesPanel);
        transactionPanel.add(sharesErrorLabel);
        transactionPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 10));
        buyButton = new JButton("Buy");
        sellButton = new JButton("Sell");
        logoutButton = new JButton("Log out");

        Dimension buttonSize = new Dimension(150, 60);
        buyButton.setPreferredSize(buttonSize);
        sellButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);

        buttonPanel.add(buyButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(logoutButton);
        transactionPanel.add(buttonPanel);

        // Summary
        JLabel purchasePriceLabel = new JLabel("Total Purchase Price: XXXXX.XX");
        JLabel currentPriceLabel = new JLabel("Total Current Price: XXXXX.XX");
        JLabel profitLossLabel = new JLabel("Total Profit/Loss: +XX.XX%");

        JPanel summaryPanel = new JPanel(new GridLayout(1, 3));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        summaryPanel.add(purchasePriceLabel);
        summaryPanel.add(currentPriceLabel);
        summaryPanel.add(profitLossLabel);

        // Adding Sections to Main Layout
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(transactionPanel, BorderLayout.EAST);
        this.add(summaryPanel, BorderLayout.SOUTH);

        // Search Button Listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ticker = tickerInputField.getText();
                if (ticker.isEmpty()) {
                    tickerErrorLabel.setText("! Ticker field cannot be empty");
                    tickerErrorLabel.setVisible(true);
                } else {
                    tickerErrorLabel.setVisible(false);
                    JOptionPane.showMessageDialog(LoggedInView.this, "Searching for ticker: " + ticker);
                }
            }
        });

        // Logout Button Listener
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(LoggedInView.this, "Logged out. Redirecting to Login Page...");
                // Mock action will replace with controller later
            }
        });
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

    /**
     * Mockup Main Function to Display the LoggedInView UI
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Logged In View");

        LoggedInView loggedInView = new LoggedInView(new LoggedInViewModel());
        loggedInView.setUsername("Testtest");
        loggedInView.setBalance(5555.55);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(loggedInView);
        frame.setSize(1000, 600); // Set the frame size
        frame.setVisible(true);
    }

    public String getViewName() {
        return this.viewName;
    }

    // Detects changes in the logged in state and updates UI accordingly
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        final LoggedInState state = (LoggedInState) evt.getNewValue();
//        setFields(state);
//
//        //HANDLES ERROR CASE
//        if (!state.getError().equals("") || state.getError() != null){
//            JOptionPane.showMessageDialog(this, state.getError(),
//                "Error", JOptionPane.ERROR_MESSAGE);
//
//            state.setError("");
//        }


    }

    //Needs implementation

    private void setFields(LoggedInState state) {

    }
}


