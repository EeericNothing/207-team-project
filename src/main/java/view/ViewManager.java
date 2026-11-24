package view;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import interface_adapter.ViewManagerModel;

/**
 * Shows whichever screen the view manager model names.
 *
 * <p>This is the only place that knows every screen by name. A use case moves the
 * user by setting a name on the model, so no screen ever has to reach for another
 * screen directly.</p>
 */
public class ViewManager implements PropertyChangeListener {

    private final CardLayout cardLayout;
    private final JPanel views;

    /**
     * Starts listening for changes to which screen should be showing.
     *
     * @param views            the panel that holds every screen
     * @param cardLayout       the layout that swaps them
     * @param viewManagerModel the model that says which one to show
     */
    public ViewManager(JPanel views, CardLayout cardLayout, ViewManagerModel viewManagerModel) {
        this.views = views;
        this.cardLayout = cardLayout;
        viewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final String viewToShow = (String) evt.getNewValue();
        cardLayout.show(views, viewToShow);
    }
}
