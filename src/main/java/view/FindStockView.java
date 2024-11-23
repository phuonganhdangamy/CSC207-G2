package view;

import interface_adapter.find_stock.FindStockController;
import interface_adapter.find_stock.FindStockState;
import interface_adapter.find_stock.FindStockViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FindStockView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName  = "find stock";
    private final FindStockViewModel findStockViewModel;

    private final JTextField tickerInputField = new JTextField(15);
    private final JLabel tickerErrorField = new JLabel();

    private final JLabel errorLabel = new JLabel();

    private final JButton searchTicker;
    private FindStockController findStockController;

    public FindStockView(FindStockViewModel findStockViewModel) {

        this.findStockViewModel = findStockViewModel;
        this.findStockViewModel.addPropertyChangeListener(this);

        // Set layout for the panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel("Find stock");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setVisible(false);

        final LabelTextPanel tickerInfo = new LabelTextPanel(
                new JLabel("Ticker: "), tickerInputField);
        final JPanel buttons = new JPanel();
        searchTicker = new JButton("Search");
        buttons.add(searchTicker);

        searchTicker.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(searchTicker)) {
                            final FindStockState currentState = findStockViewModel.getState();
                            String tickerSymbol = tickerInputField.getText();

                            findStockController.execute(tickerSymbol);
                        }
                    }


                }
        );

        this.add(title);
        this.add(errorLabel);
        this.add(tickerInfo);
        this.add(buttons);
    }

    private void findStock(String stockName) {
        // Call controller to handle business logic
        System.out.println("Finding stock: " + stockName);
    }

    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("Find Stock Test");

        // Set default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add FindStockView to the frame
        FindStockView findStockView = new FindStockView(new FindStockViewModel());
        frame.add(findStockView);

        // Set frame properties
        frame.setSize(400, 300); // Adjust the size as needed
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    public String getViewName() {
        return viewName;
    }
}
