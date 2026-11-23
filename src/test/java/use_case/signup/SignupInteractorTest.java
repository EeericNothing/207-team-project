package use_case.signup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
 * Tests the rules of Use Case 1, Sign Up.
 */
class SignupInteractorTest {

    private static final String USERNAME = "berk";
    private static final String EMAIL = "berk@example.com";
    private static final String PASSWORD = "hunter2";

    private InMemoryUserDataAccessObject userDataAccessObject;
    private RecordingPresenter presenter;
    private UserFactory userFactory;
    private PasswordHasher passwordHasher;

    @BeforeEach
    void setUp() {
        userDataAccessObject = new InMemoryUserDataAccessObject();
        presenter = new RecordingPresenter();
        userFactory = new CommonUserFactory();
        passwordHasher = new PlainTextPasswordHasher();
    }

    private SignupInteractor interactor() {
        return new SignupInteractor(userDataAccessObject, presenter, userFactory, passwordHasher);
    }

    private void signUp(String username, String email, String password, String repeatPassword) {
        interactor().execute(new SignupInputData(username, email, password, repeatPassword));
    }

    @Test
    void anAccountIsCreatedWhenEverythingIsInOrder() {
        signUp(USERNAME, EMAIL, PASSWORD, PASSWORD);

        assertTrue(presenter.succeeded, "sign-up should have succeeded: " + presenter.errorMessage);
        assertEquals(USERNAME, presenter.username);
        assertTrue(userDataAccessObject.existsByName(USERNAME));
        assertTrue(userDataAccessObject.existsByEmail(EMAIL));
    }

    @Test
    void theStoredPasswordIsWhatTheHasherProduced() {
        passwordHasher = rawPassword -> "hashed:" + rawPassword;

        signUp(USERNAME, EMAIL, PASSWORD, PASSWORD);

        assertTrue(presenter.succeeded);
        assertEquals("hashed:" + PASSWORD, userDataAccessObject.get(USERNAME).getPassword());
    }

    @Test
    void aTakenUsernameIsRefused() {
        signUp(USERNAME, EMAIL, PASSWORD, PASSWORD);
        presenter = new RecordingPresenter();

        signUp(USERNAME, "someone.else@example.com", PASSWORD, PASSWORD);

        assertFalse(presenter.succeeded);
        assertEquals("That username is already taken.", presenter.errorMessage);
    }

    @Test
    void aRegisteredEmailIsRefusedAndTheUserIsPointedAtLogIn() {
        signUp(USERNAME, EMAIL, PASSWORD, PASSWORD);
        presenter = new RecordingPresenter();

        signUp("someoneElse", EMAIL, PASSWORD, PASSWORD);

        assertFalse(presenter.succeeded);
        assertTrue(presenter.errorMessage.contains("already exists"), presenter.errorMessage);
        assertTrue(presenter.errorMessage.toLowerCase().contains("log in"), presenter.errorMessage);
    }

    @Test
    void capitalisingAnEmailDoesNotGetItPastTheCheck() {
        signUp(USERNAME, EMAIL, PASSWORD, PASSWORD);
        presenter = new RecordingPresenter();

        signUp("someoneElse", "BERK@EXAMPLE.COM", PASSWORD, PASSWORD);

        assertFalse(presenter.succeeded);
        assertTrue(presenter.errorMessage.contains("already exists"), presenter.errorMessage);
    }

    @Test
    void passwordsThatDifferAreRefused() {
        signUp(USERNAME, EMAIL, PASSWORD, "something else");

        assertFalse(presenter.succeeded);
        assertEquals("The two passwords do not match.", presenter.errorMessage);
        assertFalse(userDataAccessObject.existsByName(USERNAME));
    }

    @Test
    void anEmptyFieldIsRefused() {
        signUp(USERNAME, "   ", PASSWORD, PASSWORD);

        assertFalse(presenter.succeeded);
        assertEquals("Please fill in every field.", presenter.errorMessage);
        assertFalse(userDataAccessObject.existsByName(USERNAME));
    }

    @Test
    void anAddressThatIsNotAnEmailIsRefused() {
        signUp(USERNAME, "berk-at-example.com", PASSWORD, PASSWORD);

        assertFalse(presenter.succeeded);
        assertEquals("That does not look like an email address.", presenter.errorMessage);
        assertFalse(userDataAccessObject.existsByName(USERNAME));
    }

    @Test
    void askingForTheLogInScreenReachesThePresenter() {
        interactor().switchToLoginView();

        assertTrue(presenter.askedForLoginView);
    }

    @Test
    void theEmailIsStoredInOneCaseWhateverTheUserTyped() {
        signUp(USERNAME, "Berk@Example.COM", PASSWORD, PASSWORD);

        assertTrue(presenter.succeeded, presenter.errorMessage);
        assertEquals(EMAIL, userDataAccessObject.get(USERNAME).getEmail());
    }

    @Test
    void aStorageFailureLeavesNoAccountAndSaysSo() {
        final SignupUserDataAccessInterface brokenStorage = new SignupUserDataAccessInterface() {
            @Override
            public boolean existsByName(String username) {
                return false;
            }

            @Override
            public boolean existsByEmail(String email) {
                return false;
            }

            @Override
            public void save(User user) {
                throw new DataAccessException("the service is unreachable");
            }
        };

        new SignupInteractor(brokenStorage, presenter, userFactory, passwordHasher)
                .execute(new SignupInputData(USERNAME, EMAIL, PASSWORD, PASSWORD));

        assertFalse(presenter.succeeded);
        assertTrue(presenter.errorMessage.contains("no account was created"), presenter.errorMessage);
    }

    /**
     * A presenter that remembers what it was told, so a test can check it.
     */
    private static final class RecordingPresenter implements SignupOutputBoundary {

        private boolean succeeded;
        private boolean askedForLoginView;
        private String username;
        private String errorMessage;

        @Override
        public void prepareSuccessView(SignupOutputData response) {
            succeeded = true;
            username = response.getUsername();
        }

        @Override
        public void prepareFailView(String message) {
            succeeded = false;
            errorMessage = message;
        }

        @Override
        public void switchToLoginView() {
            askedForLoginView = true;
        }
    }
}
