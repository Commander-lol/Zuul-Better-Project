package state;
import entities.*;
import state.*;
import command.*;
import util.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class GameTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GameTest
{
    /**
     * Default constructor for test class GameTest
     */
    public GameTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void loadGameTest()
    {
        state.Game game2 = new state.Game();
    }

    @Test
    public void enterWrongCommand()
    {
        
    }
    
    

    //@Test
    //public void testGame()
    //{
    //    state.Game game1 = new state.Game();
    //    game1.play();
    //}

    @Test
    public void testBack()
    {
        state.Game game1 = new state.Game();
    }

    @Test
    public void testTrade()
    {
        state.Game game1 = new state.Game();
        game1.play();
    }
}







