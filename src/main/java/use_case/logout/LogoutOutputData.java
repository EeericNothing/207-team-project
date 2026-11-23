package use_case.logout;

/**
 * What the log-out use case reports back.
 */
public class LogoutOutputData {

    private final String username;

    /**
     * Creates the result of a log-out.
     *
     * @param username the username of the user who left
     */
    public LogoutOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
