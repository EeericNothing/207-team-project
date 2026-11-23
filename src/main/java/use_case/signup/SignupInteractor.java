package use_case.signup;

import java.util.Locale;

import entity.User;
import entity.UserFactory;
import use_case.DataAccessException;
import use_case.PasswordHasher;

/**
 * The sign-up use case: the rules an account has to satisfy before it is created.
 */
public class SignupInteractor implements SignupInputBoundary {

    private static final String EMPTY_FIELD = "Please fill in every field.";
    private static final String USERNAME_TAKEN = "That username is already taken.";
    private static final String MALFORMED_EMAIL = "That does not look like an email address.";
    private static final String EMAIL_TAKEN =
            "An account with that email address already exists. Please log in instead.";
    private static final String PASSWORDS_DIFFER = "The two passwords do not match.";
    private static final String COULD_NOT_STORE =
            "Sign-up failed and no account was created. Please try again.";

    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;
    private final PasswordHasher passwordHasher;

    /**
     * Creates the use case.
     *
     * @param userDataAccessObject where accounts are looked up and stored
     * @param userPresenter        where the outcome is reported
     * @param userFactory          how a new user is created
     * @param passwordHasher       how a typed password becomes a stored one
     */
    public SignupInteractor(SignupUserDataAccessInterface userDataAccessObject,
                            SignupOutputBoundary userPresenter,
                            UserFactory userFactory,
                            PasswordHasher passwordHasher) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
        this.userFactory = userFactory;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        final String username = signupInputData.getUsername();
        final String email = normalise(signupInputData.getEmail());
        final String password = signupInputData.getPassword();
        final String repeatPassword = signupInputData.getRepeatPassword();

        if (isMissing(username) || isMissing(email) || isMissing(password) || isMissing(repeatPassword)) {
            userPresenter.prepareFailView(EMPTY_FIELD);
        }
        else if (userDataAccessObject.existsByName(username)) {
            userPresenter.prepareFailView(USERNAME_TAKEN);
        }
        else if (!looksLikeAnEmailAddress(email)) {
            userPresenter.prepareFailView(MALFORMED_EMAIL);
        }
        else if (userDataAccessObject.existsByEmail(email)) {
            userPresenter.prepareFailView(EMAIL_TAKEN);
        }
        else if (!password.equals(repeatPassword)) {
            userPresenter.prepareFailView(PASSWORDS_DIFFER);
        }
        else {
            final User user = userFactory.create(username, email, passwordHasher.hash(password));
            try {
                userDataAccessObject.save(user);
                userPresenter.prepareSuccessView(new SignupOutputData(username));
            }
            catch (DataAccessException exception) {
                userPresenter.prepareFailView(COULD_NOT_STORE);
            }
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }

    /**
     * Checks that the given text has the shape of an email address: something, then a
     * single at sign, then a domain with a dot that is neither first nor last.
     *
     * @param email the text the user gave
     * @return true if it could be an email address
     */
    /**
     * Puts an email address into the one form this program stores it in.
     *
     * <p>An address identifies a single account however it is capitalised, so it is
     * lowered here rather than at each place that compares one. Locale.ROOT is used
     * deliberately: in a Turkish locale the default would turn "I" into a dotless
     * letter and two spellings of the same address would stop matching.</p>
     *
     * @param email the address as the user typed it
     * @return the address as it is stored and compared
     */
    private static String normalise(String email) {
        String normalised = email;
        if (normalised != null) {
            normalised = normalised.trim().toLowerCase(Locale.ROOT);
        }
        return normalised;
    }

    private static boolean looksLikeAnEmailAddress(String email) {
        final int at = email.indexOf('@');
        final int lastAt = email.lastIndexOf('@');
        final int lastDot = email.lastIndexOf('.');
        return at > 0
                && at == lastAt
                && lastDot > at + 1
                && lastDot < email.length() - 1
                && email.indexOf(' ') < 0;
    }

    private static boolean isMissing(String value) {
        return value == null || value.trim().isEmpty();
    }
}
