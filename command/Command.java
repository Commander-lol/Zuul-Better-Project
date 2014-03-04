package command;

/**
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
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
 * @version 2011.08.08
 */

public class Command {
    private String commandWord;
    private String secondWord;
    private String thirdWord;

    /**
     * Create a command object. All words must be supplied, but any (or any
     * combination) of the words can be <null>.
     * 
     * @param firstWord
     *            The first word of the command. <Null> if the command was not
     *            recognised.
     * @param secondWord
     *            The second word of the command.
     * @param thirdWord
     *            The third word of the command.
     */
    public Command(String firstWord, String secondWord, String thirdWord) {
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    /**
     * Return the command word (the first word) of this command. If the command
     * was not understood, the result is <null>.
     * 
     * @return The command word.
     */
    public String getCommandWord() {
        return commandWord;
    }

    /**
     * Return the second word of this command. If no second word was supplied or
     * the word was not understood, the result is <null>.
     * 
     * @return The second word.
     */
    public String getSecondWord() {
        return secondWord;
    }
    
    /**
     * Return the third word of this command. If no third word was supplied or
     * the word was not understood, the result is <null>.
     * 
     * @return The third word.
     */
    public String getThirdWord() {
        return thirdWord;
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown() {
        return (commandWord == null);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord() {
        return (secondWord != null);
    }
    
    /**
     * @return true if the command has a third word.
     */
    public boolean hasThirdWord() {
        return (thirdWord != null);
    }
}
