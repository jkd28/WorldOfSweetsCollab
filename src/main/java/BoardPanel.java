/*
 * @author Brian Knotten (Github: "BK874"). Primary author of this file, used originally in the "BitsPlease" repository.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BoardPanel extends JPanel{
    
    // Data for the BoardPanel
    private JPanel[] spaces;
    private JLabel start = new JLabel("Start ->");
    private JLabel grandma = new JLabel("-> Grandma's House");
    private JLabel midLabel = new JLabel("MID");
    private static final Font BUTTON_FONT = new Font("Courier", Font.PLAIN, 48);
    private static final int BOARD_COLUMNS = 8;
    private static final int BOARD_ROWS = 8;
    private static final int BOARD_HORIZONTAL_GAP = 20;
    private static final int BOARD_VERTICAL_GAP = 10;
    private static final GridLayout BOARD_LAYOUT = new GridLayout(BOARD_ROWS, BOARD_COLUMNS, BOARD_VERTICAL_GAP, BOARD_HORIZONTAL_GAP);
    private static final int NUM_SPACES = BOARD_COLUMNS * BOARD_ROWS;
    private static final int MID_SPACE = (NUM_SPACES / 2); // The middle space. When even # spaces, the one closer to the final space
    
    public BoardPanel(Player[] players){
	setLayout(BOARD_LAYOUT);

	spaces = new JPanel[NUM_SPACES];
	spaces[0] = new JPanel();

	// Add the player names
	if(players.length > 0){
	    String labelText = start.getText() + " (";
	    for(Player player : players){
		labelText = labelText + player.getName() + ", ";
	    }
	    labelText = labelText.substring(0, labelText.length() - 2);
	    labelText = labelText + ")";
	    spaces[0].add(new JLabel(labelText));
	}
	else{
	    spaces[0].add(start);
	}

	// Add the start space
	spaces[0].setBorder(BorderFactory.createLineBorder(Color.black));
	add(spaces[0]);

	// Add all of the colored spaces tpo the board
	for(int i = 1; i < spaces.length-1; i++){
	    // Mark the middle space
	    if (i == MID_SPACE){
		spaces[i] = new JPanel(new GridBagLayout());
		spaces[i].add(midLabel);
	    } else {
		spaces[i] = new JPanel();
	    }
	    
	    if (i % 5 == 2){
		spaces[i].setBackground(Color.RED);
	    }else if (i % 5 == 3){
		spaces[i].setBackground(Color.YELLOW);
	    }else if (i % 5 == 4){
		spaces[i].setBackground(Color.BLUE);
	    }else if (i % 5 == 0){
		spaces[i].setBackground(Color.GREEN);
	    }else if (i % 5 == 1){
		spaces[i].setBackground(Color.ORANGE);
	    }
	    spaces[i].setBorder(BorderFactory.createLineBorder(Color.black));
	   	    
	    add(spaces[i]);
	}

	// Add the end (grandma's house) space
	spaces[NUM_SPACES-1] = new JPanel();
	spaces[NUM_SPACES-1].add(grandma);
	spaces[NUM_SPACES-1].setBorder(BorderFactory.createLineBorder(Color.black));
	add(spaces[NUM_SPACES-1]);
    }

    // Retrieve a specific board space
    public JPanel getSpace(int index){
	return spaces[index];
    }

    // Retrieve the number of spaces 
    public int getNumSpaces(){
	return spaces.length;
    }
}
