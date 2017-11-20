/*
* @author Brian Knotten (Github: "BK874"). Primary author of this file, used originally in the "BitsPlease" repository.
*/
import org.junit.*;
import static org.junit.Assert.*;
import java.awt.Color;

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
        assertEquals(testColor, DeckPanel.DEFAULT_COLOR);
    }

    // Test that a correct color is displayed after clicking the draw button
    @Test
    public void testDeckPanelColor(){
        testDeckPanel.getDrawButton().doClick();

        Color testColor = testDeckPanel.getCurrentColor();

        assertEquals(true, Card.isValidColor(testColor));
    }

    // After the draw button is clicked through the deck, each color should
    // have been displayed ten times as a single card and twice as a
    // double card, with no other color having been displayed.
    @Test
    public void testDeckPanelDraw(){
        int redCounter,red2Counter, yellowCounter, yellow2Counter,
        blueCounter, blue2Counter, greenCounter,green2Counter,
        orangeCounter, orange2Counter, skipCounter, specialCounter,
        wrongCounter, total;
        Color testColor;
        Card testCard;
        int testValue;
        redCounter = red2Counter = yellowCounter = yellow2Counter =
        blueCounter = blue2Counter = greenCounter = green2Counter =
        orangeCounter = orange2Counter = skipCounter = specialCounter =
        wrongCounter = 0;

        int numCardsInDeck = testDeckPanel.getDrawDeck().size();

        // Click through the deck, counting each type of card as it appears
        for (int i = 0; i < numCardsInDeck; i++){
            testDeckPanel.getDrawButton().doClick();
            testCard = testDeckPanel.getCurrentCard();
            testColor = testDeckPanel.getCurrentColor();
            testValue = testCard.getValue();


            if(testValue == Card.SKIP){
                skipCounter++;
            }
            else if(testValue >= Card.GO_TO_FIRST_SPECIAL){
                specialCounter++;
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
        orangeCounter + orange2Counter + skipCounter + specialCounter +
        wrongCounter;

        // Verify that each type of card has been counted the proper number of times
        assertEquals(0, wrongCounter);
        assertEquals(Deck.NUM_SINGLE_CARDS_PER_COLOR, redCounter);
        assertEquals(Deck.NUM_SINGLE_CARDS_PER_COLOR, yellowCounter);
        assertEquals(Deck.NUM_SINGLE_CARDS_PER_COLOR, blueCounter);
        assertEquals(Deck.NUM_SINGLE_CARDS_PER_COLOR, greenCounter);
        assertEquals(Deck.NUM_SINGLE_CARDS_PER_COLOR, orangeCounter);
        assertEquals(Deck.NUM_DOUBLE_CARDS_PER_COLOR, red2Counter);
        assertEquals(Deck.NUM_DOUBLE_CARDS_PER_COLOR, yellow2Counter);
        assertEquals(Deck.NUM_DOUBLE_CARDS_PER_COLOR, blue2Counter);
        assertEquals(Deck.NUM_DOUBLE_CARDS_PER_COLOR, green2Counter);
        assertEquals(Deck.NUM_DOUBLE_CARDS_PER_COLOR, orange2Counter);
        assertEquals(Deck.NUM_GO_TO_CARDS * 5, specialCounter);
        assertEquals(Deck.NUM_SKIP_CARDS, skipCounter);
        assertEquals(numCardsInDeck, total);
    }
}
