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
    private static final int FRAME_HEIGHT = 600;
    private static final int FRAME_WIDTH = 800;

    // Data for the Board Panel
    private BoardPanel boardPanel;

    // Data for the Deck Panel
    private DeckPanel deckPanel;

    // Data for the PlayerPanel
    private PlayerPanel playerPanel;

    // Data for tracking Players
    private Player[] players;
    private int numPlayers;

    // Data for currentPlayer
    public int currentPlayerIndex;

    // Data for music
    private Clip clip;
    private File file;
    
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

	    // If this card is a "Go to Middle" card, send the Player directly to the middle of the board
	    if(card.getValue() == Card.GO_TO_MIDDLE){
	    	boardPanel.sendPlayerToMiddleSpace(player);
	    }

	    // With a normal Single or Double colored card,
	    //	send the Player to their next spot.
	    boardPanel.sendPlayerToNextSpace(player, card);
    }

    public boolean playerHasWon(Player player){
    	BoardSpace currentPlayerSpace = player.getPosition();
    	return currentPlayerSpace.isGrandmasHouse();
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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit entire program when window is closed


		// ------------------ //
		// Create the Players //
		// ------------------ //
		players = new Player[numPlayers];

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
		this.add(boardPanel, BorderLayout.NORTH);

		//Set all players to starting boardspace (index 0)
		for(Player player : players){
			player.setPosition(boardPanel.getSpace(0));
		}


		// --------------------------------------------- //
		// Create the deck Panel and add it to the Frame //
		// --------------------------------------------- //
		deckPanel = new DeckPanel();
		this.add(deckPanel, BorderLayout.WEST);


        // ----------------------------------------------- //
		// Create the player Panel and add it to the Frame //
		// ----------------------------------------------- //
        playerPanel = new PlayerPanel(players);
        this.add(playerPanel, BorderLayout.CENTER);


	// Play the free Music by https://www.free-stock-music.com
	try{
	    file = new File("src/main/resources/lets-play-a-while.wav");
	    if (file.exists()){
		AudioInputStream music = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
		clip.open(music);
	    } else {
		throw new RuntimeException("Music: file not found.");
	    }
	} catch(MalformedURLException e){
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
		this.setVisible(true);
    }
 }
