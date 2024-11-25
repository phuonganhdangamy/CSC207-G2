package view;

import interface_adapter.buy_stock.BuyStockController;
import interface_adapter.buy_stock.BuyStockViewModel;
import interface_adapter.find_stock.FindStockViewModel;
import interface_adapter.sell_stock.SellStockController;
import interface_adapter.sell_stock.SellStockViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BuySellStockView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName  = "buy sell stock";
    private final BuyStockViewModel buyStockViewModel;
    private final SellStockViewModel sellStockViewModel;

    private final FindStockView findStockView;

    // private final JTextField sharesInputField = new JTextField(15);
    // private final JLabel sharesErrorField = new JLabel();

    private final JLabel errorLabel = new JLabel();

    private BuyStockController buyStockController;

    public BuySellStockView(BuyStockViewModel buyStockViewModel, SellStockViewModel sellStockViewModel,
                            FindStockView findStockView) {
        this.buyStockViewModel  = buyStockViewModel;
        this.sellStockViewModel = sellStockViewModel;
        this.findStockView = findStockView;

        this.buyStockViewModel.addPropertyChangeListener(this);
        this.sellStockViewModel.addPropertyChangeListener(this);

        // Set layout for the panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel("Make Transaction");

        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setVisible(false);

        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");

        // TO BE CONSIDERED: The textfield for entering the number of Shares won't be placed in BuyStockView and
        // SellStockView (they are repeated) but instead be placed in FindStockView or in LoggedInView (to have shared
        // behavior. -> Which one do you prefer?

        this.add(title);
        this.add(errorLabel);
        this.add(buyButton);
        this.add(sellButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("Buy Sell Stock Test");

        // Set default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BuySellStockView buySellStockView = new BuySellStockView(new BuyStockViewModel(), new SellStockViewModel(),
                new FindStockView(new FindStockViewModel()));

        frame.add(buySellStockView);

        // Set frame properties
        frame.setSize(400, 300); // Adjust the size as needed
        frame.setVisible(true);
    }
}
