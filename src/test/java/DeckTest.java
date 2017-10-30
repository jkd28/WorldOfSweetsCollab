import org.junit.*;
import static org.junit.Assert.*;

public class DeckTest {
    Deck testDeck;

    @Before
    public void setup(){
        testDeck = new Deck();
    }

    // The deck of cards contains 60 cards.  After drawing all
    // cards, the deck needs to reshuffule and should never return a null.
    // This test assures that the deck reshuffles, and will fail if a
    // NullPointerException is thrown or a null is returned from the draw method.
    @Test
    public void testDeckReshuffle(){
        for(int i = 0; i < 60; i++){
            testDeck.draw();
        }
        Card testCard = testDeck.draw();
        assertNotNull(testCard);
    }

    // Test that a card is returned when the draw method is called on the deck 
    @Test
    public void testDraw(){
        Card testCard = testDeck.draw();
        assertNotNull(testCard);
    }
}
