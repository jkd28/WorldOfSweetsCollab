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

    // Data for the Board Panel
    private BoardPanel boardPanel;

    // Data for the Deck Panel
    private DeckPanel deckPanel;

    // Data for tracking Players
    private Player[] players;
    
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


		// ------------------ //
		// Create the Players //
		// ------------------ //
		players = new Player[numPlayers];
		for(int i = 0; i < players.length; i++){

			String playerName = "Player " + i;
			while(true){
				playerName = JOptionPane.showInputDialog(null, "What is the name of player #"+i+"?", playerName);
				if(playerName == null || playerName.equals("")){
					JOptionPane.showMessageDialog(null, 
						"I'm sorry, that's not a valid name for player #"+i+", please try again.",
						"Invalid Player Name",
						JOptionPane.WARNING_MESSAGE
						);
					continue;
				}
				break;
			}
				

			Player newPlayer = new Player(playerName);
			newPlayer.setPosition(0);

			players[i] = newPlayer;
		}



    	// ----------------------------------------------- //
		// Create game-board Panel and add it to the Frame //
		// ----------------------------------------------- //
		boardPanel = new BoardPanel(players);
		frame.add(boardPanel, BorderLayout.NORTH);


		// --------------------------------------------- //
		// Create the deck Panel and add it to the Frame //
		// --------------------------------------------- //
		deckPanel = new DeckPanel();
		frame.add(deckPanel, BorderLayout.WEST);


		// Make it all visible!
		frame.setVisible(true);	
    }
 }
