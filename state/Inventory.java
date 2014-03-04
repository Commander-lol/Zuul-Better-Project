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
* @version 2014/03/04
*/

public class Inventory {
	private float maxWeight, currentWeight;
	private ArrayList<Item> items;

	/**
	 * Constructor for objects of class Inventory
	 */
	public Inventory() {
		items = new ArrayList<>();
		currentWeight = 0;
		maxWeight = 300;
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
	 * Removes the first item in the inventory that has the given name
	 * and returns it. Will return null if no item is found.
	 * 
	 * @param itemName The name of the item that will be removed
	 * @return the Item whose name matches the given string
	 */
	public Item removeItem(String itemName){
		boolean found = false;
		int i = 0;
		while(!found){
			Item current = items.get(i);
			if(current.getName().equals(itemName)){
				items.remove(i);
				currentWeight -= current.getWeight();
				return current;
			}
		}
		return null;
	}
	
	/**
	 * If available, prints a list of the items in the inventory.
	 * If the inventory is empty, prints a message informing the player.
	 */
	public void printInventory(){
		if(items.size() == 0){
			System.out.println("Your bag is empty");
		} else {
			System.out.println("In your bag you have: ");
			for(Item i : items){
				System.out.println("- " + Phraser.addDeterminer(i.getName()));
			}
		}
	}
}
