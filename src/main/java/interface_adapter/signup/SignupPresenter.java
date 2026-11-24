package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

/**
 * Puts the outcome of signing up into the form the screens need.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final ViewManagerModel viewManagerModel;
    private final String loginViewName;

    /**
     * Creates the presenter.
     *
     * @param signupViewModel  the state of the sign-up screen
     * @param viewManagerModel which screen is showing
     * @param loginViewName    the name of the log-in screen to move to
     */
    public SignupPresenter(SignupViewModel signupViewModel,
                           ViewManagerModel viewManagerModel,
                           String loginViewName) {
        this.signupViewModel = signupViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewName = loginViewName;
    }

    @Override
    public void prepareSuccessView(SignupOutputData response) {
        final SignupState state = signupViewModel.getState();
        state.setErrorMessage(null);
        state.setUsername("");
        state.setEmail("");
        clearPasswords(state);
        signupViewModel.firePropertyChanged();

        switchToLoginView();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final SignupState state = signupViewModel.getState();
        state.setErrorMessage(errorMessage);
        clearPasswords(state);
        signupViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewName);
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Empties both password boxes, so that a password is never left on a screen the
     * user has stopped looking at.
     *
     * @param state the state to clear
     */
    private void clearPasswords(SignupState state) {
        state.setPassword("");
        state.setRepeatPassword("");
    }
}
