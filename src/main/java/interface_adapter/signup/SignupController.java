package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

/**
 * Turns what the user did on the sign-up screen into a call on the use case.
 */
public class SignupController {

    private final SignupInputBoundary userSignupUseCaseInteractor;

    /**
     * Creates the controller.
     *
     * @param userSignupUseCaseInteractor the use case to drive
     */
    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

    /**
     * Asks for an account to be created from what the user filled in.
     *
     * @param username       the username the user chose
     * @param email          the email address the user gave
     * @param password       the password the user typed
     * @param repeatPassword the password the user typed a second time
     */
    public void execute(String username, String email, String password, String repeatPassword) {
        userSignupUseCaseInteractor.execute(new SignupInputData(username, email, password, repeatPassword));
    }

    /**
     * Asks to leave sign-up for the log-in screen.
     */
    public void switchToLoginView() {
        userSignupUseCaseInteractor.switchToLoginView();
    }
}
