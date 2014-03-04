package entities;
import entities.Item;

/**
 * Class NPC - a non playing character.
 *
 * This class is part of the "GO Sunset" application. 
 * "Go Sunset" is a very simple, text based adventure game adapted from the "World of Zuul" application.  
 *
 * An NPC is a character that the player can interact with and trade items with.
 * 
 * @author  Josh Mulccock, Louis Capitanchick, Alice Charterton, John Stones.
 * @version 2014.03.04
 */


public class NPC {
    
private String name;
private Item itemHeld;
private Item itemNeeded;
private String speech;
private String currentRoom;
    
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
    
public NPC (String npcName, Item needed, Item held, String npcSpeech, String npcRoom){
    name = npcName;
    itemNeeded = needed;
    itemHeld = held;
    speech = npcSpeech;
    currentRoom = npcRoom;
}

/**
 * returns the item wanted by the NPC
 */
public Item getItemNeeded(){
    return itemNeeded;
}


/**
 * prints out the speech by the NPC
 */
public void printSpeech(){
    System.out.println(speech);
}


/**
 * returns the the item held by the NPC
 */
public Item getItemHeld(){
    return itemHeld;
}

/**
 * returns the current room the NPC is in
 */
public String getCurrentRoom(){
    return currentRoom;
}


}

