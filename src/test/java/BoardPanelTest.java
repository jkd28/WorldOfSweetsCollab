/*
 * @author Brian Knotten (Github: "BK874"). Primary author of this file, used originally in the "BitsPlease" repository.
 */

import org.junit.*;
import static org.junit.Assert.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BoardPanelTest{
    BoardPanel testBoardPanel;

    @Before
    public void setup(){
    Player[] emptyPlayers = new Player[0];
	testBoardPanel = new BoardPanel(emptyPlayers);
    }

    // The panel should initialize with a starting space first.
    @Test
    public void testInitialBoardPanelSpace(){
	Component testComponent = testBoardPanel.getSpace(0).getComponent(0);
	JLabel testLabel = (JLabel)testComponent;
	String testText = testLabel.getText();
	assertEquals("Start ->", testText);
    }

    // The panel should initialize with an ending space (grandma's house).
    @Test
    public void testFinalBoardPanelSpace(){
	Component testComponent = testBoardPanel.getSpace(1).getComponent(0);
	JLabel testLabel = (JLabel)testComponent;
	String testText = testLabel.getText();
	assertEquals("-> Grandma's House", testText);
    }
}
    
