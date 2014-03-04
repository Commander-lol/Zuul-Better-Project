package entities;

/**
* This class is part of the "Go Sunset" application. "Go Sunset" is a
* very simple, text based adventure game
* 
* This class holds information about an in game item. An item currently consists
* of a weight and a name.
*
* @author Louis Capitanchik
* @version 2014/02/23
*/

public class Item {
	private float weight;
	private String name;
	
	public Item(float weight, String name) {
		this.weight = weight;
		this.name = name;
	}

	/**
	 * @return the weight of the item
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * @return the name of the item
	 */
	public String getName() {
		return name;
	}
	
	
}
