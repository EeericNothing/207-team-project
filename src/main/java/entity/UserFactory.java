package entity;

/**
 * Creates users.
 *
 * <p>Code that needs a user asks a factory for one instead of calling the constructor
 * itself. That keeps one awkward detail in a single place: {@link User}'s constructor
 * takes the password before the email, so calling it directly invites two strings
 * being swapped silently. It also means the empty list of events a new user starts
 * with is built here rather than at every call site.</p>
 *
 * <p>There are two ways to create a user because an account has two states. An account
 * the sign-up use case has just validated does not have an identifier yet: the database
 * assigns that when the row is written. An account read back out of storage does have
 * one.</p>
 */
public interface UserFactory {

    /**
     * Creates a user that has not been stored yet.
     *
     * <p>The identifier is left at {@link CommonUserFactory#UNSAVED_ID} because only the
     * database can assign a real one, and it does so when the row is inserted.</p>
     *
     * @param username the username, which identifies the user
     * @param email    the address the user can be reached at
     * @param password the password, as it is to be stored
     * @return the new user
     */
    User create(String username, String email, String password);

    /**
     * Creates a user from an account that is already stored.
     *
     * @param id       the identifier the database assigned to this account
     * @param username the username, which identifies the user
     * @param email    the address the user can be reached at
     * @param password the password, as it is stored
     * @return the stored user
     */
    User createStored(int id, String username, String email, String password);
}
