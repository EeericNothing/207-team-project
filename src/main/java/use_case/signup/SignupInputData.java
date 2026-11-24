package use_case.signup;

/**
 * What the user filled into the sign-up form.
 */
public class SignupInputData {

    private final String username;
    private final String email;
    private final String password;
    private final String repeatPassword;

    /**
     * Creates the input for one attempt at signing up.
     *
     * @param username       the username the user chose
     * @param email          the email address the user gave
     * @param password       the password the user typed
     * @param repeatPassword the password the user typed a second time
     */
    public SignupInputData(String username, String email, String password, String repeatPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }
}
