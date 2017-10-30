/*
 * @author Brian Knotten (Github: "BK874"). Primary author of this file, used originally in the "BitsPlease" repository.
 */
import org.junit.*;
import static org.junit.Assert.*;

public class DeckPanelTest{
    DeckPanel testDeckPanel;

    @Before
    public void setup(){
	testDeckPanel = new DeckPanel();
    }

    // The panel should initialize displaying a blank (white) space
    // where the current card will be displayed.
    @Test
    public void testInitialDeckPanelColor(){
	String testColor = testDeckPanel.getCurrentColor();
	assertEquals(testColor, "WHITE");
    }
    
    // Test that a correct color is displayed after clicking the draw button
    @Test
    public void testDeckPanelColor(){
	testDeckPanel.drawButton.doClick();
	String testColor = testDeckPanel.getCurrentColor();

	assertTrue(testColor.equals("RED") || testColor.equals("RED2") ||
		   testColor.equals("YELLOW") || testColor.equals("YELLOW2") ||
		   testColor.equals("BLUE") || testColor.equals("BLUE2") ||
		   testColor.equals("GREEN") || testColor.equals("GREEN2") ||
		   testColor.equals("ORANGE") || testColor.equals("ORANGE2"));
    }
}		   
