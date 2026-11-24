package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

/**
 * The screen where someone with an account logs in.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private static final long serialVersionUID = 1L;
    private static final int FIELD_WIDTH = 18;

    private final String viewName;
    private final LoginViewModel loginViewModel;

    private final JTextField usernameInputField = new JTextField(FIELD_WIDTH);
    private final JPasswordField passwordInputField = new JPasswordField(FIELD_WIDTH);
    private final JLabel errorField = new JLabel(" ");
    private final JButton logInButton = new JButton(LoginViewModel.LOGIN_BUTTON_LABEL);
    private final JButton toSignupButton = new JButton(LoginViewModel.TO_SIGNUP_BUTTON_LABEL);

    private LoginController loginController;

    /**
     * Builds the screen and starts listening to its view model.
     *
     * @param loginViewModel the state this screen shows
     */
    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        this.viewName = loginViewModel.getViewName();
        loginViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(LoginViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorField.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorField.setForeground(Color.RED);

        final JPanel buttons = new JPanel();
        buttons.add(logInButton);
        final JPanel switchPanel = new JPanel();
        switchPanel.add(toSignupButton);

        logInButton.addActionListener(this);
        toSignupButton.addActionListener(this);

        // Pressing Enter in either box logs in, so the user does not have to reach
        // for the button after typing the password.
        usernameInputField.addActionListener(this);
        passwordInputField.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(errorField);
        this.add(new LabelTextPanel(new JLabel(LoginViewModel.USERNAME_LABEL), usernameInputField));
        this.add(new LabelTextPanel(new JLabel(LoginViewModel.PASSWORD_LABEL), passwordInputField));
        this.add(buttons);
        this.add(switchPanel);
    }

    /**
     * Gives this screen the controller it reports the user's actions to.
     *
     * @param controller the controller to use
     */
    public void setLoginController(LoginController controller) {
        this.loginController = controller;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(toSignupButton)) {
            loginController.switchToSignupView();
        }
        else {
            // The log-in button, or Enter pressed in one of the boxes.
            submit();
        }
    }

    /**
     * Asks for the user on screen to be logged in.
     */
    private void submit() {
        final LoginState typed = loginViewModel.getState();
        typed.setUsername(usernameInputField.getText());

        loginController.execute(
                usernameInputField.getText(),
                new String(passwordInputField.getPassword()));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = loginViewModel.getState();

        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());

        if (state.getErrorMessage() == null) {
            errorField.setText(" ");
        }
        else {
            errorField.setText(state.getErrorMessage());
        }
    }
}
