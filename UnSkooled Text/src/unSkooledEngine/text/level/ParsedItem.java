package unSkooledEngine.text.level;

import unSkooledEngine.text.items.BaseItem;

// -------------------------------------------------------------------------
/**
 *  Meta data about an item parsed from xml.
 *
 *  @author Tim Eck II
 *  @version May 4, 2012
 */
class ParsedItem
{
    /**
     * The item.
     */
    BaseItem item;

    /**
     * Determines if the item should be stored instead of equipped. True by default.
     */
    boolean  stored;

    /**
     * The id of this item.
     */
    String   id;


    // ----------------------------------------------------------
    /**
     * Create a new ParsedItem object.
     */
    public ParsedItem()
    {
        item = null;
        stored = true;
        id = null;
    }
}
