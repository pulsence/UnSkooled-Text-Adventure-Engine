package unSkooledEngine.text.items;

import unSkooledEngine.text.Character;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * This is a simple armor item. If allows a character to better absorb damage.
 *
 * @author Sean Meacham (sean22)
 * @author Tim Eck (etimot2)
 * @author Nate Tucker (imtucker)
 * @version 2012.4.9
 */
public class SimpleArmor
    extends BaseItem
{
    /**
     * This value can be between 0 and 1 inclusive. The value represents the
     * percentage of damage that it stops.
     */
    private float armorValue;


    /**
     * Creates a SimpleArmor object.
     */
    public SimpleArmor()
    {
        super();
        setType(ItemType.Armor);
        armorValue = 0f;
    }


    // ----------------------------------------------------------
    /**
     * Gets the value of this armor. The value represents the percentage of
     * damage that it stops and is between 0 and 1 inclusive.
     *
     * @return the armorValue of this item.
     */
    public float getArmorValue()
    {
        return armorValue;
    }


    // ----------------------------------------------------------
    /**
     * Sets the armor value of this object. This value must be between 0 and 1
     * inclusive.
     *
     * @param armorValue
     *            the armorValue to set
     */
    public void setArmorValue(float armorValue)
    {
        if (armorValue < 0f)
        {
            this.armorValue = 0f;
        }
        else if (armorValue > 1f)
        {
            this.armorValue = 1f;
        }
        else
        {
            this.armorValue = armorValue;
        }
    }


    // ----------------------------------------------------------
    /**
     * Sets this armor as the character armor.
     *
     * @param character
     *            The character to add this armor to.
     * @return will always be false.
     */
    @Override
    public boolean applyItem(Character character)
    {

        ArrayList<BaseItem> equippedItems = character.getEquippedItems();
        ArrayList<BaseItem> storedItems = character.getStoredItems();

        if (equippedItems.contains(this))
        {
            equippedItems.remove(this);
            storedItems.add(this);
        }
        else
        {
            // Removes other pieces of armor that are equipped.
            for (int i = 0; i < equippedItems.size(); i++)
            {
                if (equippedItems.get(i) instanceof SimpleArmor)
                {
                    BaseItem item = equippedItems.get(i);
                    equippedItems.remove(item);
                    storedItems.add(item);
                    i--;
                }
            }

            equippedItems.add(this);
            storedItems.remove(this);

        }
        return false;
    }


    public SimpleArmor copy()
    {
        SimpleArmor item = new SimpleArmor();
        item.setDescription(getDescription());
        item.setName(getName());
        item.setType(getType());
        item.setArmorValue(getArmorValue());
        return item;
    }
}
