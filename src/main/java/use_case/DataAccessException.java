package use_case;

/**
 * Thrown when stored data cannot be read or written.
 *
 * <p>It lives in the use case layer, not in the data access layer, so that an
 * interactor can catch it without depending on whichever storage threw it. Use Case 1
 * and Use Case 2 both say what the user is told when storage cannot be reached, and
 * this is how an interactor hears about it.</p>
 */
public class DataAccessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates the exception.
     *
     * @param message what went wrong
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * Creates the exception from an underlying failure.
     *
     * @param message what went wrong
     * @param cause   the failure underneath
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
