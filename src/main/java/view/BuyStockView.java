package view;

import interface_adapter.buy_stock.BuyStockController;
import interface_adapter.buy_stock.BuyStockViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BuyStockView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName  = "buy stock";
    private final BuyStockViewModel buyStockViewModel;

    private final FindStockView findStockView;

    // private final JTextField sharesInputField = new JTextField(15);
    // private final JLabel sharesErrorField = new JLabel();

    private final JLabel errorLabel = new JLabel();

    private final JButton buyButton;
    private BuyStockController buyStockController;

    public BuyStockView(BuyStockViewModel buyStockViewModel, FindStockView findStockView) {
        this.buyStockViewModel  = buyStockViewModel;
        this.buyStockViewModel.addPropertyChangeListener(this);

        this.findStockView = findStockView;

        // Set layout for the panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel("Buy stock");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setVisible(false);

        buyButton = new JButton("Buy");
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // TO BE CONSIDERED: The textfield for entering the number of Shares won't be placed in BuyStockView and
        // SellStockView (they are repeated) but instead be placed in FindStockView or in LoggedInView (to have shared
        // behavior. -> Which one do you prefer?


        this.add(title);
        this.add(errorLabel);
        this.add(buyButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
