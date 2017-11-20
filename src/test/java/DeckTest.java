import org.junit.*;
import static org.junit.Assert.*;

public class DeckTest {
    Deck testDeck;

    @Before
    public void setup(){
        testDeck = new Deck();
    }

    // The deck of cards contains 70 cards.  After drawing all
    // cards, the deck needs to reshuffule and should never return a null.
    // This test assures that the deck reshuffles, and will fail if a
    // NullPointerException is thrown or a null is returned from the draw method.
    @Test
    public void testDeckReshuffle(){
        int deckSize = testDeck.size();
        for(int i = 0; i < deckSize; i++){
            testDeck.draw();
        }
        // draw the last card from the deck
        Card testCard = testDeck.draw();
        assertNotNull(testCard);
    }

    // Test that a card is returned when the draw method is called on the deck
    @Test
    public void testDraw(){
        Card testCard = testDeck.draw();
        assertNotNull(testCard);
    }

    // Test that exactly 5 skip cards are present in a new deck
    @Test
    public void testNumSkipCards(){
        int skipCount = 0;
        int deckSize = testDeck.size();

        for (int i = 0; i < deckSize; i++){
            Card drawn = testDeck.draw();
            if (drawn.getValue() == Card.SKIP) {
                skipCount++;
            }
        }

        assertEquals(Deck.NUM_SKIP_CARDS, skipCount);
    }

    // Test that exactly 5 Go-To-Special cards are present in a new deck
    @Test
    public void testNumMiddleCards(){
        int specialCount = 0;
        int deckSize = testDeck.size();

        for (int i = 0; i < deckSize; i++){
            Card drawn = testDeck.draw();
            if (drawn.getValue() >= Card.GO_TO_FIRST_SPECIAL) {
                specialCount++;
            }
        }

        assertEquals(Deck.NUM_GO_TO_CARDS * 5, specialCount);
    }
}
