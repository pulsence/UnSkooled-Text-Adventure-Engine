package unSkooledEngine.text.actions;

import unSkooledEngine.text.NPC;
import unSkooledEngine.text.Room;

// -------------------------------------------------------------------------
/**
 * Talks to the npc character.
 *
 * @author Sean Meacham (sean22)
 * @author Tim Eck (etimot2)
 * @author Nate Tucker (imtucker)
 * @version 2012.04.29
 */
public class TalkAction
    extends BaseAction
{
    private NPC npc;


    // ----------------------------------------------------------
    /**
     * Create a new TalkAction object.
     *
     * @param npc
     *            the npc to talk to.
     */
    public TalkAction(NPC npc)
    {
        this.npc = npc;
    }


    // ----------------------------------------------------------
    @Override
    public String getDescription()
    {
        return "Get the message from " + npc.getCharacterName();
    }


    // ----------------------------------------------------------
    @Override
    public boolean applyAction(Room room)
    {
        room.setMessage(npc.getMessage());
        return false;
    }

    // ----------------------------------------------------------
    /**
     * Gets the NPC that this action will get the message from.
     * @return the npc associated with this action.
     */
    public NPC getNPC()
    {
        return npc;
    }

}
