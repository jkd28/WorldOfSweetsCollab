import java.awt.*;
import java.io.*;
import java.math.*;
import java.security.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class WorldOfSweets {

	// Basic game information
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;
    public static final String SAVE_FILE_EXTENSION = "ser";
    public static final FileNameExtensionFilter SAVE_FILE_FILTER = new FileNameExtensionFilter(String.format("WorldOfSweets Save Files (*.%s)", SAVE_FILE_EXTENSION), SAVE_FILE_EXTENSION);
    public static final String CHECKSUM_ALGORITHM = "MD5";
    public static final String CHECKSUM_FILE_EXTENSION = "chksum";

    private static MainFrame mainGameFrame;

    /**
     * @param ser The Serializable to be saved to saveFile, along with a checksum.
     * @param saveFile The File to save ser to, along with a chechsum.
     * @return Boolean value on whether the save was successful or not.
     */
    private static boolean saveSerializable(Serializable ser, File saveFile){
        if(ser == null || saveFile == null){
            return false;
        }

        // Print the Serializable - with its checksum - out to file
        boolean isValidFile = saveFile.isFile() || ( !saveFile.exists() && !saveFile.isDirectory() );
        if(isValidFile){
            try{
                // Serialize the Serializable
                FileOutputStream fileStream = new FileOutputStream(saveFile);
                ObjectOutputStream oos = new ObjectOutputStream(fileStream);
                oos.writeObject(ser);
                oos.flush();
                oos.reset();
                fileStream.flush();

                oos.close();
                fileStream.close();


                // Calculate the checksum for this Object
                BigInteger checksum = WorldOfSweets.calculateChecksum(ser);

                // Get the appropriate checksum file
                String checksumFileName = saveFile.getName() + "." + WorldOfSweets.CHECKSUM_FILE_EXTENSION;
                File checksumFile = new File(checksumFileName);

                // Write the checksum out to the appropriate checksum file
                FileWriter checksumFileWriter = new FileWriter(checksumFile);
                checksumFileWriter.write(checksum.toString());
                checksumFileWriter.write(" ");
                checksumFileWriter.write(saveFile.getName());
                checksumFileWriter.close();
                
                // Return
                return true;
            }
            catch(Exception e){
                e.printStackTrace();
                System.exit(1);
                return false;
            }
        }

        return false;
    }

    /**
     * @param loadFile The File containing a serialized Serializable, along with its checksum.
     * @return De-serialized Serializable.
     * @throws ChecksumValueException
     */
    private static Serializable loadSerializable(File loadFile){
        if(loadFile == null){
            return null;
        }

        // Pull the checksum and the Serializable out of the loadFile
        boolean isValidFile = loadFile.isFile();
        if(isValidFile){
            try{
                // ----------------------------------- //
                // Read-in the Serializable and the Checksum //
                // ----------------------------------- //
                // Read-in the Serializable itself
                FileInputStream fileStream = new FileInputStream(loadFile);
                ObjectInputStream oos = new ObjectInputStream(fileStream);
                
                Serializable resultObj = (Serializable) oos.readObject();

                oos.close();
                fileStream.close();

                // Read-in the checksum
                String checksumFileName = loadFile.getName() + "." + WorldOfSweets.CHECKSUM_FILE_EXTENSION;
                File checksumFile = new File(checksumFileName);
                Scanner scanner = new Scanner(checksumFile);

                String checksumString = scanner.next();
                String checksumIntendedFileName = scanner.nextLine().trim();
                scanner.close();

                if(!loadFile.getName().equals(checksumIntendedFileName)){
                    // TODO: Exception needs to be thrown here!
                    System.err.println(loadFile.getName() + " vs " + checksumIntendedFileName);
                    return null;
                }

                BigInteger checksumFromFile = new BigInteger(checksumString, 10);

                // --------------------- //
                // Validate the checksum //
                // --------------------- //
                // Calculate the checksum of the Serializable that was just read-in
                BigInteger calculatedChecksum = WorldOfSweets.calculateChecksum(resultObj);

                // Compare the calculated checksum to the read-in checksum
                if(calculatedChecksum.compareTo(checksumFromFile) != 0){
                    /////String message = String.format("The checksum contained in '%s' does not match the checksum calculated using that file's contents; "
                    /////   + "this save file is CORRUPTED or has been TAMPERED WITH.", loadFile.getName());
                    String message = "Unequal checksums; CORRUPTED SAVE FILE.";
                    System.err.println(message);
                    System.exit(1);
                }
                else{
                    return resultObj;
                }
            }
            catch(IOException e){
                e.printStackTrace();
                System.exit(1);
                return null;
            }
            catch(ClassNotFoundException e){
                e.printStackTrace();
                System.exit(1);
                return null;
            }
        }

        return null;
    }

    /**
     * @see Serializable
     * @param obj The Object that will have a checksum generated for it. This object MUST implement Serializable.
     * @return A BigInteger representing the checksum for the given Serializable object.
     */
    public static BigInteger calculateChecksum(Object obj) {
        if (obj == null) {
          return null;   
        }

        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.close();
            bos.close(); // This doesn't do anything, but it's good habit to explicitly close every Stream you use

            
            MessageDigest m = MessageDigest.getInstance(WorldOfSweets.CHECKSUM_ALGORITHM);
            m.update(bos.toByteArray());

            BigInteger result = new BigInteger(1, m.digest());

            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public static MainFrame getMainGameFrame(){
        return mainGameFrame;
    }

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

    private static File getFileToLoadFromUser(){
        // ---------------------------------- //
        // Ask the user to select a save file //
        // ---------------------------------- //
        // Create the FileChooser
        File workingDirectory = new File(System.getProperty("user.dir"));
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileFilter(SAVE_FILE_FILTER);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);

        // Disable the "New Folder" ability in the JFileChooser
        // Action folder = fileChooser.getActionMap().get("New Folder");
        // folder.setEnabled(false);

        // Get a result from the FileChooser
        File saveFile = null;
        while(true){
            int fileChooserResult = fileChooser.showOpenDialog(null);

            // Check the result from the FileChooser
            if(fileChooserResult == JFileChooser.CANCEL_OPTION){
                // JOptionPane.showMessageDialog(null, "Goodbye!", "Goodbye!", JOptionPane.INFORMATION_MESSAGE);
                // System.exit(0);
                return null;
            }
            else if(fileChooserResult == JFileChooser.APPROVE_OPTION){
                saveFile = fileChooser.getSelectedFile();

                if(WorldOfSweets.isValidSaveFile(saveFile)){
                    return saveFile;
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
    }

    private static File getFileToSaveFromUser(){
        File workingDirectory = new File(System.getProperty("user.dir"));
        File defaultFile = new File("WorldOfSweets Saved Game" + "." + WorldOfSweets.SAVE_FILE_EXTENSION);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setFileFilter(SAVE_FILE_FILTER);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);

        // Get a result from the FileChooser
        File selectedFile = new File("");
        boolean writeToFile = true;
        while(true){
            fileChooser.setSelectedFile(defaultFile);

            int fileChooserResult = fileChooser.showSaveDialog(null);

            // Check the result from the FileChooser
            if(fileChooserResult == JFileChooser.CANCEL_OPTION){
                return null;
            }
            else if(fileChooserResult == JFileChooser.APPROVE_OPTION){
                selectedFile = fileChooser.getSelectedFile();

                // If the user selected a valid WorldOfSweets save file:
                if(WorldOfSweets.isValidSaveFileName(selectedFile.getName())){
                    boolean fileAlreadyContainsData = selectedFile.length() > 0;

                    // If this file already exists with data in it, we should confirm that they want to overwrite it
                    if(fileAlreadyContainsData){
                        String message = String.format("Are you sure you want to overwrite any existing data in '%s'?", selectedFile.getName());
                        String title = "Overwrite File?";
                        int response = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);

                        // If the user responded with ANYTHING other than "Yes", we DO NOT overwrite the selected file.
                        if(response != JOptionPane.YES_OPTION){
                            writeToFile = false;
                        }
                    }

                    break;
                }

                // Else we should tell the user to try again
                else{
                    JOptionPane.showMessageDialog(null, String.format("I'm sorry, but '%s' is not a valid name for a save file, please try again.", selectedFile.getName()));
                    continue;
                }
            }

            // Else we assume that an error occurred
            else{
                JOptionPane.showMessageDialog(
                    null, 
                    "An unexpected error occurred while trying to save state of current game; assume that the game's state was not saved.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                return null;
            }
        }

        if(WorldOfSweets.isValidSaveFileName(selectedFile.getName()) && writeToFile){
            return selectedFile;
        }
        else{
            return null;
        }
    }

    private static MainFrame loadPreviousGameFromSaveFile(File saveFile){
        if(saveFile == null){
            return null;
        }

        if(WorldOfSweets.isValidSaveFile(saveFile)){
            MainFrame gameFrame = (MainFrame) loadSerializable(saveFile);
            gameFrame.refreshPanels();
            return gameFrame;
        }
        else{
            return null;
        }
    }

    public static boolean isValidSaveFile(File saveFile){
        if(saveFile == null){
            return false;
        }

        boolean isFile = saveFile.isFile();
        boolean isValidSaveFileName = WorldOfSweets.isValidSaveFileName(saveFile.getName());

        return isFile && isValidSaveFileName;
    }

    public static boolean isValidSaveFileName(String saveFileName){
        if(saveFileName == null){
            return false;
        }

        if(saveFileName.equals("")){
            return false;
        }

        boolean hasValidFileExtension = saveFileName.endsWith("."+SAVE_FILE_EXTENSION);
        
        return hasValidFileExtension;
    }

    /*
    * @param gameFrame The MainFrame whose state we want saved to a file.
    * @return boolean Returns whether or not a save-file was actually created or overwritten.
    */
    public static boolean saveCurrentGameToFile(MainFrame gameFrame){
        // Validate "gameFrame"
        if(gameFrame == null){
            return false;
        }

        // Get a valid saveFile from the user
        File saveFile = getFileToSaveFromUser();
        if(saveFile == null){
            return false;
        }

        // If the file from the user is valid, save it
        if(WorldOfSweets.isValidSaveFileName(saveFile.getName())){
            WorldOfSweets.saveSerializable(gameFrame, saveFile);

            return true;
        }
        else{
            return false;
        }
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





    public static void main(String[] args) {
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
            return;
        }


        // ============================================================================================= //
        // Else if the user wants to load a game, have them choose a save-file to load, and then load it //
        // ============================================================================================= //
        else if(loadPreviousGameResult == JOptionPane.YES_OPTION){
            File saveFile = getFileToLoadFromUser();

            if(WorldOfSweets.isValidSaveFile(saveFile)){
                mainGameFrame = loadPreviousGameFromSaveFile(saveFile);

                if(mainGameFrame == null){
                    JOptionPane.showMessageDialog(null, 
                        "Something went wrong while trying to load the game saved in \""+saveFile.getName()+"\"."
                        + "\n\nStarting a new game instead!"
                    );
                    mainGameFrame = startNewGame();
                }
                else{
                    mainGameFrame.getDeckPanel().enableDrawButton();

                    mainGameFrame.resetTimerPanel();
                    // TimerPanel timerPanel = mainGameFrame.getTimerPanel();
                    // timerPanel.gameStarted = true;
                    // timerPanel.startTimer(timerPanel.timer.getRealTime());
                }
            }
            else{
                JOptionPane.showMessageDialog(null, 
                    "I'm sorry, but the file you selected was not a valid WorldOfSweets save file name."
                    + "\n\nStarting a new game instead!"
                );
                mainGameFrame = startNewGame();
            }
        }


        // ====================== //
    	// Else, Start a new game //
        // ====================== //
    	else{
            mainGameFrame = startNewGame();
        }

        mainGameFrame.getFrame().setVisible(true);
        mainGameFrame.initializeBackgroundAudio();
    }
}
