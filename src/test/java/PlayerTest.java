import org.junit.*;
import static org.junit.Assert.*;

public class PlayerTest{
    Player p;

    @Before
    public void setup(){
        p = new Player("testname");
    }

    // Test that the Player is initialized properly with the given name
    @Test
    public void testInitialize(){
        assertEquals("testname", p.getName());
    }

    // Test that a Player returns its current position (BoardSpace)
    @Test
    public void testPosition(){
        BoardSpace space = new BoardSpace();
        p.setPosition(space);
        assertEquals(space, p.getPosition());
    }

    // Test that setName properly sets the name of the Player
    @Test
    public void testName(){
        p.setName("Kevin");
        assertEquals("Kevin", p.getName());
    }
}
