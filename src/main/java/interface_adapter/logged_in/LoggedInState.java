package interface_adapter.logged_in;

/**
 * What the screen behind the log-in shows.
 */
public class LoggedInState {

    private String username = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
