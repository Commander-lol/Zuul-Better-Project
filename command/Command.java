package command;

import java.util.ArrayList;

/**
 * This class is part of the "Go Sunset" application. "Go Sunset" is a
 * very simple, text based adventure game.
 * 
 * This class holds information about a command that was issued by the user. A
 * command currently consists of three strings:  a command word, a second word
 * and a third word. (for example, if the command was "give map dwarf", then
 * the three strings obviously are "give", "map" and "dwarf").
 * 
 * The way this is used is: Commands are already checked for being valid command
 * words. If the user entered an invalid command (a word that is not known) then
 * the command word is <null>.
 * 
 * If the command had only one word, then the second and third words are <null>.
 * Similarly if the command only has two words, then the third word is <null>.
 * 
 * @author Michael Kölling and David J. Barnes.
 *         Editted by Louis Capitanchik, Josh Mulcock, Alice Charterton and John Stones.
 * @version 2014/03/04
 */

public class Command {
    private String operator;
    private ArrayList<String> segments;

    /**
     * Create a command object. All words must be supplied, but any (or any
     * combination) of the words can be <null>.
     * 
     * @param operator The main operational word
     */
    public Command(String operator, ArrayList<String> captureGroups) {
        this.operator = operator;
        this.segments = captureGroups;
    }

    /**
     * Return the operator (the first word) of this command. If the command
     * was not understood, the result is <null>.
     * 
     * @return The command word.
     */
    public String getOperator(){
        return operator;
    }
    
    /**
     * Gets the command segment from the Nth position
     * @param n The number of the command segment to get, where the operator
     * is 0, the first operand is 1, the second operand is 2, etc.
     * @return The captured segment of that command
     */
    public String getNthSegment(int n){
        if(n == 0){
            return operator;
        } else if(segments.size() < n){
            return null;
        } else {
            return segments.get(n-1);
        }
    }

    /**
     * Returns whether or not this command was valid or understood
     * @return true if this command was not understood.
     */
    public boolean isUnknown() {
        return (operator == null);
    }

    /**
     * Checks to see if the command has the given number of capture groups
     * @param n The number of capture groups to check against
     * @return Whether or not the command contains at least N capture groups
     */
    public boolean hasNthWord(int n){
        return segments.size()+1 >= n;
    }
}
