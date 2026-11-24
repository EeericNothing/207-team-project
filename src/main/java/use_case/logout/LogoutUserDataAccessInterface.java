package use_case.logout;

import entity.User;

/**
 * What logging out needs from storage, and nothing more.
 *
 * <p>That turns out to be a single method. Logging out is told whose session is ending
 * by the screen that asked for it, so it never has to look the current user up: all it
 * does is end the session.</p>
 */
public interface LogoutUserDataAccessInterface {

    /**
     * Records who is now using the program, or null for nobody.
     *
     * @param user the current user, or null
     */
    void setCurrentUser(User user);
}
