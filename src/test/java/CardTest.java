import org.junit.*;
import static org.junit.Assert.*;

public class CardTest {
    Card testCard;

    @Test
    public void testCardGetColor(){
        testCard = new Card(1, "test");
        assertEquals("TEST", testCard.getColor());
    }

    @Test
    public void testCardGetValue(){
        testCard = new Card(1, "test");
        assertEquals(1, testCard.getValue());
    }
}
