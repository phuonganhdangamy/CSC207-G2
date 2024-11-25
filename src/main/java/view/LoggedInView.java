package view;

import interface_adapter.LoggedInState;
import interface_adapter.buy_stock.BuyStockController;
import interface_adapter.LoggedInViewModel;
import interface_adapter.find_stock.FindStockController;
import interface_adapter.login.LoginState;
import interface_adapter.logout.LogoutController;
import interface_adapter.profit_loss.ProfitLossController;
import interface_adapter.sell_stock.SellStockController;
import interface_adapter.view_owned_stock.ViewOwnedStockController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

public class LoggedInView extends JPanel implements PropertyChangeListener {

    private final LoggedInViewModel loggedInViewModel;
    private JLabel balanceLabel;
    private JTable stockTable;
    private JTextField tickerInputField;
    private JTextField sharesInputField;
    private JLabel tickerErrorLabel;
    private JLabel sharesErrorLabel;
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
    private final ProfitLossController profitLossController;

    private String username = "<username>";
    private double balance = 0.0;
    private String viewName = "logged in";

    private final JPanel findStockPanel;
    private JPanel buySellStockPanel;


    public LoggedInView(LoggedInViewModel loggedInViewModel, FindStockView findStockView,
                        BuySellStockView buySellStockView, ProfitLossController profitLossController) {

        this.loggedInViewModel = loggedInViewModel;
        this.profitLossController = profitLossController;

        // Observe changes in ViewModel to update total profit loss
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Listen to state changes
        this.loggedInViewModel.addPropertyChangeListener(this);


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

//        transactionPanel.add(tickerPanel);
        transactionPanel.add(tickerErrorLabel);
//        transactionPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        findStockPanel = new JPanel(new BorderLayout());
        findStockPanel.add(findStockView, BorderLayout.CENTER);
        transactionPanel.add(findStockPanel, BorderLayout.CENTER);

        // Shares Input Field
        JPanel sharesPanel = new JPanel(new BorderLayout());
        JLabel sharesLabel = new JLabel("Shares:");
        sharesInputField = new JTextField(15);
        sharesInputField.setPreferredSize(new Dimension(200, 20));

        // Buy/Sell Stock Panel
//        buySellStockPanel = new JPanel(new BorderLayout());
//        buySellStockPanel.add(buySellStockView, BorderLayout.CENTER);
//        transactionPanel.add(buySellStockPanel, BorderLayout.CENTER);

        //Allows you to input only numbers
        sharesInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
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

        // Summary Panel: Profit/Loss
        profitLossLabel = new JLabel("Total Profit/Loss: +XX.XX%");
        profitLossLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profitLossLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        summaryPanel.add(profitLossLabel, BorderLayout.CENTER);

        // Adding Sections to Main Layout
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(transactionPanel, BorderLayout.EAST);
        this.add(summaryPanel, BorderLayout.SOUTH);



        // Search Button Listener
//        searchButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String ticker = tickerInputField.getText();
//                if (ticker.isEmpty()) {
//                    tickerErrorLabel.setText("! Ticker field cannot be empty");
//                    tickerErrorLabel.setVisible(true);
//                } else {
//                    tickerErrorLabel.setVisible(false);
//                    JOptionPane.showMessageDialog(LoggedInView.this, "Searching for ticker: " + ticker);
//                }
//            }
//        });

        // Logout Button Listener
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(LoggedInView.this, "Logged out. Redirecting to Login Page...");
                // Mock action will replace with controller later
                logoutController.execute(username);
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ticker = tickerInputField.getText();
                int quantity = Integer.parseInt(sharesInputField.getText());

                sellStockController.execute(quantity, ticker);
                sharesInputField.setText("");
                tickerInputField.setText("");
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

    //Needs implementation

    private void setFields(LoggedInState state) {
        setBalance(state.getBalance());
        updateBalanceLabel();

        setUsername(state.getUsername());
        setUsername(state.getUsername());

        // Update stock table data
        Object[][] stockData = state.getStockTable();
        String[] columnNames = {"Ticker", "Shares", "Profit/Loss"};
        stockTable.setModel(new DefaultTableModel(stockData, columnNames));

        // Clear error messages
        tickerErrorLabel.setVisible(false);
        sharesErrorLabel.setVisible(false);
        tickerErrorLabel.repaint();
        sharesErrorLabel.repaint();
        tickerErrorLabel.revalidate();
        sharesErrorLabel.revalidate();

        // Update profit/loss summary
        profitLossLabel.setText(String.format("Total Profit/Loss: %.2f%%", state.getTotalProfitLoss()));
    }


    //Adding the logout use case to make the logout button functional.
    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    //Adding the sell stock use case to make the sell stock button functional.
    public void setSellStockController(SellStockController sellStockController) {
        this.sellStockController = sellStockController;
    }

    public void setBuyStockController(BuyStockController buyStockController) {
        this.buyStockController = buyStockController;
    }

    public JPanel getFindStockPanel() {
        return findStockPanel;
    }
}


