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
     * @param username the username of the user who logged in
     */
    void setCurrentUsername(String username);
}
