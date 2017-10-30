import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;

public class WorldOfSweets {

	// Basic game information
	public static final int MIN_PLAYERS = 2; 	// This is "public" because our "MainFrame" class also uses this for input validation
												//		It should probably eventually get moved to whatever "GameplayDriver" sort of class
												//		that gets made.

  	private static int getNumPlayersFromUser(){
    	int numPlayers = -1;

    	// --------------------------------------- //
    	// Get the number of players from the user //
    	// --------------------------------------- //
    	while(true){
			// Ask the user for the number of players
			String result = JOptionPane.showInputDialog(null, 
				"Welcome to 'World of Sweets'!\nHow many players will be in this game? (minimum "+MIN_PLAYERS+" players)", 
				"Welcome!",
				JOptionPane.QUESTION_MESSAGE);
	    	
	    	// If the user hits "Cancel" or the Exit button, quit the program
	    	if(result == null){
	    		JOptionPane.showMessageDialog(null, "Goodbye!");
	    		System.exit(0);
	    	}

	    	// Try parsing the user input into a number, and make sure it's >= MIN_PLAYERS
	    	try{
	    		numPlayers = Integer.parseInt(result);
	    		if(numPlayers < MIN_PLAYERS){
	    			JOptionPane.showMessageDialog(null, 
	    				"You have to have at least " + MIN_PLAYERS + " players for 'World of Sweets'!",
	    				"Too Few Players",
	    				JOptionPane.ERROR_MESSAGE);
	    			continue;
	    		}
	    		break;
	    	}
	    	catch(NumberFormatException e){
	    		JOptionPane.showMessageDialog(null, 
	    			"I'm sorry, but \"" + result + "\" is not a valid number.", 
	    			"Invalid Input", 
	    			JOptionPane.ERROR_MESSAGE);
	    	}
	    	catch(Exception e){
	    		JOptionPane.showMessageDialog(null, 
	    			"An unknown error has occurred, please try again.", 
	    			"Unknown Error", 
	    			JOptionPane.ERROR_MESSAGE);
	    	}
    	}

	    return numPlayers;
    }
    
    public static void main(String[] args) {
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
		MainFrame mf = new MainFrame(numPlayers);
    }
}