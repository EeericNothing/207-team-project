package use_case.logout;

/**
 * Who is logging out.
 */
public class LogoutInputData {

    private final String username;

    /**
     * Creates the input for one log-out.
     *
     * @param username the username of the user who is leaving
     */
    public LogoutInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
