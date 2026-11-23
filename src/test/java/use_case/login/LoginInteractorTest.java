package use_case.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import data_access.InMemoryUserDataAccessObject;
import data_access.PlainTextPasswordHasher;
import entity.CommonUserFactory;
import entity.UserFactory;
import entity.User;
import use_case.DataAccessException;
import use_case.PasswordHasher;

/**
 * Tests the rules of Use Case 2, Log In.
 */
class LoginInteractorTest {

    private static final String USERNAME = "berk";
    private static final String EMAIL = "berk@example.com";
    private static final String PASSWORD = "hunter2";

    private InMemoryUserDataAccessObject userDataAccessObject;
    private RecordingPresenter presenter;
    private PasswordHasher passwordHasher;

    @BeforeEach
    void setUp() {
        userDataAccessObject = new InMemoryUserDataAccessObject();
        presenter = new RecordingPresenter();
        passwordHasher = new PlainTextPasswordHasher();

        final UserFactory userFactory = new CommonUserFactory();
        userDataAccessObject.save(userFactory.create(USERNAME, EMAIL, passwordHasher.hash(PASSWORD)));
    }

    private void logIn(String username, String password) {
        new LoginInteractor(userDataAccessObject, presenter, passwordHasher)
                .execute(new LoginInputData(username, password));
    }

    @Test
    void theRightPasswordLetsTheUserIn() {
        logIn(USERNAME, PASSWORD);

        assertTrue(presenter.succeeded, "log-in should have succeeded: " + presenter.errorMessage);
        assertEquals(USERNAME, presenter.username);
        assertEquals(USERNAME, userDataAccessObject.getCurrentUsername());
    }

    @Test
    void theWrongPasswordIsRefusedAndNobodyIsLoggedIn() {
        logIn(USERNAME, "not the password");

        assertFalse(presenter.succeeded);
        assertNull(userDataAccessObject.getCurrentUsername());
    }

    @Test
    void anUnknownUsernameIsRefused() {
        logIn("nobody", PASSWORD);

        assertFalse(presenter.succeeded);
        assertNull(userDataAccessObject.getCurrentUsername());
    }

    @Test
    void anUnknownUsernameAndAWrongPasswordGiveTheSameMessage() {
        logIn("nobody", PASSWORD);
        final String messageForUnknownUser = presenter.errorMessage;

        presenter = new RecordingPresenter();
        logIn(USERNAME, "not the password");
        final String messageForWrongPassword = presenter.errorMessage;

        assertEquals(messageForUnknownUser, messageForWrongPassword,
                "the log-in screen must not reveal which accounts exist");
    }

    @Test
    void anEmptyFieldIsRefused() {
        logIn(USERNAME, "  ");

        assertFalse(presenter.succeeded);
        assertEquals("Please fill in every field.", presenter.errorMessage);
        assertNull(userDataAccessObject.getCurrentUsername());
    }

    @Test
    void aHashedPasswordIsComparedInItsStoredForm() {
        passwordHasher = rawPassword -> "hashed:" + rawPassword;
        userDataAccessObject = new InMemoryUserDataAccessObject();
        userDataAccessObject.save(new CommonUserFactory().create(USERNAME, EMAIL, "hashed:" + PASSWORD));

        logIn(USERNAME, PASSWORD);

        assertTrue(presenter.succeeded, "log-in should have succeeded: " + presenter.errorMessage);
    }

    @Test
    void askingForTheSignUpScreenReachesThePresenter() {
        new LoginInteractor(userDataAccessObject, presenter, passwordHasher).switchToSignupView();

        assertTrue(presenter.askedForSignupView);
    }

    @Test
    void storageThatCannotBeReachedSaysSoRatherThanRefusingTheUser() {
        final LoginUserDataAccessInterface brokenStorage = new LoginUserDataAccessInterface() {
            @Override
            public User get(String username) {
                throw new DataAccessException("the service is unreachable");
            }

            @Override
            public void setCurrentUsername(String username) {
                throw new DataAccessException("the service is unreachable");
            }
        };

        new LoginInteractor(brokenStorage, presenter, passwordHasher)
                .execute(new LoginInputData(USERNAME, PASSWORD));

        assertFalse(presenter.succeeded);
        assertTrue(presenter.errorMessage.contains("unavailable"), presenter.errorMessage);
    }

    /**
     * A presenter that remembers what it was told, so a test can check it.
     */
    private static class RecordingPresenter implements LoginOutputBoundary {

        private boolean succeeded;
        private boolean askedForSignupView;
        private String username;
        private String errorMessage;

        @Override
        public void prepareSuccessView(LoginOutputData response) {
            succeeded = true;
            username = response.getUsername();
        }

        @Override
        public void prepareFailView(String message) {
            succeeded = false;
            errorMessage = message;
        }

        @Override
        public void switchToSignupView() {
            askedForSignupView = true;
        }
    }
}
