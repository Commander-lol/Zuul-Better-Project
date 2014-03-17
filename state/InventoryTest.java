package state;
import entities.Item;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class InventoryTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class InventoryTest
{
    /**
     * Default constructor for test class InventoryTest
     */
    public InventoryTest()
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
    public void inventoryTest() {
        Inventory testInv = new Inventory();
        Item testItem = new Item(30,"test1");
                
        testInv.addItem(testItem);
        testInv.printInventory("in the test");
        testInv.removeItem("test1");
        testInv.printInventory("in the test");
    }
    
    @Test
    public void tooHeaveyItemTest(){
        Inventory testInv = new Inventory();
        Item heaveyTestItem = new Item(1000,"test1");
        
        testInv.printInventory("in the heavey test");
        testInv.addItem(heaveyTestItem);
        testInv.printInventory("in the heavey after add attempt test");
    }

    
}

