import java.io.File;
import java.util.HashSet;

import entities.Item;
import entities.Room;
import command.Command;
import state.Inventory;
import state.Scroll;
import util.KvReader;
import util.Parser;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game. Users can walk
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
	private HashMap <String, Room> rooms; 
	
	/**
	 * Create the game and initialise its internal map.
	 */
	public Game() {
		createRooms();
		parser = new Parser();
		for (String s : getKvFiles(".")) {
			KvReader.readFile(s);
		}
		bag = new Inventory();
		bag.addItem(new Item(10f, "Apple"));
		bag.addItem(new Item(0f, "Scroll"));
		scroll = new Scroll();
		rooms = new HashMap <String, Room>; 
	}

	/**
	 * Create all the rooms and link their exits together.
	 */
	private void createRooms() {
		Room outside, theater, pub, lab, office;

		// create the rooms
		outside = new Room("outside the main entrance of the university");
		theater = new Room("in a lecture theater");
		pub = new Room("in the campus pub");
		lab = new Room("in a computing lab");
		office = new Room("in the computing admin office");
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
	}

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
		System.out.println();
		System.out.println("Welcome to the World of Zuul!");
		System.out
				.println("World of Zuul is a new, incredibly boring adventure game.");
		System.out.println("Type 'help' if you need help.");
		System.out.println();
		System.out.println(currentRoom.getLongDescription());
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
			printHelp();
		} else if (commandWord.equals("go")) {
			goRoom(command);
		} else if (commandWord.equals("quit")) {
			wantToQuit = quit(command);
		} else if (commandWord.equals("view")){
			if(command.hasSecondWord()){
				switch(command.getSecondWord()){
				case "bag":
					bag.printInventory();
					break;
				case "scroll":
					scroll.printScroll();
					break;
				default:
					System.out.println("I don't know what you want to view...");
				}
			}
		}
		// else command not recognised.
		return wantToQuit;
	}

	// implementations of user commands:

	/**
	 * Print out some help information. Here we print some stupid, cryptic
	 * message and a list of the command words.
	 */
	private void printHelp() {
		System.out.println("You are lost. You are alone. You wander");
		System.out.println("around at the university.");
		System.out.println();
		System.out.println("Your command words are:");
		parser.showCommands();
	}

	/**
	 * Try to in to one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 */
	private void goRoom(Command command) {
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
			currentRoom = nextRoom;
			System.out.println(currentRoom.getLongDescription());
		}
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
						System.out.println(f.getPath());
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
