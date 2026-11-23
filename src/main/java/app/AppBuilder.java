package app;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import data_access.InMemoryUserDataAccessObject;
import data_access.PlainTextPasswordHasher;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.PasswordHasher;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
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

    /** The name of the log-in screen. Only a placeholder stands there for now. */
    private static final String LOGIN_VIEW_NAME = "log in";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private final UserFactory userFactory = new CommonUserFactory();
    private final PasswordHasher passwordHasher = new PlainTextPasswordHasher();
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();

    private SignupView signupView;
    private SignupViewModel signupViewModel;

    // TODO(Use Case 2): replace the placeholder with the real LoginView and its use case
    // TODO(Use Case 3): add the logout use case
    // TODO(persistence): pass a Supabase data access object in place of the in-memory one

    public AppBuilder() {
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
     * Adds a stand-in for the log-in screen, so that leaving sign-up already goes
     * somewhere while Use Case 2 is still being written.
     *
     * @return this builder
     */
    public AppBuilder addLoginPlaceholderView() {
        final JPanel placeholder = new JPanel(new BorderLayout());
        placeholder.add(new JLabel("The log-in screen is Use Case 2.", SwingConstants.CENTER),
                BorderLayout.CENTER);
        cardPanel.add(placeholder, LOGIN_VIEW_NAME);
        return this;
    }

    /**
     * Wires the sign-up use case to its screen.
     *
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupPresenter =
                new SignupPresenter(signupViewModel, viewManagerModel, LOGIN_VIEW_NAME);
        final SignupInputBoundary signupInteractor =
                new SignupInteractor(userDataAccessObject, signupPresenter, userFactory, passwordHasher);
        signupView.setSignupController(new SignupController(signupInteractor));
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
