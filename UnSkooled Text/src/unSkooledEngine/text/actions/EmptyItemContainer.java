package unSkooledEngine.text.actions;

import unSkooledEngine.text.ItemContainer;
import unSkooledEngine.text.Room;

// -------------------------------------------------------------------------
/**
 * Empties an items container.
 *
 * @author Tim Eck II
 * @version May 1, 2012
 */
public class EmptyItemContainer
    extends BaseAction
{

    private ItemContainer container;


    // ----------------------------------------------------------
    /**
     * Create a new EmptyItemContainer object.
     * @param container the container to empty.
     */
    public EmptyItemContainer(ItemContainer container)
    {
        this.container = container;
    }


    // ----------------------------------------------------------
    @Override
    public String getDescription()
    {
        return "Take the " + container.getStoredItems().size()
            + " item(s) out of " + container.getName();
    }


    // ----------------------------------------------------------
    @Override
    public boolean applyAction(Room room)
    {
        room.getMainCharacter().getStoredItems()
            .addAll(container.getStoredItems());
        return true;
    }

}
