import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.Serializable;

public class DeckPanel implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final Color DEFAULT_COLOR = Color.WHITE;

    private Deck drawDeck;
    private CardPanel cardPanel;
    private Color currentColor;
    private Card currentCard;

    private transient JPanel mainPanel;
    private transient JPanel drawPanel;
    private transient JButton drawButton;

    public DeckPanel(){
		// The two subpanels will be next to each other
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));

		// Initialize misc variables
		drawDeck = new Deck();
		cardPanel = new CardPanel();
		drawPanel = new JPanel(new BorderLayout());
		currentColor = DEFAULT_COLOR;
		currentCard = null;

		// Add the draw button to the Draw Panel
		drawButton = new JButton("<html>World of Sweets!<br /> Click to Draw!</html>");
		drawButton.setFont(new Font("Calibri", Font.PLAIN, 24));
		drawButton.addActionListener((ActionListener) new DrawListener(this));
		drawPanel.add(drawButton, BorderLayout.CENTER);

		//Add both panels to the Frame
		mainPanel.add(drawPanel);
		mainPanel.add(cardPanel);
    }

    public JPanel getPanel(){
    	return mainPanel;
    }

    // Helper method for creating the single color panels
    private JPanel createSingleColorPanel(Color color){
		JPanel tempPanel = new JPanel();
		tempPanel.setBackground(color);
		return tempPanel;
    }

    // Helper method for creating the double color panels
    private JPanel createDoubleColorPanel(Color color){
		JPanel tempPanel = new JPanel(new GridLayout(2, 1, 0, 15));
		JPanel tempPanel2 = createSingleColorPanel(color);
		JPanel tempPanel3 = createSingleColorPanel(color);
		tempPanel.add(tempPanel2);
		tempPanel.add(tempPanel3);

		return tempPanel;
    }

    // Helper method for creating the special card panels
    private JPanel createSpecialPanel(String text){
		JPanel tempPanel = new JPanel(new GridBagLayout());
		JLabel tempLabel = new JLabel(text);
		tempLabel.setFont(new Font("Calibri", Font.PLAIN, 48));

		if(text.equals("<html>Go to<br>Candy Cane Forest!</html>")){
		    tempLabel.setForeground(Color.BLACK);
		    tempPanel.setBackground(Color.MAGENTA);
		} else if(text.equals("<html>Go to<br>Minty Mountains!</html>")){
		    tempLabel.setForeground(Color.BLACK);
		    tempPanel.setBackground(Color.CYAN);
		} else if(text.equals("<html>Go to<br>Bubble Gum Trapeze!</html>")){
		    tempLabel.setForeground(Color.BLACK);
		    tempPanel.setBackground(Color.PINK);
		} else if (text.equals("<html>Go to<br>Marshmallow Marsh!</html>")){
		    tempLabel.setForeground(Color.WHITE);
		    tempPanel.setBackground(Color.GRAY);
		} else if (text.equals("<html>Go to<br>Licorice Lagoon!</html>")){
		    tempLabel.setForeground(Color.WHITE);
		    tempPanel.setBackground(Color.BLACK);
		} else if (text.equals("<html>Skip!</html>")){
		    tempLabel.setForeground(Color.WHITE);
		    tempPanel.setBackground(Color.DARK_GRAY);
		}
		tempPanel.add(tempLabel);

		return tempPanel;
    }

    // Returns the color of the current card
    public Color getCurrentColor(){
	return currentColor;
    }

    public Card getCurrentCard(){
    	return currentCard;
    }

    public JButton getDrawButton(){
    	return drawButton;
    }

    public Deck getDrawDeck(){
    	return drawDeck;
    }

    public void enableDrawButton(){
    	drawButton.setEnabled(true);
    }

    public void disableDrawButton(){
    	drawButton.setEnabled(false);
    }


    // Class for the panel displaying the most recently drawn card.
    private class CardPanel extends JPanel implements Serializable{
		private static final long serialVersionUID = 1L;
		private JPanel panel;

		public CardPanel(){
		    setLayout(new CardLayout());

		    // Set initial blank card
		    currentColor = DEFAULT_COLOR;
		    panel = new JPanel();
		    panel.setBackground(currentColor);
		    this.add(panel);
		}

		public JPanel getPanel(){
			return panel;
		}

		public void setPanel(JPanel newPanel){
			this.remove(panel);
			panel = newPanel;
			this.add(panel);
			this.validate();
			this.repaint();
		}
    }


    private class DrawListener implements ActionListener, Serializable{
		private static final long serialVersionUID = 1L;
    	private DeckPanel deckPanel;

		public DrawListener(DeckPanel deckPanel){
			this.deckPanel = deckPanel;
		}

		// Every time we click the button, it will display the
		// 	color of the next card in the deck
		public void actionPerformed(ActionEvent e){
			// ============================= //
			// Draw a card and pull its data //
			// ============================= //
			Card drawnCard = drawDeck.draw();
				currentCard = drawnCard;
		    int cardValue = drawnCard.getValue();
		    Color cardColor = drawnCard.getColor();

		    // ============================================ //
		    // Create and set the panel for this drawn card
		    // ============================================ //
		    JPanel newPanel = new JPanel();
		    switch(cardValue){
		    	case Card.SINGLE: 		newPanel = createSingleColorPanel(cardColor); break;
		    	case Card.DOUBLE: 		newPanel = createDoubleColorPanel(cardColor); break;
		    	case Card.SKIP: 		newPanel = createSpecialPanel("<html>Skip!</html>"); break;
		    	case Card.GO_TO_FIRST_SPECIAL: newPanel = createSpecialPanel("<html>Go to<br>Candy Cane Forest!</html>"); break;
		    case Card.GO_TO_SECOND_SPECIAL: newPanel = createSpecialPanel("<html>Go to<br>Minty Mountains!</html>"); break;
		    	case Card.GO_TO_THIRD_SPECIAL: newPanel = createSpecialPanel("<html>Go to<br>Bubble Gum Trapeze!</html>"); break;
		    	case Card.GO_TO_FOURTH_SPECIAL: newPanel = createSpecialPanel("<html>Go to<br>Marshmallow Marsh!</html>"); break;
		    	case Card.GO_TO_FIFTH_SPECIAL: newPanel = createSpecialPanel("<html>Go to<br>Licorice Lagoon!</html>"); break;

		    }
		    cardPanel.setPanel(newPanel);
		    currentColor = cardColor;


		    // ============================================= //
		    // Update the current Player with the drawn card //
		    // ============================================= //
		    // Get the "parent" GUI window that is holding this DeckPanel
		    Window parent = SwingUtilities.getWindowAncestor(deckPanel.getPanel());

		    // If this DeckPanel has a "parent", then we're playing a game of WorldOfSweets,
		    //	so we need to update the Player that just "drew" a card,
		    //	and then rotate to the next Player
		    // Else, this DeckPanel doesn't have a "parent" because we're running a Unit Test,
		    //	so we should not do ANYTHING more.
		    if(parent != null){ // When running the Unit Tests, the "parent" for a DeckPanel will be (NULL)
		    	MainFrame gameFrame = (MainFrame) ((JFrame) parent);
			    if(gameFrame.getNumPlayers() > 0){
			    	// ----------------------------------- //
			    	// Get the Player who just drew a Card //
			    	// ----------------------------------- //
				    Player currentPlayer = gameFrame.getCurrentPlayer();
				    
                    //get the timerPanel to check if game has started or ended
                    TimerPanel timer = gameFrame.getTimerPanel();
                    timer.gameStarted = true;

				    // Move to Player to their next BoardSpace
				    gameFrame.updatePlayerPosition(currentPlayer, currentCard);

				    // -------------------------------------------- //
			    	// Check if the current Player has won the game //
			    	// -------------------------------------------- //
			    	if(gameFrame.playerHasWon(currentPlayer)){
			    		// Disable the "draw" button //
			    		deckPanel.disableDrawButton();

			    		// Disable the "Save Game" button //
			    		gameFrame.disableSaveButton();

			    		// Diable the game timer //
                        timer.gameFinished = true;

			    		// Congratulate the winning player //
			    		JOptionPane.showMessageDialog(null, "Congratulations to " + currentPlayer.getName() + " for winning this game of 'WorldOfSweets'!");
						
						// End the game //
						System.exit(0);
			    	}
			    }

			    // Rotate to the next Player
			    gameFrame.getNextPlayer();
		    }
		}
    }
}
