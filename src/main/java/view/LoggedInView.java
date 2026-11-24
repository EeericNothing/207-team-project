package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logout.LogoutController;

/**
 * The screen a user reaches once logged in.
 *
 * <p>For now it only says who is logged in and offers a way out. The calendar that
 * belongs here is Use Case 8, which is someone else's work; when it arrives it is
 * added to this panel and nothing else has to change.</p>
 */
public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

    private static final long serialVersionUID = 1L;

    private final String viewName;
    private final LoggedInViewModel loggedInViewModel;

    private final JLabel whoIsLoggedIn = new JLabel(" ", SwingConstants.CENTER);
    private final JButton logOutButton = new JButton(LoggedInViewModel.LOGOUT_BUTTON_LABEL);

    private LogoutController logoutController;

    /**
     * Builds the screen and starts listening to its view model.
     *
     * @param loggedInViewModel the state this screen shows
     */
    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewName = loggedInViewModel.getViewName();
        loggedInViewModel.addPropertyChangeListener(this);

        whoIsLoggedIn.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel comingSoon = new JLabel("The calendar goes here: Use Case 8.", SwingConstants.CENTER);
        comingSoon.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        buttons.add(logOutButton);
        logOutButton.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(whoIsLoggedIn);
        this.add(comingSoon);
        this.add(buttons);
    }

    /**
     * Gives this screen the controller it reports the user's actions to.
     *
     * @param controller the controller to use
     */
    public void setLogoutController(LogoutController controller) {
        this.logoutController = controller;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(logOutButton)) {
            logoutController.execute(loggedInViewModel.getState().getUsername());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoggedInState state = loggedInViewModel.getState();
        whoIsLoggedIn.setText("Logged in as " + state.getUsername());
    }
}
