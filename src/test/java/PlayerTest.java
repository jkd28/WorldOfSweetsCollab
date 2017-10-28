import org.junit.*;
import static org.junit.Assert.*;

public class PlayerTest{
    Player p;
    Player q;

    @Before
    public void setup(){
        p = new Player("test", 38);
    }

    @Test
    public void testInitialize(){
        assertEquals("test", p.getName());
        assertEquals(38, p.getToken());
    }

    @Test
    public void testPosition(){
        p.setPosition(1);
        assertEquals(1, p.getPosition());
    }

    @Test
    public void testName(){
        p.setName("Kevin");
        assertEquals("Kevin", p.getName());
    }

    @Test
    public void testToken(){
        p.setToken(3);
        assertEquals(3, p.getToken());
    }

    @Test
    public void testposColor(){
        p.setposColor("Orange");
        assertEquals("Orange", p.getposColor());
    }

    @Test
    public void testWins(){
        p.addWin();
        assertEquals(1, p.getWins());
    }
}
