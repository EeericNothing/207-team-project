package use_case.create_calendar;

import entity.User;
import use_case.CurrentUserDataAccessInterface;
import use_case.DataAccessException;
import use_case.PasswordHasher;

/**
 * Validates and creates a shared calendar for the logged-in user.
 */
public final class CreateCalendarInteractor implements CreateCalendarInputBoundary {

    private final CurrentUserDataAccessInterface session;
    private final CreateCalendarDataAccessInterface calendars;
    private final PasswordHasher passwordHasher;
    private final CreateCalendarOutputBoundary presenter;

    /**
     * Creates the use case with its session, persistence, password, and presentation dependencies.
     *
     * @param session supplies the logged-in account
     * @param calendars saves the calendar and its first member together
     * @param passwordHasher hashes the submitted calendar password
     * @param presenter presents the outcome
     */
    public CreateCalendarInteractor(CurrentUserDataAccessInterface session,
                                    CreateCalendarDataAccessInterface calendars,
                                    PasswordHasher passwordHasher,
                                    CreateCalendarOutputBoundary presenter) {
        this.session = session;
        this.calendars = calendars;
        this.passwordHasher = passwordHasher;
        this.presenter = presenter;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(CreateCalendarInputData input) {
        final User creator = session.getCurrentUser();
        final String name = input.getName();
        final String password = input.getPassword();

        if (creator == null) {
            presenter.prepareFailView("Log in to create a calendar.");
        }
        else if (name == null || name.isBlank()) {
            presenter.prepareFailView("Enter a calendar name.");
        }
        else if (password == null || password.isBlank()) {
            presenter.prepareFailView("Enter a calendar password.");
        }
        else {
            saveCalendar(name.strip(), password, creator.getId());
        }
    }

    private void saveCalendar(String name, String password, int creatorId) {
        final String passwordHash = passwordHasher.hash(password);
        final int calendarId;
        try {
            calendarId = calendars.createWithFirstMember(name, passwordHash, creatorId);
        }
        catch (DataAccessException exception) {
            presenter.prepareFailView("Calendar creation could not be confirmed. Please check before trying again.");
            return;
        }
        presenter.prepareSuccessView(new CreateCalendarOutputData(calendarId, name));
    }
}
