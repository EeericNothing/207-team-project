package use_case.login;

/**
 * How the log-in use case reports what happened.
 */
public interface LoginOutputBoundary {

    /**
     * Reports that the user is now logged in.
     *
     * @param response the user who logged in
     */
    void prepareSuccessView(LoginOutputData response);

    /**
     * Reports that the user is not logged in.
     *
     * @param errorMessage what to tell the user
     */
    void prepareFailView(String errorMessage);

    /**
     * Shows the sign-up screen.
     */
    void switchToSignupView();
}
