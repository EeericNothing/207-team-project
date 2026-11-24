package use_case.login;

/**
 * What the log-in use case reports back when someone has logged in.
 */
public class LoginOutputData {

    private final String username;

    /**
     * Creates the result of a successful log-in.
     *
     * @param username the username of the user who logged in
     */
    public LoginOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
