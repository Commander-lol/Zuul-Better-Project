package state;

import command.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import entities.Item;
import entities.Room;
import entities.NPC;
import entities.SpecialRoom;
import command.Command;
import state.Inventory;
import util.KvReader;
import util.Phraser;
import util.Parser;

/**
 * This class is the main class of the "Go Sunset" application.
 * "Go Sunset" is a very simple, text based adventure game. Users can walk
 * around some scenery. That's all. It should really be extended to make it more
 * interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2011.08.08
 */

public class Game {
    private Parser parser;
    private String currentRoom;
    private Inventory bag;
    private Brewery brewery;
    private HashMap<String, NPC> npcs;
    private HashMap<String, Room> rooms;
    private HashMap<String, Item> orphanedItems;
    private ArrayList<String> previousRooms;
    
    // Progress Variables
    private int riddleFailures = 0;
    private boolean hasKey = false, isFinalRoom = false;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        
        previousRooms = new ArrayList <String>();
        rooms = new HashMap<String, Room>(); 
        orphanedItems = new HashMap<String, Item>();
        npcs = new HashMap<String, NPC>();
        parser = new Parser();
        bag = new Inventory();
        
        createRooms();
        createItems();
        createNpcs();
        brewery = new Brewery(this, adoptItem("leprosy cure"));
        finaliseItems();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        HashSet<String> roomPaths;
        try {
            roomPaths = KvReader.getKvFiles("./rooms");
        } catch (IOException e){
            e.printStackTrace(System.err);
            return;
        }
        for (String s : roomPaths) {
            HashMap<String, String> roomAttributes = KvReader.readFile(s);
            if (roomAttributes.get("riddle") == null) {
                rooms.put(roomAttributes.remove("id"), new Room(this, roomAttributes));
            } else {
                rooms.put(roomAttributes.remove("id"), new SpecialRoom(this, roomAttributes));
            }
        }
        
        currentRoom = "Empty Room 1";
    }
    
    private void createItems() {
        HashSet<String> itemPaths;
        try {
            itemPaths = KvReader.getKvFiles("./items");
        } catch (IOException e){
            e.printStackTrace(System.err);
            return;
        }
        for (String s : itemPaths) {
            HashMap<String, String> itemAttributes = KvReader.readFile(s);
            String name = itemAttributes.get("name");
            float weight = 0;
            
            try {
                weight = Float.valueOf(itemAttributes.get("weight"));
            } catch (NumberFormatException e){
                System.err.println("NaN error with item " + name);
                //Ignore this item and move on
                continue;
            }
            Item i = new Item(weight, name);
            if (itemAttributes.get("room") == null) {
                orphanedItems.put(name, i);
            } else {
                Room room = rooms.get(itemAttributes.get("room"));
                if(room instanceof SpecialRoom){
                   ((SpecialRoom)room).setReward(i);
                } else {
                    room.addItem(i);
                }
            }
        }
    }
    
    private void createNpcs(){
        HashSet<String> npcPaths;
        try {
            npcPaths = KvReader.getKvFiles("./npcs");
        } catch (IOException e){
            e.printStackTrace(System.err);
            return;
        }
        for(String s : npcPaths){
            HashMap<String, String> npcAttributes = KvReader.readFile(s);
            npcs.put(npcAttributes.get("name"), new NPC(this, npcAttributes));
        }
    }
    
    /**
     * Put all of the items that haven't been placed into rooms or npcs into the player's inventory
     */
    private void finaliseItems(){
        //We need a copy of the set so that we can remove elements without violating loop integrity
        HashSet<String> items = new HashSet<String>(orphanedItems.keySet());
        for(String s : items){
            bag.addItem(orphanedItems.remove(s));
        }
    }


    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println("GO SUNSET");
        System.out.println();
        System.out.println("The last dregs of your beer finished, \n" + 
                            "you stumble out to the front of the Isembard Kingdom Brunel, \n" +
                            "noticing the setting sun as you go. It is late, the night \n" + 
                            "is dark and full of terrors. As you hurry homeward towards \n" + 
                            "Gunwarf Quays you notice a shimmer in the air - as you enter \n" + 
                            "the vicinity of Spinnaker Tower, you start to see the ground \n" + 
                            "around you shake. Rocks fly into the air, shards of glass falling \n" + 
                            "between them like silvered daggers in the darkness. In font of \n" + 
                            "your very eyes, the metal beams ahead of you wrench themselves \n" + 
                            "free from the dirt and a stone tower rises from the ground in \n" + 
                            "its place. A large wooden door assaults your eyeballs with it's \n" +
                            "sensuous oaky texture");
        System.out.println();
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printCurrentRoom();
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            for(NPC npc : npcs.values()){
                npc.act();
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    
    /**
     * A simple way for context based entities to add an item to the current room
     * @param item The item that will be placed on the floor of the current room
     */
    public void dropItemOnFloor(Item item){
        rooms.get(currentRoom).addItem(item);
        System.out.println(Phraser.addDeterminer(item.getName()) + " has dropped on the floor");
    }
    
    /**
     * Removes an item from the orphaned items list and return it
     * @param itemName The name of the item to be adopted
     * @return The first item object with the specified name
     */
    public Item adoptItem(String itemName){
        return orphanedItems.remove(itemName);
    }
    
    public Room getCurrentRoom(){
        return rooms.get(currentRoom);
    }
    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getOperator();
        if (commandWord.equals("help")) {
           if(command.hasNthWord(2)){
               printHelp(command.getNthSegment(1));
            } else {
               printHelp();
            }
        } else if (commandWord.equals("go")) {
            if(!isFinalRoom){
                parseRoom(command);
            } else {
                wantToQuit = attemptWin(command);
            }
        } else if (commandWord.equals("quit")) {
            wantToQuit = true;
        } else if(commandWord.equals("back")){
            if(!goBack(command)){
                System.out.println("You can't go back that much!");
            }
        } else if (commandWord.equals("view")){
            String sWord = command.getNthSegment(1);
            if(sWord.equals("bag")){
                bag.printInventory("In the bag");
            } else {
                printItem(sWord);
            }
        } else if (commandWord.equals("take")){
            takeItem(command);
        } else if (commandWord.equals("drop")){
            dropItem(command);
        } else if (commandWord.equals("combine")){
            combineItems(command);
        } else if (commandWord.equals("give")){
            giveItem(command);
        }else if (commandWord.equals("answer")){
            wantToQuit = answerRiddle(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:
    
    /**
     * Print out the entire set of information for the current room, as if it had just been entered
     */
    private void printCurrentRoom() {
        Room curRoom = rooms.get(currentRoom);
        System.out.println(curRoom.getShortDescription());
        for(NPC npc : npcs.values()){
            if(npc.getCurrentRoom() != null && npc.getCurrentRoom().equals(currentRoom)){
                System.out.println();
                npc.speak();
                System.out.println();
            }
        }
        if(curRoom instanceof SpecialRoom && !((SpecialRoom)curRoom).complete()){
            System.out.println();
            System.out.println("You hear a ghostly voice say...");
            System.out.println(((SpecialRoom)rooms.get(currentRoom)).getRiddle());
            System.out.println();
        }
        curRoom.printItems();
        System.out.println();
        System.out.println(curRoom.getExitString());
        System.out.println();
    }
    
    /**
     * Print out some help information. Here we print a simple
     * message and a list of the command words.
     */
    private void printHelp() {
        printCurrentRoom();
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println();
        System.out.println("Type \"Help <Command>\" to see what a command does");
    }
    
    /**
     * Print out some help for a specific command
     */
    private void printHelp(String command){
        parser.showCommand(command);
    }
    
    /**
     * Display the picture associated with the image, as long as it exists in either the current room or your inventory
     * @param itemName The name of the item whose picture should be printed
     */
    private void printItem(String itemName){
        Item i = null;
        if (((i = bag.getItem(itemName)) != null) || ((i = rooms.get(currentRoom).getItem(itemName)) != null)){
            try {
                i.printImage();
            } catch (FileNotFoundException e) {
                System.out.println("Couldn't see " + Phraser.addDeterminer(itemName));
            }
        }
    }
    
    /**
     * Picks an item up off of the ground in the current room, but will put it back down if it would cause the player to be too heavy
     * @param command The command holding the user input
     */
    private void takeItem(Command command){
        if(!command.hasNthWord(2)){
            System.out.println("Take what?");
        } else {
            Item i = rooms.get(currentRoom).removeItem(command.getNthSegment(1));
            if(i == null){
                System.out.println("There is no " + command.getNthSegment(1));
            } else if(i.getName().equals("key")){
                hasKey = true;
                System.out.println("You found the key to the final floor");
            } else if(!bag.addItem(i)){
                rooms.get(currentRoom).addItem(i);
                System.out.println("That is too heavy to carry");
            } else {
                System.out.println("You take the " + i.getName());
            }
        }
    }
    
    /**
     * Drops an item up on to the ground in the current room, but will pick it back up if it would cause the room to be too full
     * @param command The command holding the user input
     */
    private void dropItem(Command command){
        if(!command.hasNthWord(2)){
            System.out.println("Drop what?");
        } else {
            Item i = bag.removeItem(command.getNthSegment(1));
            if(i == null){
                System.out.println("You don't have  " + Phraser.addDeterminer(command.getNthSegment(1)));
            } else if(!rooms.get(currentRoom).addItem(i)){
                bag.addItem(i);
                System.out.println("There isn't any room for that here");
            } else {
                System.out.println("You drop the " + i.getName());
            }
        }
    }
    
    private void giveItem(Command command){
        NPC npc = null;
        
        for(NPC n : npcs.values()){
            if(n.getCurrentRoom().equals(currentRoom)){
                npc = n;
            }
        }
        
        if(npc == null){
            System.out.println("Give to whom?");
        } else if (!command.hasNthWord(2)){
            System.out.println("Give what?");
        } else {
            String itemName = command.getNthSegment(1);
            if(bag.contains(itemName)){
                if(npc.getItemNeeded().equals(itemName)){
                    System.out.println(npc.getName() + " says:");
                    System.out.println("\"I'm going to leave this on the floor...\"");
                    dropItemOnFloor(npc.swapItems(bag.removeItem(itemName)));
                    System.out.println();
                    npc.speak();
                } else {
                    System.out.println(npc.getName() + " says:");
                    System.out.println("I don't want that...");
                }
            } else {
                System.out.println("You don't have " + Phraser.addDeterminer(itemName));
            }
        }
    }
    
    private boolean answerRiddle(Command command){
        Room room = rooms.get(currentRoom);
        if(!command.hasNthWord(2)){
            System.out.println("Answer with what?");
        } else if(!(room instanceof SpecialRoom) || ((SpecialRoom)room).complete()){
            System.out.println("Answer who?");
        } else {
            String answer = command.getNthSegment(1);
            SpecialRoom sRoom = (SpecialRoom) room;
            if(sRoom.attemptAnswer(answer)){
                System.out.println("The room grows slightly dimmer as a piece of a boat materialises in front of you");
                dropItemOnFloor(sRoom.getReward());
            } else {
                System.out.println("A shiver runs up your spine as the room gets colder. \nYou don't think that was the right answer, and you aren't sure how much longer you can do this...");
                riddleFailures++;
                if(riddleFailures == 3){
                    System.out.println();
                    System.out.println("You begin to hear moaning and screaming inside your head, as you collapse to the floor in a gibbering heap.");
                    System.out.println("You won't be escaping Spinnaker Tower any time soon...");
                    System.out.println();
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Combine items with the brewery specifically; will fail if the brewery is not present, but will give a reply
     * indicating that the items simply couldn't be combined
     * @param command The command that holds the items to be combined
     */
    private void combineItems(Command command){
        if(!command.hasNthWord(3)){
            System.out.println("You need to combine more things");
        } else {
            String segment1 = command.getNthSegment(1);
            String segment2 = command.getNthSegment(2);
            if(rooms.get(currentRoom).getItem("brewery")!= null || (segment1.equals("brewery") ^ segment2.equals("brewery"))){
                if(segment1.equals("brewery") && brewery.needsItem(segment2) && bag.contains(segment2)){
                    brewery.throwInItem(bag.removeItem(segment2));
                    System.out.println("You throw the " + segment2 + " into the brewery and it bubbles slightly");
                } else if(segment2.equals("brewery") && brewery.needsItem(segment1) && bag.contains(segment1)){
                    brewery.throwInItem(bag.removeItem(segment1));
                    System.out.println("You throw the " + segment1 + " into the brewery and it bubbles slightly");
                } else {
                    System.out.println("You can't combine those");
                }
            } else {
                System.out.println("You can't combine those");
            }
        }
    }
    
    /**
     * Try to move in one direction. Parses the command to look for various factors
     * including existence of a direction, and the presence of an exit in that direction.
     * If it is possible to move in that direction, it will invoke goRoom to do so
     * @param command The command holding the user input
     */
    private void parseRoom(Command command) {
        if (!command.hasNthWord(2)) {
            System.out.println("Go where?");
        } else {
            String direction = command.getNthSegment(1);
            String nextRoom = rooms.get(currentRoom).getExit(direction);
            if (nextRoom == null) {
                System.out.println("You can't go there!");
            } else {
                if(nextRoom.equals("Ken's Krib")){
                    if(hasKey){
                        isFinalRoom = true;
                    } else {
                        System.out.println("The door at the top of the stairs appears to be locked...");
                        return;
                    }
                }
                goRoom(nextRoom);
            }
        }
    }
    
    private boolean attemptWin(Command command){
        if (!command.hasNthWord(2)) {
            System.out.println("Go where?");
        } else if (command.getNthSegment(1).equalsIgnoreCase("Sunset")){
            if(bag.contains("hull") && bag.contains("mast") && bag.contains("rudder") && bag.contains("helm")){
                System.out.println("Something something ken boat win");
            } else {
                System.out.println("Oh no missing boat parts lose");
            }
            return true;
        }
        return false;
    }
    
    /**
     * Store the current room in the list of previous rooms and set the current room to 
     * the given String before printing out the description of the new room and 
     * @param nextRoom The room that is to be moved into. It is assumed that it has already been checked for validity
     */
    private void goRoom(String nextRoom) {
        previousRooms.add(currentRoom);
        currentRoom = nextRoom;
        printCurrentRoom();
    }

    /**
     * Sends the player back N rooms, where N is any positive integer less than the total number of 
     * rooms traversed so far. If no number is specified, it is assumed to mean 1 room
     * @param command The command holding the user input
     * @return A boolean value stating whether or not the player could succesfully go back that many
     * rooms
     */
    private boolean goBack(Command command){
        int backage = 0;
        if(!command.hasNthWord(2)){
            backage = 1;
        } else {
            String sWord = command.getNthSegment(1);
            try {
                backage = Integer.parseInt(sWord);
            } catch(NumberFormatException e) {
                return false;
            }
        }
        if(previousRooms.size() < backage) {
            return false;
        }
        ArrayList<String> roomsToGo = new ArrayList<String>(backage);
        for (int i = 1; i <= backage; i++) {
            roomsToGo.add(previousRooms.get(previousRooms.size()-i));
        }
        for (int i = 0; i < roomsToGo.size()-1; i ++) {
            previousRooms.add(roomsToGo.remove(i));
        }
        goRoom(roomsToGo.remove(0));
        return true;
    }
}
