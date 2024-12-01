package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private SignupController signupController;
    private final JLabel errorLabel = new JLabel();

    private final JButton signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
    private final JButton cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
    private final JButton toLogin = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        // Set layout first
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setupTitle();
        setupErrorLabel();
        setupInputFields();
        setupButtons();
        setupSwitchToLogin();

        addUsernameListener();
        addPasswordListener();
    }

    private void setupTitle() {
        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
    }

    private void setupErrorLabel() {
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setVisible(false);
        this.add(errorLabel);
    }

    private void setupInputFields() {
        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.USERNAME_LABEL), usernameInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.PASSWORD_LABEL), passwordInputField);

        this.add(usernameInfo);
        this.add(passwordInfo);
    }

    private void setupButtons() {
        final JPanel buttons = new JPanel();

        buttons.add(signUp);
        buttons.add(cancel);

        this.add(buttons);

        setupButtonActions();
    }

    private void setupButtonActions() {
        signUp.addActionListener(evt -> {
            final SignupState currentState = signupViewModel.getState();
            signupController.execute(currentState.getUsername(), currentState.getPassword());
        });

        toLogin.addActionListener(evt -> signupController.switchToLoginView());

        cancel.addActionListener(evt -> {
            usernameInputField.setText("");
            passwordInputField.setText("");
            final SignupState currentState = signupViewModel.getState();
            currentState.setError("");
            errorLabel.setVisible(false);
        });
    }

    private void setupSwitchToLogin() {
        final JLabel switchToLoginLabel = new JLabel("Have user:");
        final JPanel switchToLoginPanel = new JPanel();
        switchToLoginPanel.add(switchToLoginLabel);
        switchToLoginPanel.add(toLogin);
        this.add(switchToLoginPanel);
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
                // Clear the error message when the user modifies the username
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
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // Implement cancel button functionality like clear fields or navigate to another view
        usernameInputField.setText("");
        passwordInputField.setText("");
        errorLabel.setText(" ");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
        // Show the error message if username already exists
        if (state.getError() != null && !state.getError().isEmpty()) {
            errorLabel.setText(state.getError());
            errorLabel.setVisible(true);
        }
        else {
            errorLabel.setVisible(false);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }

    /**
     * Entry point for running the SignupView as a standalone application.
     * Sets up a JFrame and displays the SignupView panel.
     *
     * @param args the command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        final JPanel signUpView = new SignupView(new SignupViewModel());
        final JFrame frame = new JFrame();
        frame.add(signUpView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
