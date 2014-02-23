package state;

import java.util.ArrayList;

import util.Phraser;

import entities.Item;

/**
 * Write a description of class Inventory here.
 * 
 * @author (your name)
 * @version (a version number or a date)
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
