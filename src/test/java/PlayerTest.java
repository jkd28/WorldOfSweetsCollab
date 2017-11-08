import org.junit.*;
import static org.junit.Assert.*;

public class PlayerTest{
    Player p;

    @Before
    public void setup(){
        p = new Player("test");
    }

    @Test
    public void testInitialize(){
        assertEquals("test", p.getName());
    }

    @Test
    public void testPosition(){
        BoardSpace space = new BoardSpace();
        p.setPosition(space);
        assertEquals(space, p.getPosition());
    }

    @Test
    public void testName(){
        p.setName("Kevin");
        assertEquals("Kevin", p.getName());
    }
}
