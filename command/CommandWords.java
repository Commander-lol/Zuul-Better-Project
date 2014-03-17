package command;

import util.KvReader;

import java.util.HashMap;
import java.util.Map.Entry;
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
    
    private HashMap<String, String> commands;
    private HashMap<String, int[]> captureGroups;
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords() {
        commands = new HashMap<String, String>();
        captureGroups = new HashMap<String, int[]>();
        HashMap<String, String> commandFile = KvReader.readFile("help.kv");
        for(Entry<String, String> e : commandFile.entrySet()){
            String eKey = e.getKey();
            if(eKey.substring(eKey.length()-1).equals("$")){
                captureGroups.put(eKey, parseCaptureGroups(e.getValue()));
            } else {
                commands.put(eKey, e.getValue());
            }
        }
    }
    
    /**
     * Reads the simplistic capture syntax that defines how many words should be put 
     * into a capture group for parsing input
     * @oaram input The string that should be parsed and turned into capture groups
     * @return An integer array that defines how many words should be in each captured segment 
     * of user input
     */
    private int[] parseCaptureGroups(String input){
        String[] groups = input.split("\\^");
        int[] returnArray = new int[groups.length];
        for (int i = 0; i < groups.length; i++){
            try{
                returnArray[i] = Integer.valueOf(groups[i]);
            } catch(NumberFormatException e){
                System.err.println("NaN error in input " + input + ", token " + groups[i]);
                //Better to return null than an incorrect array, allows for detection
                return null;
            }
        }
        return returnArray;
    }

    /**
     * Check whether a given String is a valid command word.
     * @param aString The command word being checked
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString) {
        return commands.keySet().contains(aString);
    }
    /**
     * Gets the capture group associated with the specified command
     * @param command The command whose capture group is to be returned
     * @return The capture group specifying how to parse input related to this command
     */
    public int[] getCaptureGroups(String command){
        return captureGroups.get(command + "$");
    }
    /**
     * Print all valid commands to System.out.
     */
    public void showAll() {
        int cols = 0;
        for (String command : commands.keySet()) {
            if(cols>=5){
                System.out.println();
                cols = 0;
            }
            System.out.print("▶ " + command + "  ");
            cols++;
        }
        System.out.println();
    }
    /**
     * Prints out the extended help message for a given command, or an error if it doesn't exist
     */
    public void show(String command){
        String helpText = commands.get(command);
        if(helpText == null){
            System.out.println(command + " isn't a command.");
        } else {
            System.out.println(helpText);
        }
    }
}
