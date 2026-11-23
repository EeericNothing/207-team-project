package use_case.logout;

/**
 * What logging out needs from storage, and nothing more.
 */
public interface LogoutUserDataAccessInterface {

    /**
     * Returns who is currently using the program.
     *
     * @return the current username, or null if nobody is logged in
     */
    String getCurrentUsername();

    /**
     * Records who is now using the program, or null for nobody.
     *
     * @param username the current username, or null
     */
    void setCurrentUsername(String username);
}
