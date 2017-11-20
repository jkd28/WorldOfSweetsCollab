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
    public void testSpecialSpaces(){
        int index = numSpaces / 5;

    	BoardSpace testBoardSpace1 = testBoardPanel.getSpace(index);
        JLabel testLabel1 = testBoardSpace1.getLabel();
        String testText1 = testLabel1.getText();

	BoardSpace testBoardSpace2 = testBoardPanel.getSpace(index * 2);
	JLabel testLabel2 = testBoardSpace2.getLabel();
	String testText2 = testLabel2.getText();

	BoardSpace testBoardSpace3 = testBoardPanel.getSpace(index * 3);
	JLabel testLabel3 = testBoardSpace3.getLabel();
	String testText3 = testLabel3.getText();

	BoardSpace testBoardSpace4 = testBoardPanel.getSpace(index * 4);
	JLabel testLabel4 = testBoardSpace4.getLabel();
	String testText4 = testLabel4.getText();

	BoardSpace testBoardSpace5 = testBoardPanel.getSpace(index * 5);
	JLabel testLabel5 = testBoardSpace5.getLabel();
	String testText5 = testLabel5.getText();

    	assertEquals("<html>Special 1!</html>", testText1);
	assertEquals("<html>Special 2!</html>", testText2);
	assertEquals("<html>Special 3!</html>", testText3);
	assertEquals("<html>Special 4!</html>", testText4);
	assertEquals("<html>Special 5!</html>", testText5);
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
    
