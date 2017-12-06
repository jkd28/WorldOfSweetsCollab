/*
* @author Brian Knotten (Github: "BK874"). Primary author of this file, used originally in the "BitsPlease" repository.
*/
import org.junit.*;
import static org.junit.Assert.*;

public class PlayerPanelTest{
    PlayerPanel testPlayerPanel;
    Player[] testPlayers = new Player[4];

    @Before
    public void setup(){
        testPlayers[0] = new Player("Benjamin");
        testPlayers[1] = new Player("Brian");
        testPlayers[2] = new Player("John");
        testPlayers[3] = new Player("Kevin");
        testPlayerPanel = new PlayerPanel(testPlayers);
    }

    // The panel should initialize with the message "First turn!"
    // instead of a current player, the first player's name listed
    // as the next player
    @Test
    public void testInitialPlayerPanelText(){
        String testCurrPlayerMessage = "First turn!";
        String testNextPlayerMessage = "Next Player: \n" + testPlayers[0].getName() + " has " + testPlayers[0].getNumBoomerangs() + " boomerangs";

        assertEquals(testCurrPlayerMessage, testPlayerPanel.getCurrPlayerText());
        assertEquals(testNextPlayerMessage, testPlayerPanel.getNextPlayerText());
    }

    // Test that the PlayerPanel correctly initializes a list of Strings
    // which comprises the player order for the game instance
    @Test
    public void testPlayerOrder(){
        // testPlayOrder is of variable length in case the test cases ever need to change in the future
        String testPlayOrder[] = new String[testPlayers.length+1];
        String actualPlayOrder[] = testPlayerPanel.getOrderText();

        testPlayOrder[0] = "Player Order: \n";
        // Because of the variable length, looping is necessary here
        for (int i = 1; i <= testPlayers.length; i++){
            testPlayOrder[i] = i + ") " + testPlayers[i-1].getName();
        }

        assertArrayEquals(testPlayOrder, actualPlayOrder);
    }
}
