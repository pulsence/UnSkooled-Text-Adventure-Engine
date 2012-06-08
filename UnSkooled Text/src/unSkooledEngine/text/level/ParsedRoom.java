package unSkooledEngine.text.level;

import unSkooledEngine.text.Room;

// -------------------------------------------------------------------------
/**
 * Stores meta data about a room that was parsed from xml.
 *
 * @author Tim Eck II
 * @version May 4, 2012
 */
class ParsedRoom
{
    /**
     * The room.
     */
    Room         room;

    /**
     * The id of the room.
     */
    String       id;

    /**
     * The ids of the rooms this room should be linked to.
     */
    ParsedLink[] linkedRooms;

    /**
     * Determines if this is the room that the player starts out in or not.
     */
    boolean      startRoom;


    // ----------------------------------------------------------
    /**
     * Create a new ParsedRoom object. By default startRoom is set false.
     */
    public ParsedRoom()
    {
        room = new Room();
        id = "";
        linkedRooms = new ParsedLink[4];
        startRoom = false;
    }
}
