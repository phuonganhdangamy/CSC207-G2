package view;

import java.awt.Color;
import java.awt.Component;
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

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements PropertyChangeListener {

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    private final JLabel errorLabel = new JLabel();

    private JButton logIn;
    private JButton cancel;
    private JButton signUp;
    private LoginController loginController;

    /**
     * Constructs the LoginView UI.
     *
     * @param loginViewModel the view model for login-related data and state
     */
    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        // Initialize components
        final JLabel title = createTitleLabel();
        configureErrorLabel();

        final LabelTextPanel usernameInfo = createLabelTextPanel("Username :", usernameInputField);
        final LabelTextPanel passwordInfo = createLabelTextPanel("Password :", passwordInputField);

        configureButtons();

        // Set layout and add components
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(errorLabel);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(createButtonsPanel());
        this.add(createSignUpPanel());
    }

    private JLabel createTitleLabel() {
        final JLabel title = new JLabel("Login page");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        return title;
    }

    private void configureErrorLabel() {
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setVisible(false);
    }

    private LabelTextPanel createLabelTextPanel(String labelText, JTextField inputField) {
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateModel() {
                final LoginState currentState = loginViewModel.getState();
                if (inputField instanceof JPasswordField) {
                    currentState.setPassword(new String(((JPasswordField) inputField).getPassword()));
                }
                else {
                    currentState.setUsername(inputField.getText());
                }
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateModel();
            }
        });
        return new LabelTextPanel(new JLabel(labelText), inputField);
    }

    private void configureButtons() {
        logIn = new JButton("Log in");
        cancel = new JButton("Cancel");
        signUp = new JButton("Sign up");

        logIn.addActionListener(evt -> {
            final LoginState currentState = loginViewModel.getState();
            loginController.execute(currentState.getUsername(), currentState.getPassword());
        });

        cancel.addActionListener(evt -> {
            usernameInputField.setText("");
            passwordInputField.setText("");
            final LoginState currentState = loginViewModel.getState();
            currentState.setLoginError("");
            errorLabel.setVisible(false);
        });

        signUp.addActionListener(evt -> {
            errorLabel.setVisible(false);
            loginController.switchToSignUpView();
        });
    }

    private JPanel createButtonsPanel() {
        final JPanel buttons = new JPanel();
        buttons.add(logIn);
        buttons.add(cancel);
        return buttons;
    }

    private JPanel createSignUpPanel() {
        final JPanel signUpPanel = new JPanel();
        final JLabel noUserLabel = new JLabel("No user:");
        signUpPanel.add(noUserLabel);
        signUpPanel.add(signUp);
        return signUpPanel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);

        if (state.getLoginError() != null && !state.getLoginError().isEmpty()) {
            errorLabel.setText(state.getLoginError());
            errorLabel.setVisible(true);
        }
        else {
            errorLabel.setVisible(false);
        }
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * The main method to run the LoginView UI.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        final JPanel loginView = new LoginView(new LoginViewModel());
        final JFrame frame = new JFrame();
        frame.add(loginView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
