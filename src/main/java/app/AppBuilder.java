package app;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryUserDataAccessObject;
import data_access.PlainTextPasswordHasher;
import data_access.SupabaseClient;
import data_access.SupabaseUserDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.CurrentUserDataAccessInterface;
import use_case.PasswordHasher;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupUserDataAccessInterface;
import view.LoggedInView;
import view.LoginView;
import view.SignupView;
import view.ViewManager;

/**
 * Assembles the application.
 *
 * <p>This is the <em>only</em> class in the project that is allowed to instantiate
 * concrete data access objects, interactors, presenters and views. Every other class
 * receives what it needs through its constructor and depends on interfaces only, which
 * is what keeps the Dependency Rule of Clean Architecture intact: swapping the
 * persistence implementation, or the way passwords are stored, is a one-line change
 * here and nowhere else.</p>
 */
public class AppBuilder {

    private static final String TITLE = "Group Calendar";
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private final UserFactory userFactory = new CommonUserFactory();
    private final PasswordHasher passwordHasher = new PlainTextPasswordHasher();

    /**
     * Where accounts live. One object serves all three use cases, because it
     * implements all three of their interfaces; which object it is, is decided here
     * and nowhere else.
     */
    private final SignupUserDataAccessInterface signupDataAccess;
    private final LoginUserDataAccessInterface loginDataAccess;
    private final LogoutUserDataAccessInterface logoutDataAccess;

    /**
     * The same object again, seen as the one question every other use case will ask of
     * it: who is logged in. A use case that needs the current user takes this.
     */
    private final CurrentUserDataAccessInterface currentUserDataAccess;

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private LoggedInView loggedInView;
    private LoggedInViewModel loggedInViewModel;

    /**
     * Chooses where accounts are stored and prepares the window.
     *
     * <p>Accounts go to Supabase when config.properties names a project. Without that
     * file the program runs against storage that lasts only as long as the program,
     * so a teammate who does not have the key can still work on their own screens.</p>
     */
    public AppBuilder() {
        final AppConfig config = AppConfig.load();
        if (config.hasSupabase()) {
            final SupabaseUserDataAccessObject supabase = new SupabaseUserDataAccessObject(
                    new SupabaseClient(config.getSupabaseUrl(), config.getSupabaseKey()), userFactory);
            signupDataAccess = supabase;
            loginDataAccess = supabase;
            logoutDataAccess = supabase;
            currentUserDataAccess = supabase;
        }
        else {
            final InMemoryUserDataAccessObject inMemory = new InMemoryUserDataAccessObject();
            signupDataAccess = inMemory;
            loginDataAccess = inMemory;
            logoutDataAccess = inMemory;
            currentUserDataAccess = inMemory;
        }

        new ViewManager(cardPanel, cardLayout, viewManagerModel);
    }

    /**
     * Adds the sign-up screen.
     *
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the log-in screen.
     *
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the screen a user reaches once logged in.
     *
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Wires the sign-up use case to its screen.
     *
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupPresenter =
                new SignupPresenter(signupViewModel, viewManagerModel, loginViewModel.getViewName());
        final SignupInputBoundary signupInteractor =
                new SignupInteractor(signupDataAccess, signupPresenter, userFactory, passwordHasher);
        signupView.setSignupController(new SignupController(signupInteractor));
        return this;
    }

    /**
     * Wires the log-in use case to its screen.
     *
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginPresenter =
                new LoginPresenter(loginViewModel, loggedInViewModel, viewManagerModel, signupViewModel.getViewName());
        final LoginInputBoundary loginInteractor =
                new LoginInteractor(loginDataAccess, loginPresenter, passwordHasher);
        loginView.setLoginController(new LoginController(loginInteractor));
        return this;
    }

    /**
     * Wires the log-out use case to its screen.
     *
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutPresenter =
                new LogoutPresenter(loggedInViewModel, loginViewModel, viewManagerModel);
        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(logoutDataAccess, logoutPresenter);
        loggedInView.setLogoutController(new LogoutController(logoutInteractor));
        return this;
    }

    /**
     * Creates the window that hosts every view.
     *
     * @return the application window, not yet visible
     */
    public JFrame build() {
        final JFrame application = new JFrame(TITLE);
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setSize(WIDTH, HEIGHT);
        application.setLocationRelativeTo(null);
        application.setLayout(new BorderLayout());
        application.add(cardPanel, BorderLayout.CENTER);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
