package state;

import command.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

import entities.Item;
import entities.Room;
import command.Command;
import state.Inventory;
import state.Scroll;
import util.KvReader;
import util.Parser;
import java.util.ArrayList;
import entities.SpecialRoom;

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
    private Scroll scroll;
    private HashMap<String, Room> rooms;
    private ArrayList<String> previousRooms;

    private HashMap<String, Item> items;
  

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        previousRooms = new ArrayList <String>();
        rooms = new HashMap<String, Room>(); 

        createRooms();
        parser = new Parser();
        bag = new Inventory();
        bag.addItem(new Item(10f, "Apple"));
        bag.addItem(new Item(0f, "Scroll"));
        scroll = new Scroll();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        for (String s : getKvFiles("./rooms")) {
            HashMap<String, String> roomAttributes = KvReader.readFile(s);
            System.out.println(roomAttributes.get("description"));
            if (roomAttributes.get("riddle") == null) {
            rooms.put(roomAttributes.remove("id"), new SpecialRoom(this, roomAttributes));
            } 
            else { 
            
            rooms.put(roomAttributes.remove("id"), new Room(this, roomAttributes));}
            
        }
        
        currentRoom = "Empty Room 1";
    }
        /*
        Room outside, theater, pub, lab, office;

        // create the rooms
        outside = new Room("outside the main entrance of the university", this);
        theater = new Room("in a lecture theater", this);
        pub = new Room("in the campus pub", this);
        lab = new Room("in a computing lab", this);
        office = new Room("in the computing admin office", this);
        
        rooms.put("outside", outside);
        rooms.put("theater", theater);
        rooms.put("pub", pub);
        rooms.put("lab", lab);
        rooms.put("office", office);
        
        // initialise room exits
        outside.setExit("east", "theater");
        outside.setExit("south", "lab");
        outside.setExit("west", "pub");

        theater.setExit("west", "outside");

        pub.setExit("east", "outside");

        lab.setExit("north", "outside");
        lab.setExit("east", "office");

        office.setExit("west", "lab");

        currentRoom = "outside"; // start game outside

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println("𝗚𝗢 𝗦𝗨𝗡𝗦𝗘𝗧");
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
        System.out.println(rooms.get(currentRoom).getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command
     *            The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
           if(command.hasSecondWord()){
               printHelp(command.getSecondWord());
            } else {
               printHelp();
            }
        } else if (commandWord.equals("go")) {
            parseRoom(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if(commandWord.equals("back")){
            if(!goBack(command)){
                System.out.println("You can't go back that much!");
            }
        } else if (commandWord.equals("view")){
            if(command.hasSecondWord()){
                String sWord = command.getSecondWord();
                switch(sWord){
                case "bag":
                    String context = "In the bag";
                    bag.printInventory(context);
                    break;
                case "scroll":
                    scroll.printScroll();
                    break;
                default:
                    if(bag.contains(sWord)){
                        bag.getItem(sWord).printImage();
                    } else {
                        System.out.println("");
                    }
                }
            }
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information. Here we print a simple
     * message and a list of the command words.
     */
    private void printHelp() {
        System.out.println("You are lost in a large stone tower");
        System.out.println();
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
     * Try to move in one direction. Parses the command to look for various factors
     * including existence of a direction, and the presence of an exit in that direction.
     * If it is possible to move in that direction, it will invoke goRoom to do so
     */
    private void parseRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        String nextRoom = rooms.get(currentRoom).getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            goRoom(nextRoom);
        }
    }
    
    /**
     * Store the current room in the list of previous rooms and set the current room to 
     * the given String before printing out the description of the new room and 
     */
    private void goRoom(String nextRoom) {
            previousRooms.add(currentRoom);
            currentRoom = nextRoom;
            System.out.println(rooms.get(currentRoom).getLongDescription());
    }

    /**
     * Sends the player back N rooms, where N is any positive integer less than the total number of 
     * rooms traversed so far. If no number is specified, it is assumed to mean 1 room
     * 
     * @return A boolean value stating whether or not the player could succesfully go back that many
     * rooms
     */
    private boolean goBack(Command command){
        int backage = 0;
        if(!command.hasSecondWord()){
            backage = 1;
        } else {
            String sWord = command.getSecondWord();
            try {
                backage = Integer.parseInt(sWord);
            } catch(Exception e) {
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
    
    /**
     * "Quit" was entered. Check the rest of the command to see whether we
     * really quit the game.
     * 
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true; // signal that we want to quit
        }
    }

    /**
     * Recursively searches the given directory for any files ending with '.kv'
     * and adds their paths to a set
     * 
     * @param dir
     *            The directory path of the directory to search
     * @return A HashSet object containing the paths of all the .kv files that
     *         were found in the given directory
     */
    private HashSet<String> getKvFiles(String dir) {
        File rootDir = new File(dir);
        HashSet<String> filePaths = new HashSet<>();

        if (rootDir.isDirectory()) {
            for (File f : rootDir.listFiles()) {
                if (f.isDirectory()) {
                    filePaths.addAll(getKvFiles(f.getPath()));
                } else {
                    if (f.getPath().endsWith(".kv")) {
                        filePaths.add(f.getPath());
                    }
                }
            }
        } else {
            filePaths.add(dir);
        }

        return filePaths;
    }
}
