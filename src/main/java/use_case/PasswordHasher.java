package use_case;

/**
 * Turns a password the user typed into the form in which it is stored.
 *
 * <p>The use cases depend on this interface rather than on a particular way of
 * protecting passwords, so moving from storing them as typed to storing a hash of
 * them is a matter of supplying a different implementation. No interactor, test or
 * data access object changes.</p>
 */
public interface PasswordHasher {

    /**
     * Returns the form in which the given password is to be stored and compared.
     *
     * @param rawPassword the password as the user typed it
     * @return the stored form of that password
     */
    String hash(String rawPassword);
}
