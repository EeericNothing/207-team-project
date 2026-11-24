package use_case.logout;

/**
 * The log-out use case: ending a session so the machine can be handed over.
 */
public class LogoutInteractor implements LogoutInputBoundary {

    private final LogoutUserDataAccessInterface userDataAccessObject;
    private final LogoutOutputBoundary logoutPresenter;

    /**
     * Creates the use case.
     *
     * @param userDataAccessObject where the current user is recorded
     * @param logoutPresenter      where the outcome is reported
     */
    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccessObject,
                            LogoutOutputBoundary logoutPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.logoutPresenter = logoutPresenter;
    }

    @Override
    public void execute(LogoutInputData logoutInputData) {
        userDataAccessObject.setCurrentUser(null);
        logoutPresenter.prepareSuccessView(new LogoutOutputData(logoutInputData.getUsername()));
    }
}
