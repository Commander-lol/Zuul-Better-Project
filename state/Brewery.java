package state;

import entities.Item;

/**
 * The brewery state object 
 * 
 * @author Louis Capitanchik 
 * @author Joshua Mulcock
 * @author Alice Charterton
 * @author John Stones
 * @version 15.03.2014
 */
public class Brewery {
    private String[] neededItems = {"chips", "apple", "kebab-juice"};
    private Game context;
    private Item product;
    
    /**
     * Creates a new Brewery object that will be used to create the cure for leprosy
     */
    public Brewery(Game context, Item product){
        this.context = context;
        this.product = product;
    }
   
    /**
     * Checks the brewery to see if the given item is part of the recipe
     * @param itemName The name of the item being checked
     * @return A boolean determining whether or not the item is part of the recipe
     */
    public boolean needsItem(String itemName){
        for(String s : neededItems){
            if(s!=null && s.equals(itemName)){
                return true;
            }
        }
        return false;
    }
    /**
     * Throw an item into the brewery. It will be lost regardless of
     * whether or not it was required, but will not negatively affect the 
     * mixture if it wasn't
     * @param item The item to throw into the brewery
     */
    public void throwInItem(Item item){
        int pos = -1;
        for(int i = 0; i < neededItems.length; i++){
            if(item.getName().equals(neededItems[i])){
                neededItems[i] = null;
            }
        }
        if(isFinished()){
            context.dropItemOnFloor(product);
        }
    }
    
    /**
     * Checks to see if the number of items combined is the correct amount
     * @returns false if the number of items needed has a value
     * @returns true if the number of items needed is null
     */
    public boolean isFinished(){
        for(String s : neededItems){
            if(s!=null)return false;
        }
        return true;
    }
}
