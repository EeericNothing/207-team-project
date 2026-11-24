package interface_adapter.login;

import interface_adapter.ViewModel;

/**
 * The state of the log-in screen, and the labels it shows.
 */
public class LoginViewModel extends ViewModel<LoginState> {

    public static final String TITLE_LABEL = "Log in";
    public static final String USERNAME_LABEL = "Username";
    public static final String PASSWORD_LABEL = "Password";
    public static final String LOGIN_BUTTON_LABEL = "Log in";
    public static final String TO_SIGNUP_BUTTON_LABEL = "No account yet? Sign up";

    public LoginViewModel() {
        super("log in");
        setState(new LoginState());
    }
}
