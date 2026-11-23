package use_case.signup;

import entity.User;
import entity.UserFactory;
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
        final String email = signupInputData.getEmail();
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
            userDataAccessObject.save(user);
            userPresenter.prepareSuccessView(new SignupOutputData(username));
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
