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
    private static final Font BUTTON_FONT = new Font("Courier", Font.PLAIN, 48);
    private static final int BOARD_COLUMNS = 10;
    private static final int BOARD_ROWS = 7;
    private static final int BOARD_HORIZONTAL_GAP = 20;
    private static final int BOARD_VERTICAL_GAP = 10;
    private static final GridLayout BOARD_LAYOUT = new GridLayout(BOARD_ROWS, BOARD_COLUMNS, BOARD_VERTICAL_GAP, BOARD_HORIZONTAL_GAP);
    
    public BoardPanel(){
	setLayout(BOARD_LAYOUT);

	// Add the start and end (grandma's house) spaces
	spaces = new JPanel[BOARD_COLUMNS * BOARD_ROWS];
	spaces[0] = new JPanel();
	spaces[0].add(start);
	spaces[0].setBorder(BorderFactory.createLineBorder(Color.black));
	spaces[1] = new JPanel();
	spaces[1].add(grandma);
	spaces[1].setBorder(BorderFactory.createLineBorder(Color.black));
	add(spaces[0]);

	// Add all of the colored spaces to the board
	for(int i = 2; i < spaces.length; i++){
	    spaces[i] = new JPanel();
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
	add(spaces[1]);

    }
}
