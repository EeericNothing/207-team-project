package use_case.logout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import data_access.InMemoryUserDataAccessObject;

/**
 * Tests the rules of Use Case 3, Log Out.
 */
class LogoutInteractorTest {

    private static final String USERNAME = "berk";

    @Test
    void loggingOutLeavesNobodyLoggedIn() {
        final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();
        userDataAccessObject.setCurrentUsername(USERNAME);
        final RecordingPresenter presenter = new RecordingPresenter();

        new LogoutInteractor(userDataAccessObject, presenter).execute(new LogoutInputData(USERNAME));

        assertNull(userDataAccessObject.getCurrentUsername());
        assertTrue(presenter.succeeded);
        assertEquals(USERNAME, presenter.username);
    }

    /**
     * A presenter that remembers what it was told, so a test can check it.
     */
    private static final class RecordingPresenter implements LogoutOutputBoundary {

        private boolean succeeded;
        private String username;

        @Override
        public void prepareSuccessView(LogoutOutputData response) {
            succeeded = true;
            username = response.getUsername();
        }
    }
}
