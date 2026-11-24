package interface_adapter;

/**
 * Which screen is currently showing.
 *
 * <p>Its state is the name of the visible view. A use case that needs to move the
 * user elsewhere sets that name and fires the change; the view manager listens and
 * swaps the screen. No screen has to know about any other screen.</p>
 */
public class ViewManagerModel extends ViewModel<String> {

    public ViewManagerModel() {
        super("view manager");
    }
}
