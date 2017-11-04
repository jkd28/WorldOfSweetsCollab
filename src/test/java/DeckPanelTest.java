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
	    //	    try{
	    testDeckPanel.drawButton.doClick();
	    //	    } catch (ArithmeticException e) {
	    //		e.printStackTrace();
	    //	    }
	    String testColor = testDeckPanel.getCurrentColor();

	    assertTrue(testColor.equals("RED") || testColor.equals("RED2") ||
		       testColor.equals("YELLOW") || testColor.equals("YELLOW2") ||
		       testColor.equals("BLUE") || testColor.equals("BLUE2") ||
		       testColor.equals("GREEN") || testColor.equals("GREEN2") ||
		       testColor.equals("ORANGE") || testColor.equals("ORANGE2") ||
		       testColor.equals("SKIP"));
	}
    
    // After the draw button is click 60 times there each color should
    // have been displayed ten times as a single card and twice as a
    // double card, with no other color having been displayed.
        @Test
	public void testDeckPanelDraw(){
	    int redCounter,red2Counter, yellowCounter, yellow2Counter,
		blueCounter, blue2Counter, greenCounter,green2Counter,
		orangeCounter, orange2Counter, skipCounter, wrongCounter, total;
	    String testColor;
	    redCounter = red2Counter = yellowCounter = yellow2Counter =
		    blueCounter = blue2Counter = greenCounter = green2Counter =
		orangeCounter = orange2Counter = skipCounter = wrongCounter = 0;

	    for (int i = 0; i < 65; i++){
		testDeckPanel.drawButton.doClick();
		testColor = testDeckPanel.getCurrentColor();

		if (testColor.equals("RED")){
		    redCounter++;
		}else if (testColor.equals("RED2")){
		    red2Counter++;
		}else if (testColor.equals("YELLOW")){
		    yellowCounter++;
		}else if (testColor.equals("YELLOW2")){
		    yellow2Counter++;
		}else if (testColor.equals("BLUE")){
		    blueCounter++;
		}else if (testColor.equals("BLUE2")){
		    blue2Counter++;
		}else if (testColor.equals("GREEN")){
		    greenCounter++;
		}else if (testColor.equals("GREEN2")){
		    green2Counter++;
		}else if (testColor.equals("ORANGE")){
		    orangeCounter++;
		}else if (testColor.equals("ORANGE2")){
		    orange2Counter++;
		} else if (testColor.equals("SKIP")){
		    skipCounter++;
		}else{
		    wrongCounter++;
		}
	    }
	    total = redCounter + red2Counter + yellowCounter + yellow2Counter +
		    blueCounter + blue2Counter + greenCounter + green2Counter +
		orangeCounter + orange2Counter + skipCounter + wrongCounter;

	    assertEquals(0, wrongCounter);
	    assertEquals(10, redCounter);
	    assertEquals(10, yellowCounter);
	    assertEquals(10, blueCounter);
	    assertEquals(10, greenCounter);
	    assertEquals(10, orangeCounter);
	    assertEquals(2, red2Counter);
	    assertEquals(2, yellow2Counter);
	    assertEquals(2, blue2Counter);
	    assertEquals(2, green2Counter);
	    assertEquals(2, orange2Counter);
	    assertEquals(5, skipCounter);
	    assertEquals(65, total);
	}
}    
