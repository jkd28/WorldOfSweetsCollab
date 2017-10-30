public class WorldOfSweets {

	// Basic game information
	public static final int MIN_PLAYERS = 2; 	// This is "public" because our "MainFrame" class also uses this for input validation
												//		It should probably eventually get moved to whatever "GameplayDriver" sort of class
												//		that gets made.

    private static void showInvalidInputErrorMessage() {
		System.out.println("Usage: java WorldOfSweets <numPlayers>");
		System.out.println("'numPlayers' must be a positive integer >= " + MIN_PLAYERS);
		System.exit(1);
    }
    
    public static void main(String[] args) {
		// ------------------------ //
		// Validate input arguments //
		// ------------------------ //
		if (args.length != 1) {
		    showInvalidInputErrorMessage();
		}

		int numPlayers = -1;
		try {
		    numPlayers = Integer.parseInt(args[0]);
		    if(numPlayers < MIN_PLAYERS){
		    	showInvalidInputErrorMessage();
		    }
		} catch (Exception ex) {
		    showInvalidInputErrorMessage();
		}
		

		// --------------------------------------------------- //
		// Create the "World of Sweets" GUI and start the game //
		// --------------------------------------------------- //
		MainFrame mf = new MainFrame(numPlayers);
    }
}