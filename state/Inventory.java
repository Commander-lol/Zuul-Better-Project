package state;

import java.util.ArrayList;

import util.Phraser;

import entities.Item;

/**
* This class is part of the "Go Sunset" application. "Go Sunset" is a
* very simple, text based adventure game
*
* This command hold information about the players inventory. The inventory
* currently consists of the maximum and current weight and an array list of
* item objects.
* 
* The way this is used is: Items can be removed and added to the list up to
* the maximum weight when the name of the item is supplied. The printInventory
* method can be called to print the items currently in the inventory if available.
* Else it prints that the inventory is empty.
*
* @author Louis Capitanchik
* @author Joshua Mulcock
* @author Alice Charterton
* @author John Stones
* @version 2014/03/04
*/

public class Inventory {
    private float maxWeight, currentWeight;
    private ArrayList<Item> items;

    /**
     * Creates a new inventory with a default maximum weight of 300 units
     */
    public Inventory() {
        this(300);
    }
    
    /**
     * Creates a new inventory with a specified maximum weight
     * @param weight The maximum weights worth of objects that can be stored in this inventory
     */
    public Inventory(float weight) {
        items = new ArrayList<>();
        currentWeight = 0;
        maxWeight = weight;
    }
    
    /**
     * Attempts to add the item to the inventory, but will
     * not do so if that would increase the weight beyond
     * the defined maximum
     * 
     * @param newItem The item to put in the inventory
     * @return true if the item has successfully been added,
     * false if it is too heavy
     */
    public boolean addItem(Item newItem){
        if(currentWeight + newItem.getWeight() > maxWeight){
            return false;
        } else {
            items.add(newItem);
            return true;
        }
    }
    
    /**
     * Find the given item in the bag and returns it. Will return null if no
     * item is found.
     * 
     * @param itemName the name of the item that will be searched for
     * @return the Item whose name matches the given string
     */
    public Item getItem(String itemName){
        boolean done = false;
        int i = 0;
        while(!done){
            Item current = items.get(i);
            if(current.getName().equalsIgnoreCase(itemName)){
                return current;
            }
            i++;
            if(i >= items.size()){
                done = true;
            }
        }
        return null;
    }
    
    /**
     * Removes the first item in the inventory that has the given name
     * and returns it. Will return null if no item is found.
     * 
     * @param itemName The name of the item that will be removed
     * @return the Item whose name matches the given string
     */
    public Item removeItem(String itemName){
        boolean done = false;
        int i = 0;
        while(!done){
            Item current = items.get(i);
            if(current.getName().equalsIgnoreCase(itemName)){
                items.remove(i);
                currentWeight -= current.getWeight();
                return current;
            }
            i++;
            if(i >= items.size()){
                done = true;
            }
        }
        return null;
    }
    
    /**
     * If available, prints a list of the items in the inventory.
     * If the inventory is empty, prints a message informing the player.
     */
    public void printInventory(String context){
        if(items.size() == 0){
            System.out.println(context + " there is nothing");
        } else {
            System.out.println(context +" there is: ");
            for(Item i : items){
                System.out.println("- " + Phraser.addDeterminer(i.getName()));
            }
        }
    }
    
    /**
     * Searches the inventory for the specified item, returning whether or not
     * it is contained within the inventory instance
     * @param itemName A String containing the item that is being searched for
     * @return Will return true if the item is inside the inventory, false if it
     * is not
     */
    public boolean contains(String itemName){
        for(Item i : items){
            if(i.getName().equalsIgnoreCase(itemName)){
                return true;
            }
        }
        return false;
    }
}
