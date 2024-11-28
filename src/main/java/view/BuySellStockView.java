package view;

import interface_adapter.buy_stock.BuyStockController;
import interface_adapter.buy_stock.BuyStockViewModel;
import interface_adapter.find_stock.FindStockViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.sell_stock.SellStockController;
import interface_adapter.sell_stock.SellStockViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BuySellStockView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName  = "buy sell stock";
    //private final BuyStockViewModel buyStockViewModel;
   // private final SellStockViewModel sellStockViewModel;
    private String username;

    private final FindStockView findStockView;

    // private final JTextField sharesInputField = new JTextField(15);
    // private final JLabel sharesErrorField = new JLabel();

    private final JLabel errorLabel = new JLabel();
    private JTextField sharesInputField;
    private JLabel sharesErrorLabel;

    private JButton buyButton;
    private JButton sellButton;
    private JButton logoutButton;

    private BuyStockController buyStockController;
    private SellStockController sellStockController;
    private LogoutController logoutController;

    public BuySellStockView(FindStockView findStockView) {
        //this.buyStockViewModel  = buyStockViewModel;
        //this.sellStockViewModel = sellStockViewModel;
        this.findStockView = findStockView;

       // this.buyStockViewModel.addPropertyChangeListener(this);
        //this.sellStockViewModel.addPropertyChangeListener(this);

        // Set layout for the panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel("Make Transaction");

        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setVisible(false);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 50));
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


        // TO BE CONSIDERED: The textfield for entering the number of Shares won't be placed in BuyStockView and
        // SellStockView (they are repeated) but instead be placed in FindStockView or in LoggedInView (to have shared
        // behavior. -> Which one do you prefer?



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

        //Allows you to input only numbers
        sharesInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        this.add(title);
        this.add(sharesPanel);
        this.add(errorLabel);
        this.add(buyButton);
        this.add(sellButton);
        this.add(logoutButton);



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
                String ticker = findStockView.getTickerField();
                int quantity = Integer.parseInt(sharesInputField.getText());

                sellStockController.execute(quantity, ticker);
                sharesInputField.setText("");
                findStockView.setTickerField("");
            }
        });

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ticker = findStockView.getTickerField();
                int quantity = Integer.parseInt(sharesInputField.getText());

                buyStockController.execute(username, ticker, quantity);
                sharesInputField.setText("");
                findStockView.setTickerField("");
            }
        });





    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

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

    public void setUsername(String username){this.username = username;}


}
