import javax.swing.JOptionPane;

public class WorldOfSweets {

	// Basic game information
	public static final int MIN_PLAYERS = 2; 	// These are "public" because our "MainFrame" class also uses this for input validation
												//		It may eventually get moved to whatever "GameplayDriver" sort of class
												//		that gets made.
	public static final int MAX_PLAYERS = 4;

	public static int numPlayers = 0;
	public static int currentPlayer = 0;		// The player first to go will always be player 0, regardless of the number of players

  	private static int getNumPlayersFromUser(){
    	int numPlayers = -1;

    	// --------------------------------------- //
    	// Get the number of players from the user //
    	// --------------------------------------- //
			// Ask the user for the number of players
		Object[] options = {2,3,4};

		numPlayers = JOptionPane.showOptionDialog(null,
			"Welcome to 'World of Sweets'!\nHow many players will be in this game?",
			"Welcome!",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[0]);
		if (numPlayers != JOptionPane.CLOSED_OPTION){
			numPlayers = numPlayers + 2; //first button returns 0, second 1, third 2;
		}

	    	// If the user hits "Cancel" or the Exit button, quit the program
		System.out.println(numPlayers);
		if (numPlayers == JOptionPane.CLOSED_OPTION){
			JOptionPane.showMessageDialog(null, "Goodbye!");
			System.exit(0);
		}
	    return numPlayers;
    }

    public static void main(String[] args) {
    	// ------------------------- //
    	// Get the number of Players //
    	// ------------------------- //
    	numPlayers = getNumPlayersFromUser();

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
