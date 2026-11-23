package use_case.logout;

/**
 * How the log-out use case reports what happened.
 */
public interface LogoutOutputBoundary {

    /**
     * Reports that nobody is logged in any more and shows the log-in screen.
     *
     * @param response the user who left
     */
    void prepareSuccessView(LogoutOutputData response);
}
