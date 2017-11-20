import org.junit.*;
import static org.junit.Assert.*;
import java.awt.Color;

public class CardTest {
    Card testCard;


    // Test that a card properly returns an uppercase
    // string value for the color
    @Test
    public void testGetColor(){
        testCard = new Card(1, Color.RED);
        assertEquals(Color.RED, testCard.getColor());
    }

    // Test that the card properly returns an int value
    // when the getValue method is called after the card is initialized
    // with a valid value (1 or 2)
    @Test
    public void testGetValue(){
        testCard = new Card(1, Color.BLUE);
        Card testCard2 = new Card(2, Color.YELLOW);

        assertEquals(1, testCard.getValue());
        assertEquals(2, testCard2.getValue());
    }

    // Test that when an invalid value is passed to the card constructor,
    // an IllegalArgumentException is thrown.  This ensures no invalid value
    // (anything other than 1 or 2) can be inserted into a card
    @Test
    public void testInvalidValue(){
        int invalidValue = 132434;
        Color validColor = Color.RED;
        try {
            testCard = new Card(invalidValue, validColor);
            fail();
        } catch (IllegalArgumentException e) {
            // Test passes if we get the error thrown
        }
    }

    @Test
    public void testIllegalColor(){
        int validValue = 1;
        Color invalidColor = Color.WHITE;
        try {
            testCard = new Card(validValue, invalidColor);
            fail();
        } catch (IllegalArgumentException e) {
            // Test passes if we get the error thrown
        }
    }

    @Test
    public void testIsValidColor(){
        Color[] validColors = Card.VALID_COLORS;
        for(Color color : validColors){
            assertTrue(Card.isValidColor(color));
        }

        assertFalse(Card.isValidColor(Color.CYAN));
        assertFalse(Card.isValidColor(Color.LIGHT_GRAY));
    }

    // Test that when a skip card is intialized, it does not throw an error
    @Test
    public void testSkipCardCreation(){
        try {
            Card test = new Card(0, Color.RED);
            Card test2 = new Card(0, Color.BLUE);
        } catch (IllegalArgumentException e) {
            fail("Illegal Argument Exception was thrown on creation of a Skip card.");
        }
    }

    // Test that when a skip-card is initialized with an improper value,
    // it does indeed throw an error
    @Test
    public void testInvalidSkipCardCreation(){
        try {
            Card test = new Card(0, Color.WHITE);
        } catch (IllegalArgumentException e) {
            // Reaching here means the test was successful
        }
    }

    // Test that when a go-to-middle card is initialized with the proper
    // values, no error is thrown
    @Test
    public void testSpecialCardCreation(){
        try {
            Card test = new Card(3, Color.RED);
            Card test2 = new Card(6, Color.BLUE);
        } catch (IllegalArgumentException e) {
            fail("Illegal Argument Exception was thrown on creation of a Go-To-Middle Card");
        }
    }

    // Test that when a middle card with invalid parameters is created,
    // an IllegalArgumentException is indeed thrown
    @Test
    public void testInvalidMiddleCardCreation(){
        try {
            Card test1 = new Card(5, Color.WHITE);
        } catch (IllegalArgumentException e) {
            // Test should pass if error was thrown
        }
    }
}
