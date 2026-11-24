package use_case;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import data_access.InMemoryUserDataAccessObject;

class NoCurrentUserExceptionTest {

    @Test
    void hasAHelpfulMessage() {
        final NoCurrentUserException exception = new NoCurrentUserException();

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void sessionReportsNullWhenNobodyIsLoggedIn() {
        // The case this exception exists for: the shared session has no user.
        final CurrentUserDataAccessInterface session = new InMemoryUserDataAccessObject();

        assertNull(session.getCurrentUser());
    }
}
