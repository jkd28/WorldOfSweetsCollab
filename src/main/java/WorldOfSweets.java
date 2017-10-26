public class WorldOfSweets {

	public static final int MIN_PLAYERS = 2;

    private static void showErrorMessage() {
		System.out.println("Usage: java WorldOfSweets <numPlayers>");
		System.out.println("'numPlayers' must be a positive integer >= " + MIN_PLAYERS);
		System.exit(1);
    }
    
    public static void main(String[] args) {
		int numPlayers = -1;
		
		if (args.length != 1) {
		    showErrorMessage();
		}
		
		try {
		    numPlayers = Integer.parseInt(args[0]);
		    if(numPlayers < MIN_PLAYERS){
		    	showErrorMessage();
		    }
		} catch (Exception ex) {
		    showErrorMessage();
		}
		    
		MainFrame mf = new MainFrame(numPlayers);
    }
}
