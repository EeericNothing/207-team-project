package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * The entry point of the application.
 *
 * <p>This class deliberately contains no logic other than starting the Swing event
 * dispatch thread and handing control to {@link AppBuilder}. Every object graph is
 * assembled in {@code AppBuilder}, so that no layer has to know which concrete
 * implementations are in use.</p>
 */
public final class Main {

    private Main() {
    }

    /**
     * Builds and shows the application window.
     *
     * @param args command line arguments; none are used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final JFrame application = new AppBuilder()
                    .addSignupView()
                    .addLoginPlaceholderView()
                    .addSignupUseCase()
                    .build();
            application.setVisible(true);
        });
    }
}
