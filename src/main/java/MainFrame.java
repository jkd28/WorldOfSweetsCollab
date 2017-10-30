/*
 *	@author William Laboon (GitHub: "laboon"). Primary author of this file, used originally in his "GameOfLife" repository.
 *	@author Benjamin Muscato (GitHub: "BenjaminMuscato"). Found and modified this file for use in the "BitsPlease" repository. 
 * @author Brian Knotten (GitHub: "BK874"). Modified for use in the "BitsPlease" repository.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame{
    // Data for the entire Frame, which will hold all of our Panels
    private JFrame frame;
    private static final int FRAME_HEIGHT = 600;
    private static final int FRAME_WIDTH = 800;
    
    public MainFrame(int numPlayers){
    	// ------------------------ //
		// Validate input arguments //
		// ------------------------ //
    	if(numPlayers < WorldOfSweets.MIN_PLAYERS){
    		System.err.println("Number of players must be a positive integer >= " + WorldOfSweets.MIN_PLAYERS);
			System.exit(1);
    	}

		// ---------------- //
    	// Create the Frame //
		// ---------------- //
    	frame = new JFrame("World of Sweets");
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit entire program when window is closed

    	// ----------------------------------------------- //
		// Create game-board Panel and add it to the Frame //
		// ----------------------------------------------- //
		boardPanel = new boardPaenl();
		frame.add(boardPanel, BorderLayout.NORTH);

		// --------------------------------------------- //
		// Create the deck Panel and add it to the Frame //
		// --------------------------------------------- //
		DeckPanel deckPanel = new DeckPanel();
		frame.add(deckPanel, BorderLayout.WEST);

		frame.setVisible(true);	
    }
 }
