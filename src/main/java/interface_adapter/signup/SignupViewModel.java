package interface_adapter.signup;

import interface_adapter.ViewModel;

/**
 * The state of the sign-up screen, and the labels it shows.
 */
public class SignupViewModel extends ViewModel<SignupState> {

    public static final String TITLE_LABEL = "Create an account";
    public static final String USERNAME_LABEL = "Username";
    public static final String EMAIL_LABEL = "Email";
    public static final String PASSWORD_LABEL = "Password";
    public static final String REPEAT_PASSWORD_LABEL = "Repeat password";
    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String TO_LOGIN_BUTTON_LABEL = "Already have an account? Log in";

    public SignupViewModel() {
        super("sign up");
        setState(new SignupState());
    }
}
