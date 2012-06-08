package unSkooledEngine.text;

import java.util.ArrayList;
import unSkooledEngine.text.items.BaseItem;

// -------------------------------------------------------------------------
/**
 * Data structure stores items.
 * 
 * @author Tim Eck II
 * @version May 4, 2012
 */
public class ItemContainer
{
    private ArrayList<BaseItem> storedItems;
    private String              name;


    // ----------------------------------------------------------
    /**
     * Create a new ItemContainer object.
     */
    public ItemContainer()
    {
        storedItems = new ArrayList<BaseItem>();
        name = "Item Storage";
    }


    // ----------------------------------------------------------
    /**
     * @return the storedItems
     */
    public ArrayList<BaseItem> getStoredItems()
    {
        return storedItems;
    }


    // ----------------------------------------------------------
    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }


    // ----------------------------------------------------------
    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
