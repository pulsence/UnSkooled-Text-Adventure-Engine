package unSkooledEngine.text.level;

// -------------------------------------------------------------------------
/**
 *  Meta data about a link from one room to another.
 *
 *  @author Tim Eck II
 *  @version May 4, 2012
 */
class ParsedLink
{
    /**
     * The id of the other room to link to.
     */
    String  linkId;

    /**
     * True if the link should be hidden until explored. False by default.
     */
    boolean hidden;


    // ----------------------------------------------------------
    /**
     * Create a new ParsedLink object.
     */
    public ParsedLink()
    {
        linkId = null;
        hidden = false;
    }
}
