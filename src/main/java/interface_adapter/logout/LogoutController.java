package interface_adapter.logout;

import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;

/**
 * Turns the user asking to leave into a call on the use case.
 */
public class LogoutController {

    private final LogoutInputBoundary logoutUseCaseInteractor;

    /**
     * Creates the controller.
     *
     * @param logoutUseCaseInteractor the use case to drive
     */
    public LogoutController(LogoutInputBoundary logoutUseCaseInteractor) {
        this.logoutUseCaseInteractor = logoutUseCaseInteractor;
    }

    /**
     * Asks for the session of the given user to be ended.
     *
     * @param username the user who is leaving
     */
    public void execute(String username) {
        logoutUseCaseInteractor.execute(new LogoutInputData(username));
    }
}
