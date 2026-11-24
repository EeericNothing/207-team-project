package use_case.signup;

/**
 * How the sign-up use case reports what happened.
 */
public interface SignupOutputBoundary {

    /**
     * Reports that the account was created.
     *
     * @param response the account that was created
     */
    void prepareSuccessView(SignupOutputData response);

    /**
     * Reports that no account was created.
     *
     * @param errorMessage what to tell the user
     */
    void prepareFailView(String errorMessage);

    /**
     * Shows the log-in screen.
     */
    void switchToLoginView();
}
