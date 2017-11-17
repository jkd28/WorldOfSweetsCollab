import java.io.File;
import java.io.Serializable;
import javax.swing.Action;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicFileChooserUI;

public class WorldOfSweets implements Serializable {

	// Basic game information
	public static final int MIN_PLAYERS = 2;
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

    private static MainFrame startNewGame(){
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

    private static MainFrame loadPreviousGame(){
        // ---------------------------------- //
        // Ask the user to select a save file //
        // ---------------------------------- //
        // Create the FileChooser
        File workingDirectory = new File(System.getProperty("user.dir"));
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("WorldOfSweets Save Files (*.ser)", "ser"); // By convention, serialized objects in Java are stored in ".ser" files
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);

        // Disable the "New Folder" ability in the JFileChooser
        BasicFileChooserUI ui = (BasicFileChooserUI)fileChooser.getUI();
        Action folder = ui.getNewFolderAction();
        folder.setEnabled(false);

        // Get a result from the FileChooser
        File saveFile = null;
        while(true){
            int fileChooserResult = fileChooser.showOpenDialog(null);

            // Check the result from the FileChooser
            if(fileChooserResult == JFileChooser.CANCEL_OPTION){
                JOptionPane.showMessageDialog(null, "Goodbye!", "Goodbye!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            else if(fileChooserResult == JFileChooser.APPROVE_OPTION){
                saveFile = fileChooser.getSelectedFile();

                if(saveFile.isFile() && saveFile.getName().endsWith(".ser")){
                    break;
                }
                else{
                    JOptionPane.showMessageDialog(null, "I'm sorry, but \""+saveFile.getName()+"\" is not a valid WorldOfSweets save file name.");
                    continue;
                }
            }
            else{ //Else we assume that an error occurred
                JOptionPane.showMessageDialog(
                    null, 
                    "An unexpected error occurred while trying to open a save-file; exiting program.", 
                    "Exiting Program", 
                    JOptionPane.ERROR_MESSAGE
                );
                System.exit(1);
            }
        }

        // --------------------------------------------------------- //
        // Try to "Open" the selected save file and resume that game //
        // --------------------------------------------------------- //
        if(saveFile != null){
            System.exit(0);
            return null;
        }
        else{
            System.exit(1);
            return null;
        }
    }

    public static void main(String[] args) {
    	MainFrame gameFrame;

        // ===================================================== //
        // Ask if the user wants to load a previously saved game //
        // ===================================================== //
        int loadPreviousGameResult = JOptionPane.showConfirmDialog(
            null,
            "Would you like to load a previously saved game?",
            "Load a Saved Game?",
            JOptionPane.YES_NO_OPTION
        );


        // ============================================== //
        // If user CANCELLED the window, exit the program //
        // ============================================== //
        if(loadPreviousGameResult == JOptionPane.CLOSED_OPTION){
            JOptionPane.showMessageDialog(null, "Goodbye!", "Goodbye!", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }


        // ============================================================================================= //
        // Else if the user wants to load a game, have them choose a save-file to load, and then load it //
        // ============================================================================================= //
        else if(loadPreviousGameResult == JOptionPane.YES_OPTION){
            gameFrame = loadPreviousGame();
        }


        // ====================== //
    	// Else, Start a new game //
        // ====================== //
    	else{
            gameFrame = startNewGame();
        }
    }
}
