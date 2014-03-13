package entities;
import java.util.Set;
import java.util.HashMap;
import state.Game;

/**
 * The class SpecialRoom is a child class of the Class Room in thr adventure game 'Go Sunset'. Its function is to be used when the
 * player enters a 'Special Room'. The differences are that this Class includes the informations for Riddles, which are used when the 
 * player enters a Special Room.
 * 
 * @author Josh Mulcock, Louis Capitanchik, John Stones, Alice Charterton 
 * @version 2014.03.09
 */
public class SpecialRoom extends Room
{
    private String riddle;
    private String riddleAnswer;
    private String answer;
    
    /**
     * Creates a SpecialRoom class. Takes information from the Room parent class.
     * 
     * @param description The room's description.
     * @param context An instance of the Game class.
     */
    public SpecialRoom (String description, Game context) {
        super(description, context);
    }
    
    public SpecialRoom(Game context, HashMap<String, String> attributes){
        super(context, attributes);
        // Riddle parts are still going to be in the HashMap, remove them
        this.riddle = this.exits.remove("riddle");
        this.riddleAnswer = this.exits.remove("answer");
    }
    
    /**
     * @return The riddle for the current special room.
     */
    public String getRiddle(){
        return riddle;
    }
    
    /**
     * Checks whether the answer entered by the user is equal to the actual answer
     * of the riddle.
     * 
     * @param userAnswer The answer entered by the User.
     * @return Whether the answers were euqal or not.
     */
    public boolean isAnswerCorrect(String userAnswer) {
        boolean answerCorrect;
        answer = userAnswer;
        
        if (answer == riddleAnswer) {
            answerCorrect = true;
        }
        else {
            answerCorrect = false;
        }
        
        return answerCorrect;
    }
}
