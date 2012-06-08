package org.unSkooledEngine.TextTest;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Observable;
import java.util.Observer;
import unSkooledEngine.text.Character;
import unSkooledEngine.text.Room;
import unSkooledEngine.text.actions.BaseAction;
import unSkooledEngine.text.items.BaseItem;
import unSkooledEngine.text.items.ItemType;
import unSkooledEngine.text.items.SimpleArmor;
import unSkooledEngine.text.items.SimpleWeapon;
import unSkooledEngine.text.level.LevelLoader;

// -------------------------------------------------------------------------
/**
 * The main activity for the game.
 *
 * @author Sean Meacham (sean22)
 * @author Tim Eck (etimot2)
 * @author Nate Tucker (imtucker)
 * @version 2012.04.29
 */
public class TextTestActivity
    extends Activity
{
    private Room         room;

    private TextView     mainCharHP;
    private TextView     mainCharDEF;
    private TextView     mainCharATT;

    private LinearLayout actions;
    private LinearLayout items;
    private Context      context;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        context = this;
        setupInitialMap();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mainCharHP = (TextView)findViewById(R.id.mainCharHP);
        mainCharDEF = (TextView)findViewById(R.id.mainCharDEF);
        mainCharATT = (TextView)findViewById(R.id.mainCharATT);
        updateStatusBars();

        actions = (LinearLayout)findViewById(R.id.actions);
        buildActionList();

        items = (LinearLayout)findViewById(R.id.items);
        buildStoredItemList();
    }


    /**
     * Sets up the conditions for the initial room
     */
    private void setupInitialMap()
    {
        Resources res = context.getResources();
        LevelLoader loader = new LevelLoader();
        room =
            loader.loadRoom(
                res.openRawResource(R.raw.level),
                new RoomObserver());
    }


    /**
     * Updates the action list with the actions that are in the current room.
     */
    private void buildActionList()
    {
        actions.removeAllViews();
        TextView actionView = new TextView(this);
        actionView.setText("Select Action");
        actionView.setTextAppearance(
            this,
            android.R.style.TextAppearance_Medium);
        actionView.setPadding(0, 5, 0, 0);
        actions.addView(actionView);
        for (BaseAction action : room.getCurrentActions())
        {
            actionView = new TextView(this);
            actionView.setText(action.getDescription());
            actionView.setTag(action);
            actionView.setOnClickListener(new ActionSelectedListener());
            actionView.setPadding(3, 10, 0, 0);
            actions.addView(actionView);
        }
        actionView = new TextView(this);
        actionView.setText("");
        actions.addView(actionView);
    }


    private void buildStoredItemList()
    {
        items.removeAllViews();
        TextView itemView = new TextView(this);
        itemView.setText("Select Item");
        itemView.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        items.addView(itemView);
        for (BaseItem item : room.getMainCharacter().getEquippedItems())
        {
            itemView = new TextView(this);
            itemView.setTag(item);
            itemView.setOnClickListener(new ItemSelectedListener());
            itemView.setPadding(5, 10, 0, 0);
            if (item.getType() == ItemType.Armor
                || item.getType() == ItemType.Weapon)
            {
                itemView.setText("remove: " + item.getName());
            }
            else
            {
                itemView.setText("use: " + item.getName());
            }
            items.addView(itemView);
        }

        for (BaseItem item : room.getMainCharacter().getStoredItems())
        {
            itemView = new TextView(this);
            itemView.setTag(item);
            itemView.setOnClickListener(new ItemSelectedListener());
            itemView.setPadding(5, 10, 0, 0);
            if (item.getType() == ItemType.Armor
                || item.getType() == ItemType.Weapon)
            {
                itemView.setText("equip: " + item.getName());
            }
            else
            {
                itemView.setText("use: " + item.getName());
            }
            items.addView(itemView);
        }
    }


    // ----------------------------------------------------------
    /**
     * Updates the status bar that display player information
     */
    public void updateStatusBars()
    {
        Character main = room.getMainCharacter();
        float att = 0;
        float def = 0;

        for (BaseItem item : main.getEquippedItems())
        {
            if (item instanceof SimpleWeapon)
            {
                att = ((SimpleWeapon)item).getDamage();
            }
            else if (item instanceof SimpleArmor)
            {
                def = ((SimpleArmor)item).getArmorValue();
            }
        }

        mainCharHP.setText("HP: " + main.getCharacterHealth());
        mainCharDEF.setText("DEF: " + def);
        mainCharATT.setText("ATT: " + att);
    }


    /**
     * Nested class to set do a specific action when one is selected by the
     * action spinner.
     */
    public class ActionSelectedListener
        implements OnClickListener
    {
        public void onClick(View clickedView)
        {
            Object tag = clickedView.getTag();
            if (tag == null)
            {
                return;
            }
            if (((BaseAction)tag).applyAction(room))
            {
                room.getCurrentActions().remove(tag);
            }

            buildActionList();
            buildStoredItemList();
            updateStatusBars();
        }

    }


    /**
     * Nested class to set do a add or remove and item when it is selected.
     */
    public class ItemSelectedListener
        implements OnClickListener
    {
        public void onClick(View selectedItem)
        {
            Object tag = selectedItem.getTag();
            if (tag == null)
            {
                return;
            }
            Character main = room.getMainCharacter();
            if (((BaseItem)tag).applyItem(main))
            {
                main.getEquippedItems().remove(tag);
                main.getStoredItems().remove(tag);
            }
            buildActionList();
            buildStoredItemList();
            updateStatusBars();
        }
    }


    // -------------------------------------------------------------------------
    /**
     * The observer class for the room.
     */
    public class RoomObserver
        implements Observer
    {
        public void update(Observable observered, Object param)
        {
            Room tempRoom = (Room)observered;
            if (tempRoom.hasMessage())
            {
                Toast.makeText(
                    context,
                    tempRoom.getMessage(),
                    Toast.LENGTH_LONG).show();
                tempRoom.eraseMessage();
            }
            else if (tempRoom.shouldChangeRoom())
            {

                room = tempRoom.getLinkedRooms()[(Integer)param];
                room.setMainCharacter(tempRoom.getMainCharacter());
                tempRoom.setChangeRoom(false);
                buildActionList();
                buildStoredItemList();
                updateStatusBars();
            }
        }

    }

}
