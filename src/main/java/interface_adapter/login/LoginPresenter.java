package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * Puts the outcome of logging in into the form the screens need.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;
    private final String signupViewName;

    /**
     * Creates the presenter.
     *
     * @param loginViewModel    the state of the log-in screen
     * @param loggedInViewModel the state of the screen reached once logged in
     * @param viewManagerModel  which screen is showing
     * @param signupViewName    the name of the sign-up screen to move to
     */
    public LoginPresenter(LoginViewModel loginViewModel,
                          LoggedInViewModel loggedInViewModel,
                          ViewManagerModel viewManagerModel,
                          String signupViewName) {
        this.loginViewModel = loginViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.signupViewName = signupViewName;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername(response.getUsername());
        loggedInViewModel.firePropertyChanged();

        final LoginState loginState = loginViewModel.getState();
        loginState.setErrorMessage(null);
        loginState.setUsername("");
        loginState.setPassword("");
        loginViewModel.firePropertyChanged();

        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setErrorMessage(errorMessage);
        loginState.setPassword("");
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSignupView() {
        viewManagerModel.setState(signupViewName);
        viewManagerModel.firePropertyChanged();
    }
}
