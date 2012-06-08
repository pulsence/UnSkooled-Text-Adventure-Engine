package unSkooledEngine.text.level;

import unSkooledEngine.text.ItemContainer;

// -------------------------------------------------------------------------
/**
 *  Meta data about a parsed item container.
 *
 *  @author Tim Eck II
 *  @version May 4, 2012
 */
class ParsedContainer
{
    /**
     * The item container.
     */
    ItemContainer container;

    /**
     * Determines if the container is originally hidden. False by default.
     */
    boolean       hidden;


    // ----------------------------------------------------------
    /**
     * Create a new ParsedContainer object.
     */
    public ParsedContainer()
    {
        container = new ItemContainer();
        hidden = false;
    }
}
