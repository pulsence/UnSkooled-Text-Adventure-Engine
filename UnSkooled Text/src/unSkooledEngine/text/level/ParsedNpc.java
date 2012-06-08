package unSkooledEngine.text.level;

import unSkooledEngine.text.NPC;

// -------------------------------------------------------------------------
/**
 *  Stores meta data about an npc parsed from xml.
 *
 *  @author Tim Eck II
 *  @version May 4, 2012
 */
class ParsedNPC
{
    /**
     * The npc.
     */
    NPC     npc;

    /**
     * True if the npc will be hidden to begin with. This is false by default.
     */
    boolean hidden;


    // ----------------------------------------------------------
    /**
     * Create a new ParsedNPC object.
     */
    public ParsedNPC()
    {
        npc = new NPC();
        hidden = false;
    }
}
