package unSkooledEngine.text.level;

import java.util.Hashtable;
import unSkooledEngine.text.Character;
import unSkooledEngine.text.items.BaseItem;

// -------------------------------------------------------------------------
/**
 *  The fully parsed global resources. This holds the player and global items
 *  that can be referenced in the rooms.
 *
 *  @author Tim Eck II
 *  @version May 4, 2012
 */
class GlobalResources
{
    /**
     * The items that can be used in the rooms. The key is the id of the item.
     */
    Hashtable<String, BaseItem> items;

    /**
     * The main character for the game.
     */
    Character                 mainCharacter;


    // ----------------------------------------------------------
    /**
     * Create a new GlobalResources object.
     */
    public GlobalResources()
    {
        items = new Hashtable<String, BaseItem>();
        mainCharacter = new Character();
    }
}
