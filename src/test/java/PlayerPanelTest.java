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
    // as the next player, and a list of player names in turn order.
    @Test
    public void testInitialPlayerPanel(){
	String testCurrPlayerMessage = "First turn!";
	String testNextPlayerMessage = "Next Player: \n" + testPlayers[0].getName();
	String testPlayOrder[] = new String[testPlayers.length+1];
	String actualPlayOrder[] = testPlayerPanel.getOrderText();
	
	testPlayOrder[0] = "Player Order: \n";
	for (int i = 1; i <= testPlayers.length; i++){
	    testPlayOrder[i] = i + ") " + testPlayers[i-1].getName();
	}

	assertEquals(testCurrPlayerMessage, testPlayerPanel.getCurrPlayerText());
	assertEquals(testNextPlayerMessage, testPlayerPanel.getNextPlayerText());

	for (int i = 0; i <= testPlayers.length; i++){
	    assertEquals(testPlayOrder[i], actualPlayOrder[i]);
	}
    }
}
