package use_case.login;

/**
 * What the user filled into the log-in form.
 */
public class LoginInputData {

    private final String username;
    private final String password;

    /**
     * Creates the input for one attempt at logging in.
     *
     * @param username the username the user gave
     * @param password the password the user typed
     */
    public LoginInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
