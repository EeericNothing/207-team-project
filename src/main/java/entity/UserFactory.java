package entity;

/**
 * Creates users.
 *
 * <p>Code that needs a new user asks a factory for one instead of naming a concrete
 * class, so that a different kind of user can be introduced later without changing
 * the code that creates them.</p>
 */
public interface UserFactory {

    /**
     * Creates a new user.
     *
     * @param name     the username, which identifies the user
     * @param email    the address the user can be reached at
     * @param password the password, as it is to be stored
     * @return the new user
     */
    User create(String name, String email, String password);
}
