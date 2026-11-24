package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

/**
 * Turns what the user did on the log-in screen into a call on the use case.
 */
public class LoginController {

    private final LoginInputBoundary loginUseCaseInteractor;

    /**
     * Creates the controller.
     *
     * @param loginUseCaseInteractor the use case to drive
     */
    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    /**
     * Asks for the user to be logged in with what they filled in.
     *
     * @param username the username the user gave
     * @param password the password the user typed
     */
    public void execute(String username, String password) {
        loginUseCaseInteractor.execute(new LoginInputData(username, password));
    }

    /**
     * Asks to leave log-in for the sign-up screen.
     */
    public void switchToSignupView() {
        loginUseCaseInteractor.switchToSignupView();
    }
}
