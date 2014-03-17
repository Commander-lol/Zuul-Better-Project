package entities;

import java.util.Set;
import java.util.HashMap;

import state.Game;
import entities.Item;
import java.util.ArrayList;
import state.Inventory;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Go Sunset" application. 
 * "Go Sunset" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @author Louis Capitanchik
 * @author Joshua Mulcock
 * @author Alice Charterton
 * @author John Stones
 * 
 * @version 2014.03.14
 */

public class Room 
{
    protected String description;
    protected HashMap<String, String> exits;        // stores exits of this room.
    protected Game context;
    protected Inventory roomInventory;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     * @param context An instance of the Game class
     * @Deprecated Use other constructor instead
     */
    public Room(String description, Game context) 
    {
        this(context, tempDepFix(description));
    }
   
    private static final HashMap<String, String> tempDepFix(String s){HashMap<String, String> h = new HashMap<String, String>(); h.put("description", s); return h;}
    
    public Room(Game context, HashMap<String, String> attributes){
        this.context = context;
        this.description = attributes.remove("description");
        exits = attributes;
        roomInventory = new Inventory(30000);
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, String neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    public String getExitString()
    {
        String returnString = "Exits:";
        for(String exit : exits.keySet()) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public String getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Puts an item into the inventory of the room
     * @param item The item to be placed in the room
     */
    public boolean addItem(Item item) {
        return roomInventory.addItem(item);
    }

    /**
     * Take an item out of the room's inventory and return it
     * @param itemName the name of the item to be removed from the room
     * @return The first item in the room with the given item name
     */
    public Item removeItem(String itemName) {
        return roomInventory.removeItem(itemName);
    }
    
    /**
     * Gets a given item from the room's inventory, but does not remove it
     * @param itemName the name of the item to be returned
     * @return The first item in the room with the given item name
     */
    public Item getItem(String itemName){
        return roomInventory.getItem(itemName);
    }
     
    /**
     * Used to find the rooms exits from the hashmaps
     * @return returns the exits for the room
     */
    public HashMap<String, String> getExits(){
        return exits;
    }
    
    /**
     * Used to print the what items are in the room
     * context is a string that holds the information for why the items are being printed out
     */
    public void printItems(){
        String context = "In the room";
        roomInventory.printInventory(context);
    }
}

