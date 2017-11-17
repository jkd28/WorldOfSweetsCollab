import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.Serializable;

public class DeckPanel extends JPanel implements Serializable {
    public static final Color DEFAULT_COLOR = Color.WHITE;

    private Deck drawDeck;
    private CardPanel cardPanel;
    private JPanel drawPanel;
    private JButton drawButton;
    private Color currentColor;
    private Card currentCard;

    public DeckPanel(){
		// The two subpanels will be next to each other
		setLayout(new GridLayout(1, 2));

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
		add(drawPanel);
		add(cardPanel);
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
		tempLabel.setForeground(Color.WHITE);
		tempPanel.setBackground(Color.BLACK);
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
    class CardPanel extends JPanel{
		CardLayout cardLayout = new CardLayout();
		JPanel panel;

		public CardPanel(){
		    setLayout(cardLayout);

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


    private class DrawListener implements ActionListener{
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
		    	case Card.GO_TO_MIDDLE: newPanel = createSpecialPanel("<html>Go to<br>Middle!</html>"); break;
		    }
		    cardPanel.setPanel(newPanel);
		    currentColor = cardColor;


		    // ============================================= //
		    // Update the current Player with the drawn card //
		    // ============================================= //
		    // Get the "parent" GUI window that is holding this DeckPanel
		    Window parent = SwingUtilities.getWindowAncestor(deckPanel);

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

				    // --------------------------------------- //
				    // Move to Player to their next BoardSpace //
				    // --------------------------------------- //
				    gameFrame.updatePlayerPosition(currentPlayer, currentCard);

				    // -------------------------------------------- //
			    	// Check if the current Player has won the game //
			    	// -------------------------------------------- //
			    	if(gameFrame.playerHasWon(currentPlayer)){
			    		// Disable the "draw" button //
			    		deckPanel.getDrawButton().setEnabled(false);

			    		// Diable the game timer //
			    		

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
