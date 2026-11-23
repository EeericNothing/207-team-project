package interface_adapter.login;

/**
 * What the log-in screen currently shows.
 */
public class LoginState {

    private String username = "";
    private String password = "";
    private String errorMessage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns what went wrong on the last attempt.
     *
     * @return the message to show the user, or null if nothing has gone wrong
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
