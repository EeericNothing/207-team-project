package use_case.logout;

/**
 * What the log-out use case can be asked to do.
 */
public interface LogoutInputBoundary {

    /**
     * Ends the current session.
     *
     * @param logoutInputData who is logging out
     */
    void execute(LogoutInputData logoutInputData);
}
