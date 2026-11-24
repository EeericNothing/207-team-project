package use_case;

/**
 * Thrown when a use case needs the logged-in user but nobody is logged in.
 *
 * <p>{@link CurrentUserDataAccessInterface#getCurrentUser()} returns null when nobody is
 * logged in. Calendar View (Use Case 8) and View Event (Use Case 13) can only be reached
 * after logging in, so a null there means the screens were wired wrongly rather than
 * that the user did something wrong. Those interactors throw this exception instead of
 * letting the null travel on and fail later with a less helpful error.</p>
 *
 * <p>It lives in the use case layer, next to {@code DataAccessException}, so that no
 * interactor has to know where the session is kept.</p>
 */
public class NoCurrentUserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates the exception with a standard message.
     */
    public NoCurrentUserException() {
        super("No user is logged in.");
    }
}
