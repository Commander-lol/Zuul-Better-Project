package command;

import util.KvReader;

import java.util.HashMap;
/**
 * This class is part of the "Go Sunset" application. "Go Sunset" is a
 * very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game. It is
 * used to recognise commands as they are typed in.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2011.08.08
 */

public class CommandWords {
    // a constant array that holds all valid command words
    // validCommands = { "go", "quit", "help", "view", "back", "take", "use", "give", "combine"};
    private HashMap<String, String> helpText;
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords() {
        helpText = new HashMap<String, String>();
        helpText = KvReader.readFile("help.kv");
    }

    /**
     * Check whether a given String is a valid command word.
     * 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString) {
        return helpText.keySet().contains(aString);
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() {
        for (String command : helpText.keySet()) {
            System.out.print("▶ " + command + "  ");
        }
        System.out.println();
    }
}
