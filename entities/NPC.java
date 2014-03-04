package entities;
import entities.Item;

public class NPC {
    
private String name;
private Item itemHeld;
private Item itemNeeded;
private String speech;
private String currentRoom;
    
public NPC (String npcName, Item needed, Item held, String npcSpeech, String npcRoom){
    name = npcName;
    itemNeeded = needed;
    itemHeld = held;
    speech = npcSpeech;
    currentRoom = npcRoom;
}

public Item getItemNeeded(){
    return itemNeeded;
}

public void printSpeech(){
    System.out.println(speech);
}

public Item getItemHeld(){
    return itemHeld;
}

public String getCurrentRoom(){
    return currentRoom;
}

}

