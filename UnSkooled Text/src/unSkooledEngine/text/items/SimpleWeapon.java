package unSkooledEngine.text.items;

import unSkooledEngine.text.Character;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * This is a simple weapon item. When applied to a character it is armed as the
 * character main weapon.
 *
 * @author Sean Meacham (sean22)
 * @author Tim Eck (etimot2)
 * @author Nate Tucker (imtucker)
 * @version 2012.4.9
 */
public class SimpleWeapon
    extends BaseItem
{
    private float damage;


    /**
     * Creates a SimpleWeapon object.
     */
    public SimpleWeapon()
    {
        super();
        setType(ItemType.Weapon);
        damage = 0f;
    }


    // ----------------------------------------------------------
    /**
     * Gets the damage of this weapon.
     *
     * @return the damage of this weapon.
     */
    public float getDamage()
    {
        return damage;
    }


    // ----------------------------------------------------------
    /**
     * Sets this weapons damage.
     *
     * @param damage
     *            the damage that this weapon will do.
     */
    public void setDamage(float damage)
    {
        if (damage < 0)
        {
            this.damage = 0;
        }
        else
        {
            this.damage = damage;
        }
    }


    // ----------------------------------------------------------
    /**
     * Equips the character with this weapon,
     *
     * @param character
     *            The character to give this weapon to.
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
            // Removes other weapons that are equipped.
            for (int i = 0; i < equippedItems.size(); i++)
            {
                if(equippedItems.get(i) instanceof SimpleWeapon)
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


    public SimpleWeapon copy()
    {
        SimpleWeapon item = new SimpleWeapon();
        item.setDescription(getDescription());
        item.setName(getName());
        item.setType(getType());
        item.setDamage(getDamage());
        return item;
    }

}
