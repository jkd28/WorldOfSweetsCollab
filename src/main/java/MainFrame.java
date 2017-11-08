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
    private static BoardPanel boardPanel;

    // Data for the Deck Panel
    private DeckPanel deckPanel;

    // Data for tracking Players
    private static Player[] players;
    private static int NUM_PLAYERS = 0;

    // Data for currentPlayer
    public static int currentPlayerIndex = 0; // The player first to go will always be player 0, regardless of the number of players
    private static PlayerPanel playerPanel;

    // --------------------------------------- //
    // Calling this will return the player who //
    // is up next and advance currentPlayer    //
    // --------------------------------------- //
    public static Player getNextPlayer(){
    	Player currentPlayer = players[currentPlayerIndex];

    	playerPanel.changePlayer(currentPlayer);

    	currentPlayerIndex = (currentPlayerIndex + 1) % NUM_PLAYERS;

    	return currentPlayer;
    }
    public static Player getCurrentPlayer(){
    	return players[currentPlayerIndex];
    }

    public static int getNumPlayers(){
    	return NUM_PLAYERS;
    } 

    public static void updatePlayerPosition(Player player, Card card){
    	// Get the BoardSpace that this Player currently inhabits
	    int currentSpaceIndex = player.getPosition();
	    BoardSpace currentSpace = boardPanel.getSpace(currentSpaceIndex);

	    // If this spot is "Grandma's House", we do not move anywhere
	    if(currentSpace.isGrandmasHouse()){
	    	return;
	    }

	    // If this card is a "Skip" card, we do nothing
	    if(card.getValue() == Card.SKIP){
	    	return;
	    }

	    // If this card is a "Go to Middle" card, send the Player directly to the middle of the board
	    if(card.getValue() == Card.GO_TO_MIDDLE){
	    	boardPanel.sendPlayerToMiddleSpace(player);
	    }

	    // With a normal Single or Double colored card,
	    //	send the Player to their next spot.
	    boardPanel.sendPlayerToNextSpace(player, card);
    }

    public static boolean playerHasWon(Player player){
    	BoardSpace currentPlayerSpace = boardPanel.getSpace(player.getPosition());
    	return currentPlayerSpace.isGrandmasHouse();
    }

    public MainFrame(int numPlayers){
        NUM_PLAYERS = numPlayers;
    	
    	// ------------------------ //
		// Validate input arguments //
		// ------------------------ //
    	if(NUM_PLAYERS < WorldOfSweets.MIN_PLAYERS || NUM_PLAYERS > WorldOfSweets.MAX_PLAYERS){
    		String message = String.format("Number of players must be a positive integer between %d and %d!", WorldOfSweets.MIN_PLAYERS, WorldOfSweets.MAX_PLAYERS);
    		JOptionPane.showMessageDialog(null, 
				message,
				"Invalid Number of Players",
				JOptionPane.ERROR_MESSAGE);
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
		players = new Player[NUM_PLAYERS];


		for(int i = 0; i < players.length; i++){

			String playerName = "Player "+i;
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

			players[i] = newPlayer;
		}

		// ----------------------------------------------- //
		// Create game-board Panel and add it to the Frame //
		// ----------------------------------------------- //
		boardPanel = new BoardPanel(players);
		frame.add(boardPanel, BorderLayout.NORTH);

		//Set all players to starting boardspace (index 0)
		for(Player player : players){
			player.setPosition(boardPanel.getSpace(0));
		}

		// --------------------------------------------- //
		// Create the deck Panel and add it to the Frame //
		// --------------------------------------------- //
		deckPanel = new DeckPanel();
		frame.add(deckPanel, BorderLayout.WEST);

        // ----------------------------------------------- //
		// Create the player Panel and add it to the Frame //
		// ----------------------------------------------- //
        playerPanel = new PlayerPanel(players[0]);
        frame.add(playerPanel, BorderLayout.EAST);

		// Make it all visible!
		frame.setVisible(true);
    }
 }
