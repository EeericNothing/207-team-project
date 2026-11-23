package use_case.signup;

/**
 * What the sign-up use case reports back when an account has been created.
 */
public class SignupOutputData {

    private final String username;

    /**
     * Creates the result of a successful sign-up.
     *
     * @param username the username of the account that was created
     */
    public SignupOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
