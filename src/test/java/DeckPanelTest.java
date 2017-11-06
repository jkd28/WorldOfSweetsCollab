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
		Color testColor = testDeckPanel.getCurrentColor();
		assertEquals(testColor, DeckPanel.DEFAUL_COLOR);
    }

    // Test that a correct color is displayed after clicking the draw button
    @Test
    public void testDeckPanelColor(){
		testDeckPanel.drawButton.doClick();

		Color testColor = testDeckPanel.getCurrentColor();

		assertTrue(Card.isValidColor(testColor));
    }

    // After the draw button is click 60 times there each color should
    // have been displayed ten times as a single card and twice as a
    // double card, with no other color having been displayed.
    @Test
    public void testDeckPanelDraw(){
		int redCounter,red2Counter, yellowCounter, yellow2Counter,
		    blueCounter, blue2Counter, greenCounter,green2Counter,
		    orangeCounter, orange2Counter, skipCounter, middleCounter,
		    wrongCounter, total;
		Color testColor;
		Card testCard;
		redCounter = red2Counter = yellowCounter = yellow2Counter =
		    blueCounter = blue2Counter = greenCounter = green2Counter =
		    orangeCounter = orange2Counter = skipCounter = middleCounter =
		    wrongCounter = 0;

		int numCardsInDeck = testDeckPanel.drawDeck.size();

		for (int i = 0; i < numCardsInDeck; i++){
		    testDeckPanel.drawButton.doClick();
		    testCard = testDeckPanel.getCurrentCard();
		    testColor = testDeckPanel.getCurrentColor();
		    testValue = testCard.getValue();


		    if(testValue == Card.SKIP){
		    	skipCounter++;
		    }
		    else if(testValue == Card.GO_TO_MIDDLE){
		    	middleCounter++;
		    }
		    else if(testValue == Card.SINGLE){
		    	if(testColor.equals(Color.RED)){
		    		redCounter++;
		    	}
		    	else if(testColor.equals(Color.YELLOW)){
		    		yellowCounter++;
		    	}
		    	else if(testColor.equals(Color.BLUE)){
		    		blueCounter++;
		    	}
		    	else if(testColor.equals(Color.GREEN)){
		    		greenCounter++;
		    	}
		    	else if(testColor.equals(Color.ORANGE)){
		    		orangeCounter++;
		    	}
		    	else{
		    		wrongCounter++;
		    	}
		    }
		    else if(testValue == Card.DOUBLE){
		    	if(testColor.equals(Color.RED)){
		    		red2Counter++;
		    	}
		    	else if(testColor.equals(Color.YELLOW)){
		    		yellow2Counter++;
		    	}
		    	else if(testColor.equals(Color.BLUE)){
		    		blue2Counter++;
		    	}
		    	else if(testColor.equals(Color.GREEN)){
		    		green2Counter++;
		    	}
		    	else if(testColor.equals(Color.ORANGE)){
		    		orange2Counter++;
		    	}
		    	else{
		    		wrongCounter++;
		    	}
		    }
		    else{
		    	wrongCounter++;
		    }

		}
		total = redCounter + red2Counter + yellowCounter + yellow2Counter +
		    blueCounter + blue2Counter + greenCounter + green2Counter +
		    orangeCounter + orange2Counter + skipCounter + middleCounter +
		    wrongCounter;

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
		assertEquals(3, middleCounter);
		assertEquals(numCardsInDeck, total);
    }
}
