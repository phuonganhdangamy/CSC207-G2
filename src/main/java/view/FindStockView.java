package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.find_stock.FindStockController;
import interface_adapter.find_stock.FindStockState;
import interface_adapter.find_stock.FindStockViewModel;

/**
 * The FindStockView class represents the UI for finding stock details.
 */
public class FindStockView extends JPanel implements PropertyChangeListener {

    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 300;
    private static final int FONT_SIZE = 12;
    private static final Font ERROR_LABEL_FONT = new Font("Arial", Font.BOLD, FONT_SIZE);

    private final String viewName = "find stock";
    private final FindStockViewModel findStockViewModel;

    private final JTextField tickerInputField = new JTextField(15);
    private final JLabel tickerErrorField = new JLabel("");

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
        errorLabel.setFont(ERROR_LABEL_FONT);
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

        searchTicker.addActionListener(this::handleSearchTickerAction);

        tickerInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final FindStockState currentState = findStockViewModel.getState();
                currentState.setTickerSymbol(tickerInputField.getText());
                findStockViewModel.setState(currentState);
                errorLabel.setText(" ");
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        this.add(title);
        this.add(tickerErrorField);
        this.add(tickerInfo);
        this.add(errorLabel);
        this.add(resultLabel);
        this.add(buttons);
    }

    private void handleSearchTickerAction(ActionEvent evt) {
        String errorMessage = null;
        final String tickerSymbol = tickerInputField.getText();

        if (!evt.getSource().equals(searchTicker)) {
            errorMessage = "Invalid action source!";
        }
        else if (findStockController == null) {
            errorMessage = "FindStockController is not set!";
        }
        else {
            findStockController.execute(tickerSymbol);
        }

        if (errorMessage != null) {
            JOptionPane.showMessageDialog(
                    FindStockView.this,
                    errorMessage,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final FindStockState state = findStockViewModel.getState();
        if ("findStockState".equals(evt.getPropertyName())) {
            if ("true".equals(state.getSuccess())) {
                errorLabel.setVisible(false);
                resultLabel.setText(state.getMessage());
                resultLabel.setVisible(true);
            }
            else {
                errorLabel.setText(state.getError());
                errorLabel.setVisible(true);
                resultLabel.setVisible(false);
            }
            this.revalidate();
            this.repaint();
        }
    }

    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the FindStockController for this view.
     *
     * @param controller the controller to handle user actions
     */
    public void setFindStockController(FindStockController controller) {
        this.findStockController = controller;
    }

    public String getTickerField() {
        return tickerInputField.getText();
    }

    /**
     * The main method to test the FindStockView UI.
     * This method creates a frame and adds the FindStockView to it for testing purposes.
     *
     * @param args command-line arguments, not used in this method.
     */
    public static void main(String[] args) {
        // Create the JFrame
        final JFrame frame = new JFrame("Find Stock Test");

        // Set default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add FindStockView to the frame
        final FindStockView findStockView = new FindStockView(new FindStockViewModel());
        frame.add(findStockView);

        // Set frame properties
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
    }

    /**
     * Sets the text in the ticker input field.
     *
     * @param tickerSymbol the ticker symbol to set in the input field
     */
    public void setTickerField(String tickerSymbol) {
        tickerInputField.setText(tickerSymbol);
    }
}
