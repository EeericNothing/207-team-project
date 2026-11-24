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

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The screen where a visitor creates an account.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {

    private static final long serialVersionUID = 1L;
    private static final int FIELD_WIDTH = 18;

    private final String viewName;
    private final SignupViewModel signupViewModel;

    private final JTextField usernameInputField = new JTextField(FIELD_WIDTH);
    private final JTextField emailInputField = new JTextField(FIELD_WIDTH);
    private final JPasswordField passwordInputField = new JPasswordField(FIELD_WIDTH);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(FIELD_WIDTH);
    private final JLabel errorField = new JLabel();
    private final JButton signUpButton = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
    private final JButton toLoginButton = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);

    private SignupController signupController;

    /**
     * Builds the screen and starts listening to its view model.
     *
     * @param signupViewModel the state this screen shows
     */
    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        this.viewName = signupViewModel.getViewName();
        signupViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        errorField.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorField.setForeground(Color.RED);

        final JPanel buttons = new JPanel();
        buttons.add(signUpButton);
        final JPanel switchPanel = new JPanel();
        switchPanel.add(toLoginButton);

        signUpButton.addActionListener(this);
        toLoginButton.addActionListener(this);

        // Pressing Enter in any of the boxes submits the form, so the user does not
        // have to reach for the button after typing the last field.
        usernameInputField.addActionListener(this);
        emailInputField.addActionListener(this);
        passwordInputField.addActionListener(this);
        repeatPasswordInputField.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(errorField);
        this.add(new LabelTextPanel(new JLabel(SignupViewModel.USERNAME_LABEL), usernameInputField));
        this.add(new LabelTextPanel(new JLabel(SignupViewModel.EMAIL_LABEL), emailInputField));
        this.add(new LabelTextPanel(new JLabel(SignupViewModel.PASSWORD_LABEL), passwordInputField));
        this.add(new LabelTextPanel(new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordInputField));
        this.add(buttons);
        this.add(switchPanel);
    }

    /**
     * Gives this screen the controller it reports the user's actions to.
     *
     * @param controller the controller to use
     */
    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(toLoginButton)) {
            signupController.switchToLoginView();
        }
        else {
            // The sign-up button, or Enter pressed in one of the boxes.
            submit();
        }
    }

    /**
     * Asks for an account to be created from what is on screen.
     */
    private void submit() {
        // Remember what was typed, so a failed attempt does not make the user type it again.
        final SignupState typed = signupViewModel.getState();
        typed.setUsername(usernameInputField.getText());
        typed.setEmail(emailInputField.getText());

        signupController.execute(
                usernameInputField.getText(),
                emailInputField.getText(),
                new String(passwordInputField.getPassword()),
                new String(repeatPasswordInputField.getPassword()));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = signupViewModel.getState();

        usernameInputField.setText(state.getUsername());
        emailInputField.setText(state.getEmail());
        passwordInputField.setText(state.getPassword());
        repeatPasswordInputField.setText(state.getRepeatPassword());

        if (state.getErrorMessage() == null) {
            errorField.setText(" ");
        }
        else {
            errorField.setText(state.getErrorMessage());
        }
    }
}
