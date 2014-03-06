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
    
    /**
     * Prints the image associated with this item to stdout
     */
    public void printImage() {
        FileReader fileInput;
        BufferedReader fileReader;
        try {
            fileInput = new FileReader("./images/"+name.toLowerCase());
            fileReader = new BufferedReader(fileInput);
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.err);
            return;
        }
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
