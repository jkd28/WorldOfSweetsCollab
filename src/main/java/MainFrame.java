/*
*	@author William Laboon (GitHub: "laboon"). Primary author of this file, used originally in his "GameOfLife" repository.
*	@author Benjamin Muscato (GitHub: "BenjaminMuscato"). Found and modified this file for use in the "BitsPlease" repository. 
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame{

    private static final int FRAME_HEIGHT = 600;
    private static final int FRAME_WIDTH = 800;
    private static final int MIN_PLAYERS = 2;
    
    private JFrame frame;

    private JPanel boardPanel;
    private JButton[] boardButtons;
    private static final Font BUTTON_FONT = new Font("Courier", Font.PLAIN, 48);
    private static final int BOARD_COLUMNS = 6;
    private static final int BOARD_ROWS = 4;
    private static final int BOARD_HORIZONTAL_GAP = 30;
    private static final int BOARD_VERTICAL_GAP = 30;
    //private MainPanel mainPanel;

    //private ButtonPanel buttonPanel;
    
    public MainFrame(int numPlayers){

    	if(numPlayers < WorldOfSweets.MIN_PLAYERS){
    		System.err.println("Number of players must be a positive integer >= " + WorldOfSweets.MIN_PLAYERS);
			System.exit(1);
    	}

    	// Create the Frame
    	frame = new JFrame("World of Sweets");
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit entire program when window is closed
		
		// Add Board Panel
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(BOARD_ROWS, BOARD_COLUMNS, BOARD_VERTICAL_GAP, BOARD_HORIZONTAL_GAP));
		frame.add(boardPanel, BorderLayout.NORTH);
		
		// Add Buttons to the Board Panel
		boardButtons = new JButton[BOARD_COLUMNS * BOARD_ROWS];
		for(int i = 0; i < boardButtons.length; i++){
		    boardButtons[i] = new JButton("_");
		    boardButtons[i].addActionListener((ActionListener) new ButtonListener());
		    boardButtons[i].setFont(BUTTON_FONT);

		    boardPanel.add(boardButtons[i]);
		}


		
		frame.setVisible(true);	
    }

    class ButtonListener implements ActionListener {
		// Every time we click the button, it will perform
		// the following action.
		public void actionPerformed(ActionEvent e) {
		    JButton source = (JButton) e.getSource();
		    String currentText = source.getText();
		    if (currentText.equals("_")) {
				source.setText("X");
		    } else {
				source.setText("_");
		    }
		}
	    
	}
    
}
