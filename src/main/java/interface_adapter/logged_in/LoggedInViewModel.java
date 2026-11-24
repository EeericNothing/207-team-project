package interface_adapter.logged_in;

import interface_adapter.ViewModel;

/**
 * The state of the screen a user reaches once logged in.
 */
public class LoggedInViewModel extends ViewModel<LoggedInState> {

    public static final String LOGOUT_BUTTON_LABEL = "Log out";

    public LoggedInViewModel() {
        super("logged in");
        setState(new LoggedInState());
    }
}
