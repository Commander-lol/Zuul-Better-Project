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
    private String answer;
    private Item reward;
    private boolean complete = false;
    
    /**
     * Creates a SpecialRoom class. Takes information from the Room parent class.
     * 
     * @param description The room's description.
     * @param context An instance of the Game class.
     */
    public SpecialRoom (String description, Game context) {
        super(description, context);
    }
    
    /**
     * Creates an instance of SpecialRoom, a room that contains a riddle that should be answered and extends the Room class
     * 
     * @param context The Game object that the room belongs to
     * @param attributes The attributes that make up the game
     */
    public SpecialRoom(Game context, HashMap<String, String> attributes){
        super(context, attributes);
        // Riddle parts are still going to be in the HashMap, remove them
        this.riddle = this.exits.remove("riddle");
        this.answer = this.exits.remove("answer");
    }
    
    /**
     * @return The riddle for the current special room.
     */
    public String getRiddle(){
        return riddle;
    }
    
    public void setReward(Item reward){
        this.reward = reward;
    }
    
    /**
     * Gets the item from this room that has been specified as the reward for succesfully completing the riddle
     * @return The reward item for getting the correct answer
     */
    public Item getReward() {
        return reward;
    }
    /**
     * Checks whether the answer entered by the user is equal to the actual answer
     * of the riddle.
     * 
     * @param answer The answer entered by the User.
     * @return Whether the answers were equal or not.
     */
    public boolean attemptAnswer(String answer) {
        return complete = this.answer.equalsIgnoreCase(answer);
    }
    
    public boolean complete(){
        return complete;
    }
}
