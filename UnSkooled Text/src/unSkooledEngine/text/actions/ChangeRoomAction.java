package unSkooledEngine.text.actions;

import unSkooledEngine.text.Room;

// -------------------------------------------------------------------------
/**
 * Tells the game to update the room that the main character is currently in.
 *
 * @author Sean Meacham (sean22)
 * @author Tim Eck (etimot2)
 * @author Nate Tucker (imtucker)
 * @version Apr 17, 2012
 */
public class ChangeRoomAction
    extends BaseAction
{
    private int    roomIndex;
    private String roomName;


    // ----------------------------------------------------------
    /**
     * Create a new ChangeRoomAction object.
     *
     * @param roomIndex
     *            of the room that this action will signal to move to.
     * @param roomName
     *            The name of the room that this action will change too.
     */
    public ChangeRoomAction(int roomIndex, String roomName)
    {
        if (roomIndex < 0)
        {
            this.roomIndex = 0;
        }
        else if (roomIndex > 3)
        {
            this.roomIndex = 3;
        }
        else
        {
            this.roomIndex = roomIndex;
        }
        this.roomName = roomName;
    }


    // ----------------------------------------------------------
    @Override
    public String getDescription()
    {
        return "Go to the " + roomName;
    }


    // ----------------------------------------------------------
    @Override
    public boolean applyAction(Room room)
    {
        room.changeRoom(roomIndex);
        return false;
    }
}
