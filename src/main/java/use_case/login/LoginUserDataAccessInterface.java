package use_case.login;

import entity.User;

/**
 * What logging in needs from storage, and nothing more.
 */
public interface LoginUserDataAccessInterface {

    /**
     * Returns the stored account with the given username.
     *
     * @param username the username to look up
     * @return the user, or null if no account has that username
     */
    User get(String username);

    /**
     * Records who is now using the program.
     *
     * <p>The whole account is recorded rather than its username, because logging in has
     * just read it out of storage and the use cases that follow need the identifier it
     * carries. Passing the username alone would throw that away and make every later
     * use case ask storage for it again.</p>
     *
     * @param user the user who logged in
     */
    void setCurrentUser(User user);
}
