import org.junit.*;
import static org.junit.Assert.*;

public class CardTest {
    Card testCard;


    // Test that a card properly returns an uppercase
    // string value for the color
    @Test
    public void testCardGetColor(){
        testCard = new Card(1, "test");
        assertEquals("TEST", testCard.getColor());
    }

    // Test that the card properly returns an int value
    // when the getValue method is called
    @Test
    public void testCardGetValue(){
        testCard = new Card(1, "test");
        assertEquals(1, testCard.getValue());
    }
}
