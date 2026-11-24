package use_case.login;

/**
 * What the log-in use case can be asked to do.
 */
public interface LoginInputBoundary {

    /**
     * Attempts to log the user in with what they filled in.
     *
     * @param loginInputData the contents of the log-in form
     */
    void execute(LoginInputData loginInputData);

    /**
     * Leaves log-in for the sign-up screen, for a visitor with no account yet.
     */
    void switchToSignupView();
}
