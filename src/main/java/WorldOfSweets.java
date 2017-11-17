import java.io.File;
import java.io.Serializable;
import javax.swing.Action;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicFileChooserUI;

public class WorldOfSweets implements Serializable {

	// Basic game information
	public static final int MIN_PLAYERS = 2; 	// These are "public" because our "MainFrame" class also uses this for input validation
												//		It may eventually get moved to whatever "GameplayDriver" sort of class
												//		that gets made.
	public static final int MAX_PLAYERS = 4;


  	private static int getNumPlayersFromUser(){
		// Create the list of options
		int numOptions = MAX_PLAYERS - MIN_PLAYERS + 1;
		Object[] options = new Object[numOptions];
		for(int i = 0; i < options.length; i++){
			options[i] = i + MIN_PLAYERS;
		}

		// Ask the user for the number of players
		int dialogResult = JOptionPane.showOptionDialog(null,
			"Welcome to 'World of Sweets'!\nHow many players will be in this game?",
			"Welcome!",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[0]);

	    // If the user hits "Cancel" or the Exit button, quit the program
		if (dialogResult == JOptionPane.CLOSED_OPTION){
			JOptionPane.showMessageDialog(null, "Goodbye!");
			System.exit(0);
		}

		// Else, return the count of players
		int playerCount = dialogResult + 2; //first button of the JOptionPane returns 0, second 1, third 2;
		return playerCount;
	    	
    }

    public static MainFrame startNewGame(){
    	// ------------------------- //
    	// Get the number of Players //
    	// ------------------------- //
    	int numPlayers = getNumPlayersFromUser();

    	// Validate that we got a proper response
    	if(numPlayers == -1){
    		System.err.println("An unexpected error occurred; exiting program.");
    		System.exit(1);
    	}
    	else if(numPlayers < MIN_PLAYERS){
    		System.err.println("You cannot play 'World of Sweets' with less than "+MIN_PLAYERS+" players; exiting program.");
    		System.exit(1);
    	}


	    System.out.println("Starting a new game of 'World of Sweets' with " + numPlayers + " players...");


		// --------------------------------------------------- //
		// Create the "World of Sweets" GUI and start the game //
		// --------------------------------------------------- //
		MainFrame newGameFrame = new MainFrame(numPlayers);

		return newGameFrame;
    }

    public static void main(String[] args) {
    	MainFrame gameFrame;

    	// Start a new game
    	gameFrame = startNewGame();
    }
}
