package unSkooledEngine.text.designer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ItemsPanel
    extends JPanel
{

    /**
     * Create the panel.
     */
    public ItemsPanel()
    {
        super();
        setLayout(null);

        JLabel selectItemLabel = new JLabel("Select Item");
        selectItemLabel.setBounds(10, 11, 61, 14);
        add(selectItemLabel);

        JComboBox selectItem = new JComboBox();
        selectItem.setBounds(10, 36, 61, 20);
        add(selectItem);

        JButton addButton = new JButton("New Item");
        addButton.setBounds(81, 7, 89, 23);
        add(addButton);

    }
}
