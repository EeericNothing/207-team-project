package use_case.login;

import entity.User;
import use_case.DataAccessException;
import use_case.PasswordHasher;

/**
 * The log-in use case: the rules that decide whether someone may enter.
 */
public class LoginInteractor implements LoginInputBoundary {

    private static final String EMPTY_FIELD = "Please fill in every field.";

    /**
     * The one answer given whether the username is unknown or the password is wrong.
     *
     * <p>Use Case 2 asks for a single message for both, so that nobody can use the
     * log-in screen to find out which accounts exist.</p>
     */
    private static final String REFUSED = "Incorrect username or password.";
    private static final String UNAVAILABLE = "Logging in is unavailable right now. Please try again.";

    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;
    private final PasswordHasher passwordHasher;

    /**
     * Creates the use case.
     *
     * @param userDataAccessObject where accounts are looked up
     * @param loginPresenter       where the outcome is reported
     * @param passwordHasher       how a typed password becomes the stored form
     */
    public LoginInteractor(LoginUserDataAccessInterface userDataAccessObject,
                           LoginOutputBoundary loginPresenter,
                           PasswordHasher passwordHasher) {
        this.userDataAccessObject = userDataAccessObject;
        this.loginPresenter = loginPresenter;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        if (isMissing(username) || isMissing(password)) {
            loginPresenter.prepareFailView(EMPTY_FIELD);
        }
        else {
            try {
                logIn(username, password);
            }
            catch (DataAccessException exception) {
                loginPresenter.prepareFailView(UNAVAILABLE);
            }
        }
    }

    /**
     * Logs the user in if the account exists and the password matches it.
     *
     * @param username the username given
     * @param password the password typed
     */
    private void logIn(String username, String password) {
        final User user = userDataAccessObject.get(username);
        if (user != null && user.getPassword().equals(passwordHasher.hash(password))) {
            userDataAccessObject.setCurrentUsername(username);
            loginPresenter.prepareSuccessView(new LoginOutputData(username));
        }
        else {
            loginPresenter.prepareFailView(REFUSED);
        }
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }

    private static boolean isMissing(String value) {
        return value == null || value.trim().isEmpty();
    }
}
