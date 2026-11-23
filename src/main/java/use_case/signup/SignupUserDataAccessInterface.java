package use_case.signup;

import entity.User;

/**
 * What signing up needs from storage, and nothing more.
 *
 * <p>Each use case declares its own narrow interface, so that no use case is made to
 * depend on methods it never calls. One data access object may implement several of
 * these interfaces.</p>
 */
public interface SignupUserDataAccessInterface {

    /**
     * Checks whether an account with the given username exists.
     *
     * @param username the username to look for
     * @return true if an account already has that username
     */
    boolean existsByName(String username);

    /**
     * Checks whether an account with the given email address exists.
     *
     * @param email the email address to look for
     * @return true if an account already has that email address
     */
    boolean existsByEmail(String email);

    /**
     * Stores the given user, who must not already exist.
     *
     * @param user the user to store
     */
    void save(User user);
}
