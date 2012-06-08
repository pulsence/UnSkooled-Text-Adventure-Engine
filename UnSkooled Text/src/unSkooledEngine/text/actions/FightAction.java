package unSkooledEngine.text.actions;

import unSkooledEngine.text.Character;
import unSkooledEngine.text.NPC;
import unSkooledEngine.text.Room;
import unSkooledEngine.text.items.BaseItem;
import unSkooledEngine.text.items.SimpleArmor;
import unSkooledEngine.text.items.SimpleWeapon;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * Simple action class that allows the user to fight an enemy.
 *
 * @author Sean Meacham (sean22)
 * @author Tim Eck (etimot2)
 * @author Nate Tucker (imtucker)
 * @version Apr 16, 2012
 */
public class FightAction
    extends BaseAction
{
    private NPC enemy;


    // ----------------------------------------------------------
    /**
     * Create a new FightAction object.
     *
     * @param enemy
     *            The enemy that this action will fight against.
     * @precondition the enemy must be able to fight.
     */
    public FightAction(NPC enemy)
    {
        assert enemy.willFight();
        this.enemy = enemy;
    }


    /**
     * Gets the description of this room.
     *
     * @return the rooms descriptions.
     */
    @Override
    public String getDescription()
    {
        String description =
            "Fight " + enemy.getCharacterName() + ", he has "
                + enemy.getCharacterHealth() + " health points left";

        return description;
    }


    /**
     * Applies this action to the passed room. This actions has the enemy
     * specified to this action fight the main character in the room.
     *
     * @param room
     *            the room to apply this action too.
     * @return true is the enemy is killed.
     */
    @Override
    public boolean applyAction(Room room)
    {
        Character mainCharacter = room.getMainCharacter();

        // Gets the characters weapon and armor.
        ArrayList<BaseItem> mainCharacterItems =
            mainCharacter.getEquippedItems();
        SimpleWeapon mainCharacterWeapon = new SimpleWeapon();
        SimpleArmor mainCharacterArmor = new SimpleArmor();
        for (BaseItem item : mainCharacterItems)
        {
            if (item instanceof SimpleWeapon)
            {
                mainCharacterWeapon = (SimpleWeapon)item;
            }
            else if (item instanceof SimpleArmor)
            {
                mainCharacterArmor = (SimpleArmor)item;
            }
        }

        // Gets the enemies weapon and armor.
        ArrayList<BaseItem> enemyItems = enemy.getEquippedItems();
        SimpleWeapon enemyWeapon = new SimpleWeapon();
        SimpleArmor enemyArmor = new SimpleArmor();
        for (BaseItem item : enemyItems)
        {
            if (item instanceof SimpleWeapon)
            {
                enemyWeapon = (SimpleWeapon)item;
            }
            else if (item instanceof SimpleArmor)
            {
                enemyArmor = (SimpleArmor)item;
            }
        }

        // Damages both characters.
        float damage =
            (1 - enemyArmor.getArmorValue()) * mainCharacterWeapon.getDamage();
        enemy.setCharacterHealth(enemy.getCharacterHealth() - damage);

        damage =
            (1 - mainCharacterArmor.getArmorValue()) * enemyWeapon.getDamage();
        mainCharacter.setCharacterHealth(mainCharacter.getCharacterHealth()
            - damage);

        if (enemy.getCharacterHealth() <= 0)
        {
            room.getMainCharacter().getStoredItems()
                .addAll(enemy.getEquippedItems());
            room.getMainCharacter().getStoredItems()
                .addAll(enemy.getStoredItems());

            for(BaseAction action : room.getCurrentActions())
            {
                if(action instanceof TalkAction)
                {
                    if(((TalkAction)action).getNPC() == enemy)
                    {
                        room.getCurrentActions().remove(action);
                        break;
                    }
                }
            }

            return true;
        }
        return false;
    }
}
