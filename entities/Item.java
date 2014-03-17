package entities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

/**
* This class is part of the "Go Sunset" application. "Go Sunset" is a
* very simple, text based adventure game
* 
* This class holds information about an in game item. An item currently consists
* of a weight and a name.
*
* @author Louis Capitanchik
* @author Joshua Mulcock
* @author Alice Charterton
* @author John Stones
* 
* @version 2014/02/23
*/

public class Item {
    private float weight;
    private String name;
    
    /**
     * Creates a new Item instance with the given name and weight. The name is tightly coupled to an image
     * and they must match exactly if they are to be linked
     * @param weight The weight of the item in units
     * @param name The name of the item
     */
    public Item(float weight, String name) {
        this.weight = weight;
        this.name = name;
    }

    /**
     * Gets the weight of the item
     * @return the weight of the item
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Gets the name of the item
     * @return the name of the item
     */
    public String getName() {
        return name;
    }
    
    /**
     * Prints the image associated with this item to stdout
     */
    public void printImage() throws FileNotFoundException{
        FileReader fileInput;
        BufferedReader fileReader;
        
        fileInput = new FileReader("./images/"+name.toLowerCase());
        fileReader = new BufferedReader(fileInput);
        
        String line;
        try {
            while((line = fileReader.readLine()) != null){
                System.out.println(line);
            }
            System.out.println(); // Print blank line to improve readability of following text
        } catch(IOException e){
            e.printStackTrace(System.err);
        } finally {
             try{
                fileReader.close();
                fileInput.close();
            } catch(IOException e){
                e.printStackTrace(System.err);
            }
        } 
    }
    
}
