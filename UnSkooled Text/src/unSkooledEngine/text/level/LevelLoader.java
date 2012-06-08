package unSkooledEngine.text.level;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observer;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import unSkooledEngine.text.Character;
import unSkooledEngine.text.Room;
import unSkooledEngine.text.actions.ChangeRoomAction;
import unSkooledEngine.text.actions.FightAction;
import unSkooledEngine.text.actions.SearchAction;
import unSkooledEngine.text.actions.SearchItemContainer;
import unSkooledEngine.text.actions.TalkAction;
import unSkooledEngine.text.items.HealthPotion;
import unSkooledEngine.text.items.SimpleArmor;
import unSkooledEngine.text.items.SimpleWeapon;

/**
 * Has a method to load a level from xml and then returns the room that is the
 * starting room.
 *
 * @author Tim Eck II
 * @version May 4, 2012
 */
public class LevelLoader
{
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     *
     * @param fileStream
     *            Inputstream for the xml to parse
     * @param roomObserver
     *            the observer for the rooms
     * @return the first room that the main character starts out at.
     * @throws XmlLevelParseException
     */
    public Room loadRoom(InputStream fileStream, Observer roomObserver)
        throws XmlLevelParseException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(fileStream);
            Node root = null;
            // Gets the root level node
            for (int i = 0; i < dom.getChildNodes().getLength(); i++)
            {
                Node node = dom.getChildNodes().item(i);
                if (node.getNodeName().equals("level"))
                {
                    root = node;
                }
            }
            if (root == null)
            {
                throw new XmlLevelParseException(
                    "The first node has to be a level node.");
            }

            NodeList children = root.getChildNodes();
            if (children == null)
            {
                throw new XmlLevelParseException(
                    "No tags where found inside of the level tag.");
            }

            LinkedList<Node> rawRooms = new LinkedList<Node>();
            Node global = null;
            Node child;
            for (int i = 0; i < children.getLength(); i++)
            {
                child = children.item(i);
                if (child.getNodeType() == Node.TEXT_NODE)
                {
                    continue;
                }
                if (child.getNodeName().equals("global"))
                {
                    global = child;
                }
                else
                {
                    rawRooms.add(child);
                }
            }

            // Parses the global resources
            GlobalResources globalRes = new GlobalResources();
            if (global != null)
            {
                globalRes = parseGlobal(global);
            }

            return parseRooms(rawRooms, globalRes, roomObserver);
        }
        catch (ParserConfigurationException e)
        {
            throw new XmlLevelParseException(
                "The DOM for this xml file can be prepared.");
        }
        catch (SAXException e)
        {
            throw new XmlLevelParseException(
                "The file could not be parsed into a DOM.");
        }
        catch (IOException e)
        {
            throw new XmlLevelParseException(
                "There was an error reading from the file.");
        }
    }


    private GlobalResources parseGlobal(Node global)
    {
        GlobalResources globalRes = new GlobalResources();
        NodeList children = global.getChildNodes();
        Node child;
        Node character = null;
        Node items = null;
        for (int i = 0; i < children.getLength(); i++)
        {
            child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            if (child.getNodeName().equals("player"))
            {
                character = child;
            }
            else if (child.getNodeName().equals("items"))
            {
                items = child;
            }
        }

        // Loads items
        if (items != null)
        {
            children = items.getChildNodes();
            for (int i = 0; i < children.getLength(); i++)
            {
                child = children.item(i);
                if (child.getNodeType() == Node.TEXT_NODE)
                {
                    continue;
                }
                if (!child.getNodeName().equals("item"))
                {
                    continue;
                }

                ParsedItem item = parseItem(child, globalRes);
                if (item.id == null)
                {
                    continue;
                }
                globalRes.items.put(item.id, item.item);
            }
        }

        // Loads main character
        if (character == null)
        {
            globalRes.mainCharacter.setCharacterHealth(100);
            globalRes.mainCharacter.setCharacterName("Main Character");
        }
        else
        {
            children = character.getChildNodes();
            for (int i = 0; i < children.getLength(); i++)
            {
                child = children.item(i);
                if (child.getNodeType() == Node.TEXT_NODE)
                {
                    continue;
                }
                if (child.getNodeName().equals("items"))
                {
                    parseItems(child, globalRes, globalRes.mainCharacter);
                }
                else if (child.getNodeName().equals("name"))
                {
                    globalRes.mainCharacter.setCharacterName(child
                        .getTextContent().trim());
                }
                else if (child.getNodeName().equals("health"))
                {
                    String health = child.getTextContent().trim();
                    globalRes.mainCharacter.setCharacterHealth(Float
                        .parseFloat(health));
                }
            }
        }

        return globalRes;
    }


    private void parseItems(
        Node rawItems,
        GlobalResources global,
        Character character)
    {
        NodeList children = rawItems.getChildNodes();
        Node child;
        for (int i = 0; i < children.getLength(); i++)
        {
            child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            ParsedItem item = parseItem(child, global);
            if (item.item == null)
            {
                continue;
            }
            if (item.stored)
            {
                character.getStoredItems().add(item.item);
            }
            else
            {
                character.getEquippedItems().add(item.item);
            }
        }
    }


    private ParsedItem parseItem(Node rawItem, GlobalResources global)
    {
        ParsedItem parsedItem = new ParsedItem();
        HashMap<String, String> itemFields = new HashMap<String, String>();
        NodeList children = rawItem.getChildNodes();
        Node child;
        for (int i = 0; i < children.getLength(); i++)
        {
            child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            if (child.getNodeName().equals("globalId"))
            {
                parsedItem.item =
                    global.items.get(child.getTextContent().trim()).copy();
            }
            else if (child.getNodeName().equals("stored"))
            {
                parsedItem.stored =
                    child.getTextContent().toLowerCase().trim().equals("true");
            }
            else if (child.getNodeName().equals("id"))
            {
                parsedItem.id = child.getTextContent().trim();
            }
            itemFields.put(child.getNodeName(), child.getTextContent().trim());
        }

        if (parsedItem.item != null)
        {
            return parsedItem;
        }

        String itemType = itemFields.get("type");
        if (itemType.equals("potion"))
        {
            HealthPotion item = new HealthPotion();
            if (itemFields.containsKey("description"))
            {
                item.setDescription(itemFields.get("description"));
            }
            if (itemFields.containsKey("name"))
            {
                item.setName(itemFields.get("name"));
            }
            if (itemFields.containsKey("value"))
            {
                item.setHealAmount(Float.parseFloat(itemFields.get("value")));
            }
            parsedItem.item = item;
        }
        else if (itemType.equals("weapon"))
        {
            SimpleWeapon item = new SimpleWeapon();
            if (itemFields.containsKey("description"))
            {
                item.setDescription(itemFields.get("description"));
            }
            if (itemFields.containsKey("name"))
            {
                item.setName(itemFields.get("name"));
            }
            if (itemFields.containsKey("value"))
            {
                item.setDamage(Float.parseFloat(itemFields.get("value")));
            }
            parsedItem.item = item;
        }
        else if (itemType.equals("armor"))
        {
            SimpleArmor item = new SimpleArmor();
            if (itemFields.containsKey("description"))
            {
                item.setDescription(itemFields.get("description"));
            }
            if (itemFields.containsKey("name"))
            {
                item.setName(itemFields.get("name"));
            }
            if (itemFields.containsKey("value"))
            {
                item.setArmorValue(Float.parseFloat(itemFields.get("value")));
            }
            parsedItem.item = item;
        }

        return parsedItem;
    }


    private Room parseRooms(
        LinkedList<Node> rawRooms,
        GlobalResources global,
        Observer roomObserver)
    {
        Room firstRoom = null;
        HashMap<String, ParsedRoom> rooms = new HashMap<String, ParsedRoom>();
        ParsedRoom parsedRoom = null;
        // Loads the rooms into hash
        for (Node child : rawRooms)
        {
            parsedRoom = parseRoom(child, global, roomObserver);
            rooms.put(parsedRoom.id, parsedRoom);

            if (parsedRoom.startRoom)
            {
                firstRoom = parsedRoom.room;
            }
        }

        // Links the rooms together
        Set<String> keys = rooms.keySet();
        for (String key : keys)
        {
            ParsedRoom room = rooms.get(key);
            Room[] linkedRooms = room.room.getLinkedRooms();
            // Loops over the parsed links
            for (int i = 0; i < linkedRooms.length; i++)
            {
                if (room.linkedRooms[i] == null)
                {
                    continue;
                }
                linkedRooms[i] = rooms.get(room.linkedRooms[i].linkId).room;

                ChangeRoomAction action =
                    new ChangeRoomAction(i, linkedRooms[i].getName());
                if (room.linkedRooms[i].hidden)
                {
                    room.room.getHiddenActions().add(action);
                }
                else
                {
                    room.room.getCurrentActions().add(action);
                }
            }
            room.room.setLinkedRooms(linkedRooms);

            if (room.room.getHiddenActions().size() > 0)
            {
                room.room.getCurrentActions().add(new SearchAction());
            }
        }

        if (rooms.size() == 0)
        {
            throw new XmlLevelParseException(
                "No rooms were found in the xml file.");
        }
        if (firstRoom == null)
        {
            firstRoom = parsedRoom.room;
        }
        firstRoom.setMainCharacter(global.mainCharacter);
        return firstRoom;
    }


    private ParsedRoom parseRoom(
        Node rawRoom,
        GlobalResources global,
        Observer roomObserver)
    {
        ParsedRoom room = new ParsedRoom();
        room.room = new Room();
        room.room.addObserver(roomObserver);
        NodeList children = rawRoom.getChildNodes();
        Node child;
        for (int i = 0; i < children.getLength(); i++)
        {
            child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            if (child.getNodeName().equals("id"))
            {
                room.id = child.getTextContent().trim();
            }
            else if (child.getNodeName().equals("npc"))
            {
                ParsedNPC parsedNPC = parseNPC(child, global);
                room.room.getNpcs().add(parsedNPC.npc);

                if (parsedNPC.npc.willFight())
                {
                    FightAction action = new FightAction(parsedNPC.npc);
                    if (parsedNPC.hidden)
                    {
                        room.room.getHiddenActions().add(action);
                    }
                    else
                    {
                        room.room.getCurrentActions().add(action);
                    }
                }

                if (parsedNPC.npc.canTalk())
                {

                    TalkAction action = new TalkAction(parsedNPC.npc);
                    if (parsedNPC.hidden)
                    {
                        room.room.getHiddenActions().add(action);
                    }
                    else
                    {
                        room.room.getCurrentActions().add(action);
                    }
                }
            }
            else if (child.getNodeName().equals("rooms"))
            {
                room.linkedRooms = getRoomLinkIds(child);
            }
            else if (child.getNodeName().equals("startRoom"))
            {
                room.startRoom =
                    child.getTextContent().toLowerCase().trim().equals("true");
            }
            else if (child.getNodeName().equals("name"))
            {
                room.room.setName(child.getTextContent().trim());
            }
            else if (child.getNodeName().equals("container"))
            {
                ParsedContainer container = parseItemContainer(child, global);
                room.room.getItemContainers().add(container.container);
                SearchItemContainer action =
                    new SearchItemContainer(container.container);
                if (container.hidden)
                {
                    room.room.getHiddenActions().add(action);
                }
                else
                {
                    room.room.getCurrentActions().add(action);
                }
            }
        }

        if (room.room.getName().equals("Room"))
        {
            room.room.setName(room.id);
        }

        return room;
    }


    private ParsedLink[] getRoomLinkIds(Node rawLinks)
    {
        ParsedLink[] links = new ParsedLink[4];
        int index = 0;
        NodeList children = rawLinks.getChildNodes();
        Node child;
        for (int i = 0; i < children.getLength(); i++)
        {
            child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            if (child.getNodeName().equals("link"))
            {
                ParsedLink link = new ParsedLink();
                NodeList linkChildren = child.getChildNodes();
                Node linkChild;
                for (int j = 0; j < linkChildren.getLength(); j++)
                {
                    linkChild = linkChildren.item(j);
                    if (linkChild.getNodeType() == Node.TEXT_NODE)
                    {
                        continue;
                    }

                    if (linkChild.getNodeName().equals("id"))
                    {
                        link.linkId = linkChild.getTextContent().trim();
                    }
                    else if (linkChild.getNodeName().equals("hidden"))
                    {
                        link.hidden =
                            linkChild.getTextContent().trim().toLowerCase()
                                .equals("true");
                    }
                }

                if (link.linkId == null)
                {
                    continue;
                }
                links[index] = link;
                index++;
            }
            if (index == 4)
            {
                break;
            }
        }
        return links;
    }


    private ParsedNPC parseNPC(Node rawNPC, GlobalResources global)
    {
        ParsedNPC npc = new ParsedNPC();
        NodeList children = rawNPC.getChildNodes();
        Node child;
        for (int i = 0; i < children.getLength(); i++)
        {
            child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            if (child.getNodeName().equals("health"))
            {
                npc.npc.setCharacterHealth(Float.parseFloat(child
                    .getTextContent().trim()));
            }
            else if (child.getNodeName().equals("name"))
            {
                npc.npc.setCharacterName(child.getTextContent().trim());
            }
            else if (child.getNodeName().equals("message"))
            {
                npc.npc.setMessage(child.getTextContent().trim());
            }
            else if (child.getNodeName().equals("hidden"))
            {
                npc.hidden =
                    child.getTextContent().trim().toLowerCase().equals("true");
            }
            else if (child.getNodeName().equals("items"))
            {
                parseItems(child, global, npc.npc);
            }
            else if (child.getNodeName().equals("canFight"))
            {
                npc.npc.setFight(child.getTextContent().trim().toLowerCase()
                    .equals("true"));
            }
        }

        return npc;
    }


    private ParsedContainer parseItemContainer(
        Node rawContainer,
        GlobalResources global)
    {
        ParsedContainer parsedContainer = new ParsedContainer();
        NodeList children = rawContainer.getChildNodes();
        Node child;
        for (int i = 0; i < children.getLength(); i++)
        {
            child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            if (child.getNodeName().equals("name"))
            {
                parsedContainer.container
                    .setName(child.getTextContent().trim());
            }
            else if (child.getNodeName().equals("hidden"))
            {
                parsedContainer.hidden =
                    child.getTextContent().trim().toLowerCase().equals("true");
            }
            else if (child.getNodeName().equals("items"))
            {
                NodeList itemChildren = child.getChildNodes();
                Node itemChild;
                ParsedItem item;
                for (int j = 0; j < itemChildren.getLength(); j++)
                {
                    itemChild = itemChildren.item(j);
                    if (itemChild.getNodeType() == Node.TEXT_NODE)
                    {
                        continue;
                    }
                    item = parseItem(itemChild, global);
                    if (item.item == null)
                    {
                        continue;
                    }
                    parsedContainer.container.getStoredItems().add(item.item);
                }
            }
        }

        return parsedContainer;
    }
}
