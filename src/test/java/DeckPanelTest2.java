/*
 * @author Brian Knotten (Github: "BK874"). Primary author of this file, used originally in the "BitsPlease" repository.
 */
import org.junit.*;
import static org.junit.Assert.*;

public class DeckPanelTest2{
    DeckPanel testDeckPanel;

    @Before
    public void setup(){
	testDeckPanel = new DeckPanel();
    }

    // After the draw button is click 60 times there each color should
    // have been displayed ten times as a single card and twice as a
    // double card, with no other color having been displayed.
    @Test
    public void testDeckPanelDraw(){
	int redCounter,red2Counter, yellowCounter, yellow2Counter,
	    blueCounter, blue2Counter, greenCounter,green2Counter,
	    orangeCounter, orange2Counter, wrongCounter, total;
	String testColor;
	redCounter = red2Counter = yellowCounter = yellow2Counter =
	    blueCounter = blue2Counter = greenCounter = green2Counter =
	    orangeCounter = orange2Counter = wrongCounter = 0;

	for (int i = 0; i < 60; i++){
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
	    }else{
		wrongCounter++;
	    }
	}
	total = redCounter + red2Counter + yellowCounter + yellow2Counter +
	    blueCounter + blue2Counter + greenCounter + green2Counter +
	    orangeCounter + orange2Counter + wrongCounter;
	
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
	assertEquals(60, total);
    }
}	    
