package use_case.signup;

/**
 * What the sign-up use case can be asked to do.
 */
public interface SignupInputBoundary {

    /**
     * Attempts to create an account from what the user filled in.
     *
     * @param signupInputData the contents of the sign-up form
     */
    void execute(SignupInputData signupInputData);

    /**
     * Leaves sign-up for the log-in screen, for a user who already has an account.
     */
    void switchToLoginView();
}
