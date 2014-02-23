package entities;

/**
 * Write a description of class Item here.
 * 
 * @author (your name)
 * @version (a version number or a date)
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
