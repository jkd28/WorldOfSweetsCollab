/*
 * @author Brian Knotten (Github: "BK874"). Primary author of this file, used originally in the "BitsPlease" repository.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BoardPanel extends JPanel{
    
    // Data for the BoardPanel
    private BoardSpace[] spaces;
    private JLabel start = new JLabel("Start ->");
    private JLabel grandma = new JLabel("-> Grandma's House");
    private static final Font BUTTON_FONT = new Font("Courier", Font.PLAIN, 48);
    private static final int BOARD_COLUMNS = 10;
    private static final int BOARD_ROWS = 7;
    private static final int BOARD_HORIZONTAL_GAP = 20;
    private static final int BOARD_VERTICAL_GAP = 10;
    private static final GridLayout BOARD_LAYOUT = new GridLayout(BOARD_ROWS, BOARD_COLUMNS, BOARD_VERTICAL_GAP, BOARD_HORIZONTAL_GAP);
    
    public BoardPanel(Player[] players){
		setLayout(BOARD_LAYOUT);

		// ----------------- //
		// Create the Spaces //
		// ----------------- //
		spaces = new BoardSpace[BOARD_COLUMNS * BOARD_ROWS];
		
		// Create the "Start" space
		spaces[0] = new BoardSpace(Color.WHITE, start, players);

		// Create all of the colored spaces
		for(int i = 1; i < spaces.length - 1; i++){
		    Color backgroundColor = Color.WHITE;
		    switch(i % 5){
		    	case 0: backgroundColor = Color.RED; 	break;
		    	case 1: backgroundColor = Color.YELLOW; break;
		    	case 2: backgroundColor = Color.BLUE; 	break;
		    	case 3: backgroundColor = Color.GREEN; 	break;
		    	case 4: backgroundColor = Color.ORANGE; break;
		    }
		    spaces[i] = new BoardSpace(backgroundColor);
		}

		// Create the end space ("Grandma's House")
		spaces[spaces.length - 1] = new BoardSpace(Color.WHITE, grandma);
		
		// --------------------------- //
		// Add the Spaces to the board //
		// --------------------------- //
		for(BoardSpace space : spaces){
			add(space);
		}
    }

    // Retrieve a specific board space
    public JPanel getSpace(int index){
		return (JPanel)spaces[index];
    }
}
