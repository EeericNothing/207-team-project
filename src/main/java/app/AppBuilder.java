package app;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * Assembles the application.
 *
 * <p>This is the <em>only</em> class in the project that is allowed to instantiate
 * concrete data access objects, interactors, presenters and views. Every other class
 * receives what it needs through its constructor and depends on interfaces only, which
 * is what keeps the Dependency Rule of Clean Architecture intact: swapping the
 * persistence implementation, for instance, is a one-line change here and nowhere
 * else.</p>
 *
 * <p>Views are shown inside a single {@link CardLayout} panel, so a use case can switch
 * the visible screen by name without knowing anything about the other screens.</p>
 */
public class AppBuilder {

    private static final String TITLE = "Group Calendar";
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    // TODO(Phase 1): addSignupView() and addSignupUseCase()
    // TODO(Phase 2): addLoginView(), addLoginUseCase(), addLoggedInView()
    // TODO(Phase 3): accept a UserDataAccessInterface and inject it into the interactors

    public AppBuilder() {
        cardPanel.add(buildPlaceholderView(), "placeholder");
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
        return application;
    }

    /**
     * A temporary screen shown while no real view has been added yet.
     *
     * <p>Delete this method as soon as the signup and login views exist.</p>
     *
     * @return a panel with a single explanatory label
     */
    private JPanel buildPlaceholderView() {
        final JPanel panel = new JPanel(new BorderLayout());
        final JLabel label = new JLabel(
                "Skeleton is running. Views are registered in AppBuilder.", SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
