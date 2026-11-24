package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * What one screen shows, and a way for that screen to hear about changes to it.
 *
 * <p>A view model holds the state of a screen and lets views subscribe to it. The
 * views observe; nothing in here knows that Swing exists, and nothing below this
 * layer knows that views exist.</p>
 *
 * @param <T> the type that holds this screen's state
 */
public abstract class ViewModel<T> {

    private final String viewName;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private T state;

    /**
     * Creates a view model for the screen with the given name.
     *
     * @param viewName the name the view manager uses to show this screen
     */
    protected ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
    }

    /**
     * Tells every listener that the state has changed.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    /**
     * Adds a listener that will hear about changes to the state.
     *
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
