package use_case.create_calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import entity.CommonUserFactory;
import entity.User;
import use_case.DataAccessException;

/** Tests UC5's validation, storage request, and presentation outcomes. */
class CreateCalendarInteractorTest {

    private static final int CREATOR_ID = 7;
    private static final int CALENDAR_ID = 42;
    private static final User CREATOR =
            new CommonUserFactory().createStored(CREATOR_ID, "eric", "eric@example.com", "account-password");

    private final RecordingPresenter presenter = new RecordingPresenter();

    @Test
    void requiresAnActiveSession() {
        assertRejected(null, "Study group", "calendar-password", "Log in to create a calendar.");
    }

    @Test
    void identifiesBlankCalendarName() {
        assertRejected(CREATOR, " \t ", "calendar-password", "Enter a calendar name.");
    }

    @Test
    void identifiesBlankCalendarPassword() {
        assertRejected(CREATOR, "Study group", " \t ", "Enter a calendar password.");
    }

    @Test
    void savesWithTheLoggedInCreatorAndPresentsTheAssignedId() {
        final String rawPassword = " calendar-password ";
        final String hash = "hashed-password";
        final CreateCalendarInteractor interactor = new CreateCalendarInteractor(
                () -> CREATOR,
                (name, passwordHash, creatorId) -> {
                    assertEquals("Study group", name);
                    assertEquals(hash, passwordHash);
                    assertEquals(CREATOR_ID, creatorId);
                    return CALENDAR_ID;
                },
                password -> {
                    assertEquals(rawPassword, password);
                    return hash;
                },
                presenter);

        interactor.execute(new CreateCalendarInputData(" Study group ", rawPassword));

        assertNull(presenter.failure);
        assertNotNull(presenter.success);
        assertEquals(CALENDAR_ID, presenter.success.getCalendarId());
        assertEquals("Study group", presenter.success.getCalendarName());
    }

    @Test
    void reportsAnUnconfirmedSaveWithoutOpeningACalendar() {
        final CreateCalendarInteractor interactor = new CreateCalendarInteractor(
                () -> CREATOR,
                (name, passwordHash, creatorId) -> {
                    throw new DataAccessException("Simulated storage failure");
                },
                password -> "hashed-password",
                presenter);

        interactor.execute(new CreateCalendarInputData("Study group", "calendar-password"));

        assertEquals("Calendar creation could not be confirmed. Please check before trying again.",
                presenter.failure);
        assertNull(presenter.success);
    }

    private void assertRejected(User user, String name, String password, String message) {
        final CreateCalendarInteractor interactor = new CreateCalendarInteractor(
                () -> user,
                (calendarName, passwordHash, creatorId) -> fail("Rejected input must not reach storage"),
                rawPassword -> fail("Rejected input must not be hashed"),
                presenter);

        interactor.execute(new CreateCalendarInputData(name, password));

        assertEquals(message, presenter.failure);
        assertNull(presenter.success);
    }

    /** Records the outcome delivered by the interactor. */
    private static final class RecordingPresenter implements CreateCalendarOutputBoundary {
        private CreateCalendarOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(CreateCalendarOutputData output) {
            success = output;
        }

        @Override
        public void prepareFailView(String message) {
            failure = message;
        }
    }
}
