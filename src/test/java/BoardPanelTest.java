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
    int numSpaces;

    @Before
    public void setup(){
        Player[] emptyPlayers = new Player[0];
    	testBoardPanel = new BoardPanel(emptyPlayers);
    	numSpaces = testBoardPanel.getNumSpaces();
    }

    // The panel should initialize with a starting space first.
    @Test
    public void testInitialBoardPanelSpace(){
        int index = 0; 

    	BoardSpace testBoardSpace = testBoardPanel.getSpace(index);
    	JLabel testLabel = testBoardSpace.getLabel();
    	String testText = testLabel.getText();

    	assertEquals("<html>Start -></html>", testText);
    }

    // The panel should initialize with an ending space (grandma's house).
    @Test
    public void testFinalBoardPanelSpace(){
        int index = numSpaces - 1;

    	BoardSpace testBoardSpace = testBoardPanel.getSpace(index);
        JLabel testLabel = testBoardSpace.getLabel();
        String testText = testLabel.getText();

    	assertEquals("<html>-> Grandma's House</html>", testText);
    }

    // The middle space should be marked as such.
    @Test
    public void testMiddleBoardPanelSpace(){
        int index = numSpaces / 2;

    	BoardSpace testBoardSpace = testBoardPanel.getSpace(index);
        JLabel testLabel = testBoardSpace.getLabel();
        String testText = testLabel.getText();

    	assertEquals("<html>MID</html>", testText);
    }

    // An average space - at the start of the game, before Player movement - should be marked as such.
    @Test
    public void testAverageBoardPanelSpace(){
        int index = 1;

        BoardSpace testBoardSpace = testBoardPanel.getSpace(index);
        JLabel testLabel = testBoardSpace.getLabel();
        String testText = testLabel.getText();

        assertEquals("<html></html>", testText);
    }
}
    
