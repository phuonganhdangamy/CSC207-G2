package view;

import interface_adapter.find_stock.FindStockController;
import interface_adapter.find_stock.FindStockState;
import interface_adapter.find_stock.FindStockViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

    private final JLabel resultLabel = new JLabel();

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

        tickerErrorField.setForeground(Color.RED);
        tickerErrorField.setAlignmentX(Component.CENTER_ALIGNMENT);
        tickerErrorField.setVisible(false);

        final LabelTextPanel tickerInfo = new LabelTextPanel(
                new JLabel("Ticker: "), tickerInputField);
        final JPanel buttons = new JPanel();
        searchTicker = new JButton("Search");
        buttons.add(searchTicker);

        // Result label styling
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setForeground(Color.BLUE);
        resultLabel.setVisible(false);

        searchTicker.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(searchTicker)) {
                            // final FindStockState currentState = findStockViewModel.getState();
                            String tickerSymbol = tickerInputField.getText();
//                            JOptionPane.showMessageDialog(FindStockView.this,
//                                    tickerSymbol, "Test", JOptionPane.PLAIN_MESSAGE);

                            if (findStockController == null) {
                                JOptionPane.showMessageDialog(FindStockView.this,
                                        "FindStockController is not set!", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            findStockController.execute(tickerSymbol);
                        }
                    }
                }
        );

        tickerInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final FindStockState currentState = findStockViewModel.getState();
                currentState.setTickerSymbol(tickerInputField.getText());
                findStockViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {documentListenerHelper();}

            @Override
            public void removeUpdate(DocumentEvent e) {documentListenerHelper();}

            @Override
            public void changedUpdate(DocumentEvent e) {documentListenerHelper();}
        });

        this.add(title);
        this.add(errorLabel);
        this.add(tickerErrorField);
        this.add(tickerInfo);
        this.add(buttons);
        this.add(resultLabel);
    }

    private void findStock(String stockName) {
        // Call controller to handle business logic
        System.out.println("Finding stock: " + stockName);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("findStockSuccess".equals(evt.getPropertyName())) {
            String success = (String) evt.getNewValue();
            if ("true".equals(success)) {
                // If successful, show the success message
                String message = "Stock search was successful.\n" + findStockViewModel.getSuccess();
                showMessageDialog(message, "Success");
            } else {
                // If failed, show the error message
                String message = "Error: " + findStockViewModel.getError();
                showMessageDialog(message, "Error");
            }
        }
    }

    private void showMessageDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void setFields(FindStockState state) {
        tickerInputField.setText(state.getTickerSymbol());
    }

    public String getViewName() {return viewName;}

    /**
     * Sets the FindStockController for this view.
     *
     * @param controller the controller to handle user actions
     */
    public void setFindStockController(FindStockController controller) {
        this.findStockController = controller;
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
}
