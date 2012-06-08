package unSkooledEngine.text;

// -------------------------------------------------------------------------
/**
 * NPC class that represents non player characters.
 * 
 * @author Sean Meacham (sean22)
 * @author Tim Eck (etimot2)
 * @author Nate Tucker (imtucker)
 * @version Apr 10, 2012
 */
public class NPC
    extends Character
{
    private float   npcHealth;
    private boolean canFight;
    private String  message;


    // ----------------------------------------------------------
    /**
     * Create a new NPC object.
     */
    public NPC()
    {
        npcHealth = 100f;
        message = null;
        canFight = false;
    }


    // ----------------------------------------------------------
    /**
     * Gets the amount of health the NPC has.
     * 
     * @return the npcHealth
     */
    public float getNPCHealth()
    {
        return npcHealth;
    }


    // ----------------------------------------------------------
    /**
     * Gets the amount of health the NPC has.
     * 
     * @param npcHealth
     *            the npcHealth to set
     */
    public void setNPCHealth(float npcHealth)
    {
        this.npcHealth = npcHealth;
    }


    // ----------------------------------------------------------
    /**
     * Gets the message that the NPC has.
     * 
     * @return message the message that the NPC has.
     */
    public String getMessage()
    {
        return message;
    }


    // ----------------------------------------------------------
    /**
     * Sets the message that the NPC has.
     * 
     * @param message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }


    // ----------------------------------------------------------
    /**
     * Determines if the NPC can fight
     * 
     * @return canFight true if the NPC can fight and false otherwise
     */
    public boolean willFight()
    {
        return canFight;
    }


    // ----------------------------------------------------------
    /**
     * Sets if this NPC will fight or not.
     * 
     * @param fight
     *            true to allow this npc to fight.
     */
    public void setFight(boolean fight)
    {
        canFight = fight;
    }


    // ----------------------------------------------------------
    /**
     * Determines if the NPC can talk
     * 
     * @return canTalk true if the NPC has a message and can talk, false
     *         otherwise
     */
    public boolean canTalk()
    {
        return message != null;
    }
}
