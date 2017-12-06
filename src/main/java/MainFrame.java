import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;
import java.net.URL;
import java.net.MalformedURLException;

public class MainFrame implements Externalizable {
    // Data for the entire Frame, which will hold all of our Panels
    public static final int FRAME_HEIGHT = 800;
    public static final int FRAME_WIDTH = 800;
    private static final String BACKGROUND_MUSIC_FILE_PATH = "src/main/resources/lets-play-a-while.wav";
    private static final String SAVE_GAME_BUTTON_TEXT = "Save Game";
    private static final String MAIN_FRAME_TITLE = "World of Sweets";


    private ActionListener saveGameButtonListener;
    private WindowAdapter exitGameListener;

    // Data for the Board Panel
    private BoardPanel boardPanel;

    // Data for the Deck Panel
    private DeckPanel deckPanel;

    // Data for the PlayerPanel
    private PlayerPanel playerPanel;

    // Data for Timer
    private TimerPanel timerPanel;

    // Data for tracking Players
    private Player[] players;
    private int numPlayers;

    // Data for currentPlayer
    public int currentPlayerIndex;

    protected boolean isStrategicMode;


    private transient JFrame mainFrame;
    private transient JPanel southPanel;    // Holds the "SaveButton" and "TimerPanel" pieces
    private transient JButton saveButton;   // Data for the "SaveButton" Panel
    private transient JPanel savePanel;     //

    

    public JFrame getFrame(){
        return mainFrame;
    }

    public void setVisible(boolean setVisible){
        mainFrame.setVisible(setVisible);
    }

    //get the TimerPanel to check if the game has won
    public TimerPanel getTimerPanel(){
        return timerPanel;
    }

    public void resetTimerPanel(){
    	timerPanel.updateLabel();
    	timerPanel.startTimer();
    	
    	if(!timerPanel.timerIsRunning()){
			timerPanel.startTimer();
    	}
    }
  
    public BoardPanel getBoardPanel(){
        return boardPanel;
    }



    public void initializeBackgroundAudio(){
    	initializeBackgroundAudio(BACKGROUND_MUSIC_FILE_PATH);
    }
    public void initializeBackgroundAudio(String audioFilePath){
    	// Play the free Music by https://www.free-stock-music.com
  		Clip clip;
		try{
			if(audioFilePath == null){
				audioFilePath = BACKGROUND_MUSIC_FILE_PATH;
			}
			else if(audioFilePath.equals("")){
				audioFilePath = BACKGROUND_MUSIC_FILE_PATH;
			}

			File musicFile = new File(audioFilePath);
			if (musicFile.exists()){
				AudioInputStream music = AudioSystem.getAudioInputStream(musicFile);
				AudioFormat format = music.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class, format);
				clip = (Clip)AudioSystem.getLine(info);
				clip.open(music);
			} 
			else {
				throw new RuntimeException(String.format("Music file '%s' not found.", musicFile.getName()));
			}
		} 
		catch(MalformedURLException e){
			e.printStackTrace();
			throw new RuntimeException("Malformed URL: " + e);
		}
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			throw new RuntimeException("Unsupported Audio File: " + e);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Input/Output Error: " + e);
		}
		catch (LineUnavailableException e) {
			e.printStackTrace();
			throw new RuntimeException("Line Unavailable Exception Error: " + e);
		}
		clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    
    // --------------------------------------- //
    // Calling this will return the player who //
    // is up next and advance currentPlayer    //
    // --------------------------------------- //
    public Player getNextPlayer(){
    	Player currentPlayer = getCurrentPlayer();

    	currentPlayerIndex = (currentPlayerIndex + 1) % getNumPlayers();
    	Player nextPlayer = getPlayer(currentPlayerIndex);
    	playerPanel.changePlayer(currentPlayer, nextPlayer);

    	return currentPlayer;
    }

	public Player getCurrentPlayer(){
    	return players[currentPlayerIndex];
    }

    public int getNumPlayers(){
    	return numPlayers;
    }

    public Player getPlayer(int playerIndex){
    	return players[playerIndex];
    }

    public DeckPanel getDeckPanel(){
    	return deckPanel;
    }

    public void disableSaveButton(){
    	saveButton.setEnabled(false);
    }

    public void enableSaveButton(){
    	saveButton.setEnabled(true);
    }

    public void updatePlayerPosition(Player player, Card card, Boolean reverse){

	    boardPanel.sendPlayerToNextSpace(player, card, reverse);
    }

    public boolean playerHasWon(Player player){
    	BoardSpace currentPlayerSpace = player.getPosition();
    	return currentPlayerSpace.isGrandmasHouse();
    }
    

    private void initializeSwingComponents(){
        saveButton = new JButton(MainFrame.SAVE_GAME_BUTTON_TEXT);
        saveButton.addActionListener(saveGameButtonListener);

        savePanel = new JPanel();
        savePanel.add(saveButton);

        southPanel = new JPanel();
        southPanel.add(timerPanel.getLabel(), BorderLayout.WEST);
        southPanel.add(savePanel, BorderLayout.EAST);

        mainFrame = new JFrame();
        mainFrame.setTitle(MainFrame.MAIN_FRAME_TITLE);
        mainFrame.setSize(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT);
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(exitGameListener);

        mainFrame.add(boardPanel.getPanel(), BorderLayout.NORTH);
        mainFrame.add(deckPanel.getPanel(), BorderLayout.WEST);
        deckPanel.refreshCardPanelBackground(); // If we don't call this, "deckPanel" will be blank, rather than displaying the last card chosen (if we're loading a game)

        mainFrame.add(playerPanel.getPanel(), BorderLayout.CENTER);
        mainFrame.add(southPanel, BorderLayout.SOUTH);

        mainFrame.validate();
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void createPlayers(){
        players = new Player[numPlayers];
        String[] usedTokens = new String[4];
        for(int i = 0; i < players.length; i++){
            String defaultPlayerName = "Player "+i;
            String playerName = defaultPlayerName;
            String token;
            while(true){
                playerName = JOptionPane.showInputDialog(null, "What is the name of player #"+i+"?", defaultPlayerName);
                TokenPanel tp = new TokenPanel(usedTokens);
                tp.setVisible(true);
                token = tp.getSelectedToken();
                usedTokens[i] = token;

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
            newPlayer.setToken(token);
            players[i] = newPlayer;
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException{
        out.writeObject(saveGameButtonListener);
        out.writeObject(exitGameListener);
        out.writeObject(boardPanel);
        out.writeObject(deckPanel);
        out.writeObject(playerPanel);
        out.writeObject(timerPanel);
        out.writeObject(players);
        out.writeInt(numPlayers);
        out.writeInt(currentPlayerIndex);
        out.writeBoolean(isStrategicMode);
        
        // All of the data needed to reconstruct the Swing objects
        //  is already saved, by virtue of the data already saved above.
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
                                                    ClassNotFoundException{
        saveGameButtonListener = (ActionListener) ((SaveGameButtonListener) in.readObject());
        exitGameListener = (WindowAdapter) ((ExitGameListener) in.readObject());
        boardPanel = (BoardPanel) in.readObject();
        boardPanel.refreshBoardPanel();
        deckPanel = (DeckPanel) in.readObject();
        playerPanel = (PlayerPanel) in.readObject();
        timerPanel = (TimerPanel) in.readObject();
        players = (Player[]) in.readObject();
        numPlayers = (int) in.readInt();
        currentPlayerIndex = (int) in.readInt();
        isStrategicMode = (boolean) in.readBoolean();

        // Regenerate the Swing objects
        initializeSwingComponents();
    }

    public MainFrame(){
        /*
        * FOR THE LOVE OF THE DIVINE TRINITY DO NOT DELETE ME; 
            I - the B L A N K C O N S T R U C T O R - am required by the Holy Ghost of Externalizable to be present
            (I'm pretty sure becasuse it uses me to instantiate any class variables, like all of my "static final" variables) 
        */
    }

    public MainFrame(int playerCount){
        this(playerCount, false);
    }

    public MainFrame(int playerCount, boolean isStrategicMode){
        this.numPlayers = playerCount;
        currentPlayerIndex = 0; // The first player to go will always be player 0, regardless of the number of players

        // ------------------------ //
        // Validate input arguments //
        // ------------------------ //
        if(numPlayers < WorldOfSweets.MIN_PLAYERS || numPlayers > WorldOfSweets.MAX_PLAYERS){
            String message = String.format("Number of players must be a positive integer between %d and %d!", WorldOfSweets.MIN_PLAYERS, WorldOfSweets.MAX_PLAYERS);
            JOptionPane.showMessageDialog(null,
                message,
                "Invalid Number of Players",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        this.isStrategicMode = isStrategicMode;

        createPlayers();

        boardPanel = new BoardPanel(players);
        for(Player player : players){                     //Set all players to starting boardspace (index 0)
            player.setPosition(boardPanel.getSpace(0));   //
        }                                                 //

        deckPanel = new DeckPanel(this.isStrategicMode);
        playerPanel = new PlayerPanel(players, this.isStrategicMode);
        timerPanel = new TimerPanel();

        saveGameButtonListener = (ActionListener) new SaveGameButtonListener(this, deckPanel);
        exitGameListener = (WindowAdapter) new ExitGameListener(this);

        initializeSwingComponents();
    }

    private class SaveGameButtonListener implements ActionListener, Serializable{
        private static final long serialVersionUID = 1L;
    	private MainFrame gameFrame;
    	private DeckPanel deckPanel;

		public SaveGameButtonListener(MainFrame gameFrame, DeckPanel deckPanel){
			this.gameFrame = gameFrame;
			this.deckPanel = deckPanel;
		}

		public void actionPerformed(ActionEvent e){
			// ========================= //
			// Disable the "draw" button //
			// ========================= //
			deckPanel.disableDrawButton();


			// ====================== //
			// Disable the game timer //
			// ====================== //
            gameFrame.getTimerPanel().stopTimer();


			// ============= //
			// Save the game //
			// ============= //
			boolean successfulSave = WorldOfSweets.saveCurrentGameToFile(gameFrame);

			// If the save was successful, let the user know
			if(successfulSave){
				JOptionPane.showMessageDialog(null, "The state of this game has been succesfully saved!");
			}

			// Else the save was not successful; let the user know
			else{
				JOptionPane.showMessageDialog(null, "We were unable to save the state of this game for some reason; please try again!");
			}

			// ======================== //
			// Re-enable the game timer //
			// ======================== //
            gameFrame.getTimerPanel().startTimer();


			// =========================== //
			// Re-enable the "draw" button //
			// =========================== //
			deckPanel.enableDrawButton();

			// =============== //
			// Resume the game //
			// =============== //
			return;
		}
    }

    private class ExitGameListener extends WindowAdapter implements Serializable{
        private static final long serialVersionUID = 1L;
        private MainFrame gameFrame;

    	public ExitGameListener(MainFrame gameFrame){
    		this.gameFrame = gameFrame;
    	}

    	@Override
    	public void windowClosing(WindowEvent e){
    		// ========================= //
			// Disable the "draw" button //
			// ========================= //
			deckPanel.disableDrawButton();


    		// ====================== //
			// Disable the game timer //
			// ====================== //
            gameFrame.getTimerPanel().stopTimer();


    		// ======================================================= //
    		// Ask if the user wants to save their game before exiting //
    		// ======================================================= //
            while(true){
        		String message = "Would you like to save your game before exiting?";
                String title = "Save Before Exit?";
                int response = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);

                if(response == JOptionPane.YES_OPTION){
                	boolean successfulSave = WorldOfSweets.saveCurrentGameToFile(gameFrame);
                    if(successfulSave){
                        JOptionPane.showMessageDialog(null, "The state of this game has been succesfully saved!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "We were unable to save the state of this game for some reason; please try again!");
                    }
                }

                JOptionPane.showMessageDialog(null, "Goodbye!");
                gameFrame.setVisible(false);
                System.exit(0);
            }
    	}
    }
 }
