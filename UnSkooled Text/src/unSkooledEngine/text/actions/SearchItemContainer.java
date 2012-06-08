package unSkooledEngine.text.actions;

import unSkooledEngine.text.ItemContainer;
import unSkooledEngine.text.Room;

// -------------------------------------------------------------------------
/**
 * Searches an items container.
 *
 * @author Tim Eck II
 * @version May 1, 2012
 */
public class SearchItemContainer
    extends BaseAction
{
    private ItemContainer container;


    // ----------------------------------------------------------
    /**
     * Create a new SearchItemContainer object.
     * @param container the container that this action will search.
     */
    public SearchItemContainer(ItemContainer container)
    {
        this.container = container;
    }


    // ----------------------------------------------------------
    @Override
    public String getDescription()
    {
        return "Search the " + container.getName();
    }


    // ----------------------------------------------------------
    @Override
    public boolean applyAction(Room room)
    {
        room.getCurrentActions().add(new EmptyItemContainer(container));
        return true;
    }

}
