package unSkooledEngine.text.level;

// -------------------------------------------------------------------------
/**
 * Thrown when ever there was an error loading a level from xml.
 *
 * @author Tim Eck II
 * @version Apr 30, 2012
 */
public class XmlLevelParseException
    extends RuntimeException
{

    // ----------------------------------------------------------
    /**
     * Create a new XmlLevelParseException object.
     *
     * @param msg
     *            the message to pass for this exception.
     */
    public XmlLevelParseException(String msg)
    {
        super(msg);
    }
}
