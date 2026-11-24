package view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A label with its input box beside it.
 */
public class LabelTextPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Puts the given label and field side by side.
     *
     * @param label     the label to show
     * @param textField the box the user types in
     */
    public LabelTextPanel(JLabel label, JTextField textField) {
        this.add(label);
        this.add(textField);
    }
}
