package entities;
import state.Game;
import entities.Item;
import java.util.HashMap;
import java.util.Set;
import java.util.Random;
/**
 * Class NPC - a non playing character.
 *
 * This class is part of the "GO Sunset" application. 
 * "Go Sunset" is a very simple, text based adventure game adapted from the "World of Zuul" application.  
 *
 * An NPC is a character that the player can interact with and trade items with.
 * 
 *@author Louis Capitanchik
 * @author Joshua Mulcock
 * @author Alice Charterton
 * @author John Stones
 * 
 * @version 2014.03.04
 */


public class NPC {
private Game context;    
private String name, preSpeech, postSpeech, currentRoom, itemNeeded;
private Item itemHeld;
    
/**
 * This constructs the NPC class, using information that will be passed when
 * a new NPC object is created.
 * 
 * name is the NPC's name.
 * itemHeld is the item that the NPC currently has and will trade with the player for the item which is wanted.
 * itemNeeded is the item that the NPC wants and will trade the the itemHeld for.
 * speech is the the text that will be printed out when the player interacts with NPC.
 * currentRoom is the name of the room the NPC is currently in.
 * 
 * @param npcName the name of the NPC.
 * @param needed is the Item that the NPC needs.
 * @param held is the Item that the NPC currently.
 * @param npcSpeech is the speech for the NPC.
 * @param npcRoom is the room that the NPC is currrently in.
 */
    
public NPC (Game context, HashMap<String, String> attributes){
    this.context = context;
    name = attributes.get("name");
    itemNeeded = attributes.get("wanted item");
    itemHeld = context.adoptItem(attributes.get("held item"));
    preSpeech = attributes.get("text");
    postSpeech = attributes.get("post trade text");
    currentRoom = attributes.get("room");
    
}

/**
 * Gets the NPCs name, as would be used in conversation
 * @return The name of the NPC
 */
public String getName(){
    return name;
}

/**
 * Gets the item wanted by the NPC
 * @return the item wanted by the NPC
 */
public String getItemNeeded(){
    return itemNeeded;
}


/**
 * prints out the speech by the NPC
 * 
 * speech holds the speech required
 */
public void speak(){
    String speech;
    if(itemHeld.getName().equals(itemNeeded)){
        speech = postSpeech;
    } else  {
        speech = preSpeech;
    }
    System.out.println(name + " says:");
    System.out.println("\"" + speech + "\"");
}


/**
 * Used to move the NPCs into a valid and connected room
 * exits is a HashMap of availabe exits
 * r is a random variable
 * valid is a boolean variable to show when a valid room has been found
 * exitNum is the placement of the valid room found
 */
public void act(){
    HashMap<String, String> exits = context.getCurrentRoom(currentRoom).getExits();
    Set<String> exitSet = exits.keySet();
    
    int exitNum = 0;
    String exit = null;
    Random r = new Random();
    boolean valid = false;
    while(!valid){
        exitNum = r.nextInt(exitSet.size());
        int c = 0;
        for(String s : exitSet){
            if(c == exitNum){
                exit = s;
                break;
            }
            c++;
        }
        if(!exit.equals("upstairs") && !exit.equals("downstairs")){
            valid = true;
        }
    }
    currentRoom = exits.get(exit);
}


/**
 * gets the item held by the NPC
 * @return the item held by NPC
 */
public Item getItemHeld(){
    return itemHeld;
}

/**
 * Swaps the item held by the npc with the given item
 * @param itemToSwap the item that will be given to the NPC
 * @return the item the NPC was previously holding
 */
public Item swapItems(Item itemToSwap){
    Item i = itemHeld;
    itemHeld = itemToSwap;
    return i;
}

/**
 * gets the current room the NPC is in
 * @return the NPC's current room
 */
public String getCurrentRoom(){
    return currentRoom;
}


}

