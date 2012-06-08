package unSkooledEngine.text.designer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import unSkooledEngine.text.items.ItemType;

public class ItemPanel
    extends JPanel
{
    private JTextField itemName;
    private JTextField value;
    private JTextField id;


    /**
     * Create the panel.
     */
    public ItemPanel()
    {
        super();
        setLayout(null);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(18, 67, 37, 14);
        add(nameLabel);

        itemName = new JTextField();
        itemName.setBounds(18, 92, 86, 20);
        add(itemName);
        itemName.setColumns(10);

        JLabel valueLabel = new JLabel("Value");
        valueLabel.setBounds(114, 67, 37, 14);
        add(valueLabel);

        value = new JTextField();
        value.setBounds(114, 92, 86, 20);
        add(value);
        value.setColumns(10);

        JComboBox type = new JComboBox();
        type.setModel(new DefaultComboBoxModel(ItemType.values()));
        type.setBounds(114, 36, 74, 20);
        add(type);

        JLabel typeLabel = new JLabel("Item Type");
        typeLabel.setBounds(116, 11, 61, 14);
        add(typeLabel);

        JLabel idLabel = new JLabel("ID");
        idLabel.setBounds(18, 11, 46, 14);
        add(idLabel);

        id = new JTextField();
        id.setBounds(18, 36, 86, 20);
        add(id);
        id.setColumns(10);

    }
}
