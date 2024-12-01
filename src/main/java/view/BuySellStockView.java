package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import interface_adapter.buy_stock.BuyStockController;
import interface_adapter.logout.LogoutController;
import interface_adapter.sell_stock.SellStockController;

/**
 * The BuySellStockView is responsible for displaying the UI
 * for buying and selling stocks.
 */
public class BuySellStockView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 50;
    private static final int TEXT_FIELD_COLUMNS = 15;

    private final FindStockView findStockView;

    private JTextField sharesInputField;
    private JLabel sharesErrorLabel;

    private JButton buyButton;
    private JButton sellButton;
    private JButton logoutButton;

    private BuyStockController buyStockController;
    private SellStockController sellStockController;
    private LogoutController logoutController;

    private String username;

    /**
     * Constructor to initialize the BuySellStockView.
     *
     * @param findStockView The FindStockView component to link with.
     */
    public BuySellStockView(FindStockView findStockView) {
        this.findStockView = findStockView;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setupTitle();
        setupSeparators();
        setupSharesPanel();
        setupButtons();
        setupButtonListeners();
    }

    private void setupTitle() {
        final JLabel title = new JLabel("Make Transaction");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
    }

    private void setupSeparators() {
        final JSeparator topSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        final JSeparator bottomSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        topSeparator.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomSeparator.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(topSeparator);
        this.add(bottomSeparator);
    }

    private void setupSharesPanel() {
        sharesInputField = new JTextField(TEXT_FIELD_COLUMNS);
        sharesErrorLabel = new JLabel("! Balance is not enough");
        sharesErrorLabel.setForeground(Color.RED);
        sharesErrorLabel.setVisible(false);

        sharesInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        final JPanel sharesPanel = new JPanel(new BorderLayout());
        final LabelTextPanel sharesLabel = new LabelTextPanel(new JLabel("Number of Shares:"), sharesInputField);
        sharesPanel.add(sharesLabel, BorderLayout.NORTH);
        sharesPanel.add(sharesInputField, BorderLayout.CENTER);

        this.add(sharesPanel);
        this.add(sharesErrorLabel);
    }

    private void setupButtons() {
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buyButton = new JButton("Buy");
        sellButton = new JButton("Sell");
        logoutButton = new JButton("Log out");

        buyButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        sellButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        logoutButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        buttonPanel.add(buyButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(logoutButton);

        this.add(buttonPanel);
    }

    private void setupButtonListeners() {
        logoutButton.addActionListener(event -> logoutController.execute(username));

        sellButton.addActionListener(event -> {
            final String ticker = findStockView.getTickerField();
            final int quantity = Integer.parseInt(sharesInputField.getText());
            sellStockController.execute(quantity, ticker);
            sharesInputField.setText("");
            findStockView.setTickerField("");
        });

        buyButton.addActionListener(event -> {
            final String ticker = findStockView.getTickerField();
            final int quantity = Integer.parseInt(sharesInputField.getText());
            buyStockController.execute(username, ticker, quantity);
            sharesInputField.setText("");
            findStockView.setTickerField("");
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Method intentionally left empty
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Placeholder: Handle property changes here if needed
    }

    // Adding the logout use case to make the logout button functional.
    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    // Adding the sell stock use case to make the sell stock button functional.
    public void setSellStockController(SellStockController sellStockController) {
        this.sellStockController = sellStockController;
    }

    public void setBuyStockController(BuyStockController buyStockController) {
        this.buyStockController = buyStockController;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
