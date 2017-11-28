import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.Serializable;
import javax.sound.sampled.*;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class MainFrame extends JFrame implements Serializable {
    // Data for the entire Frame, which will hold all of our Panels
    private static final int FRAME_HEIGHT = 800;
    private static final int FRAME_WIDTH = 800;

    // Data for the Board Panel
    private BoardPanel boardPanel;

    // Data for the Deck Panel
    private DeckPanel deckPanel;

    // Data for the PlayerPanel
    private PlayerPanel playerPanel;

    // Data for the "SaveButton" Panel
    private JPanel savePanel;
    private JButton saveButton;

    // Data for Timer
    private TimerPanel timerPanel;

    // Data for tracking Players
    private Player[] players;
    private int numPlayers;

    // Data for currentPlayer
    public int currentPlayerIndex;

  
    // Data for music
    private File file;
    private final String BACKGROUND_MUSIC_FILE_PATH = "src/main/resources/lets-play-a-while.wav";

    private JPanel southPanel;


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
  
    	southPanel.removeAll();
        southPanel.add(timerPanel.getLabel(), BorderLayout.WEST);
        southPanel.add(savePanel, BorderLayout.EAST);
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

			file = new File(audioFilePath);
			if (file.exists()){
				AudioInputStream music = AudioSystem.getAudioInputStream(file);
				AudioFormat format = music.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class, format);
				clip = (Clip)AudioSystem.getLine(info);
				clip.open(music);
			} 
			else {
				throw new RuntimeException("Music: file not found.");
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
      

        // -------------------- //
		// Make it all visible! //
		// -------------------- //
        this.pack();
		this.setVisible(true);
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

        this.refreshPanels();

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

    public void updatePlayerPosition(Player player, Card card){
    	// Get the BoardSpace that this Player currently inhabits
	    BoardSpace currentSpace = player.getPosition();

	    // If this spot is "Grandma's House", we do not move anywhere
	    if(currentSpace.isGrandmasHouse()){
	    	return;
	    }

	    // If this card is a "Skip" card, we do nothing
	    if(card.getValue() == Card.SKIP){
	    	return;
	    }

	    // Temporarily ignore the "Special" cards (currently valued > 2)
	    // if(card.getValue() > 2){
		// return;
	    // }

	    // If this card is a "Go to Middle" card, send the Player directly to the middle of the board
	    //	    if(card.getValue() == Card.GO_TO_MIDDLE){
	    //	    	boardPanel.sendPlayerToMiddleSpace(player);
	    //	    }

	    // With a normal Single or Double colored card,
	    //	send the Player to their next spot.
	    boardPanel.sendPlayerToNextSpace(player, card);
    }

    public boolean playerHasWon(Player player){
    	BoardSpace currentPlayerSpace = player.getPosition();
    	return currentPlayerSpace.isGrandmasHouse();
    }
    
    public void refreshPanels(){
        this.getContentPane().removeAll();
    	this.validate();
    	this.repaint();

    	this.add(boardPanel, BorderLayout.NORTH);
        this.add(playerPanel.getPanel(), BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
        this.add(deckPanel.getPanel(), BorderLayout.WEST);
        deckPanel.refreshCardPanelBackground();
    	this.validate();
    	this.repaint();
    }

    public MainFrame(int playerCount){
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


		// ---------------- //
    	// Create the Frame //
		// ---------------- //
		this.setTitle("World of Sweets");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener((WindowAdapter) new ExitGameListener(this));

		// ------------------ //
		// Create the Players //
		// ------------------ //
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


		// ----------------------------------------------- //
		// Create game-board Panel and add it to the Frame //
		// ----------------------------------------------- //
		boardPanel = new BoardPanel(players);
		this.add(boardPanel, BorderLayout.NORTH);

		//Set all players to starting boardspace (index 0)
		for(Player player : players){
			player.setPosition(boardPanel.getSpace(0));
		}


		// --------------------------------------------- //
		// Create the deck Panel and add it to the Frame //
		// --------------------------------------------- //
		deckPanel = new DeckPanel();
		this.add(deckPanel.getPanel(), BorderLayout.WEST);

      
        // ----------------------------------------------- //
		// Create the player Panel and add it to the Frame //
		// ----------------------------------------------- //
        playerPanel = new PlayerPanel(players);
        this.add(playerPanel.getPanel(), BorderLayout.CENTER);


        // ---------------------- //
        // Create the Timer panel //
        // ---------------------- //
        southPanel = new JPanel();
        timerPanel = new TimerPanel();
        southPanel.add(timerPanel.getLabel(), BorderLayout.WEST);


        // ----------------------------------------------- //
        // Create the "Save" panel and add it to the Frame //
        // ----------------------------------------------- //
        saveButton = new JButton("Save Game");
        saveButton.addActionListener((ActionListener) new SaveGameButtonListener(this, deckPanel));
        savePanel = new JPanel();
        savePanel.add(saveButton);
        southPanel.add(savePanel, BorderLayout.EAST);

        this.add(southPanel, BorderLayout.SOUTH);
    }

    private class SaveGameButtonListener implements ActionListener, Serializable{
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
    		String message = "Would you like to save your game before exiting?";
            String title = "Save Before Exit?";
            int response = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);

            if(response == JOptionPane.YES_OPTION){
            	WorldOfSweets.saveCurrentGameToFile(gameFrame);
            }

            JOptionPane.showMessageDialog(null, "Goodbye!");
            gameFrame.setVisible(false);
            System.exit(0);
    	}
    }
 }
