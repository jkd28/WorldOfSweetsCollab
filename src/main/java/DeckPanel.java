import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.Serializable;

public class DeckPanel implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Font DRAW_BUTTON_FONT = new Font("Calibri", Font.PLAIN, 24);
	private static final String DRAW_BUTTON_TEXT = "<html>World of Sweets!<br /> Click to Draw!</html>";
	public static final Color DEFAULT_COLOR = Color.WHITE;
	private boolean boomerangError = false;

	private Deck drawDeck;
	private CardPanel cardPanel;
	private Color currentColor;
	private Card currentCard;
	private ActionListener drawListener;

	private transient JPanel mainPanel;
	private transient JPanel drawPanel;
	private transient JButton drawButton;
	private transient JButton boomerangButton;

	private boolean isStrategicMode;

	public DeckPanel(boolean isStrategicMode){
		drawDeck = new Deck();
		cardPanel = new CardPanel();
		currentColor = DEFAULT_COLOR;
		currentCard = null;
		drawListener = (ActionListener) new DrawListener(this);

		this.isStrategicMode = isStrategicMode;

		initializeMainPanel();
	}
	public DeckPanel(){
		this(false);
	}

	private void initializeDrawButton(){
		drawButton = new JButton(DRAW_BUTTON_TEXT);
		drawButton.setFont(DRAW_BUTTON_FONT);
		drawButton.addActionListener(drawListener);
		drawButton.setEnabled(true);
		boomerangButton = new JButton("Use Boomerang");
		boomerangButton.setFont(DRAW_BUTTON_FONT);
		boomerangButton.addActionListener(drawListener);
		boomerangButton.setEnabled(isStrategicMode);
	}

	private void initializeDrawPanel(){
		if(drawButton == null){
			initializeDrawButton();
		}

		drawPanel = new JPanel(new BorderLayout());
		drawPanel.add(drawButton, BorderLayout.CENTER);
		if(isStrategicMode){
			drawPanel.add(boomerangButton, BorderLayout.EAST);
		}
	}

	private void initializeMainPanel(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));

		if(drawButton == null){
			initializeDrawButton();
		}

		if(drawPanel == null){
			initializeDrawPanel();
		}

		refreshPanels();
	}

	public void refreshPanels(){
		if(drawButton == null){
			initializeDrawButton();
		}

		if(drawPanel == null){
			initializeDrawPanel();
		}

		if(mainPanel == null){
			initializeMainPanel();
		}

		mainPanel.removeAll();
		mainPanel.add(drawPanel);
		mainPanel.add(cardPanel.getPanel());
	}

	public void refreshCardPanelBackground(){
		if(currentCard == null){
			cardPanel.setPanel(createSingleColorPanel(currentColor));
		}
		else{
			switch(currentCard.getValue()){
				case Card.SINGLE: 				cardPanel.setPanel(createSingleColorPanel(currentCard.getColor())); break;
				case Card.DOUBLE: 				cardPanel.setPanel(createDoubleColorPanel(currentCard.getColor())); break;
				case Card.SKIP: 				cardPanel.setPanel(createSpecialPanel(Card.SKIP_TEXT)); break;
				case Card.GO_TO_FIRST_SPECIAL: 	cardPanel.setPanel(createSpecialPanel(Card.GO_TO_FIRST_SPECIAL_TEXT)); break;
				case Card.GO_TO_SECOND_SPECIAL: cardPanel.setPanel(createSpecialPanel(Card.GO_TO_SECOND_SPECIAL_TEXT)); break;
				case Card.GO_TO_THIRD_SPECIAL: 	cardPanel.setPanel(createSpecialPanel(Card.GO_TO_THIRD_SPECIAL_TEXT)); break;
				case Card.GO_TO_FOURTH_SPECIAL: cardPanel.setPanel(createSpecialPanel(Card.GO_TO_FOURTH_SPECIAL_TEXT)); break;
				case Card.GO_TO_FIFTH_SPECIAL: 	cardPanel.setPanel(createSpecialPanel(Card.GO_TO_FIFTH_SPECIAL_TEXT)); break;

			}
		}
	}

	public JPanel getPanel(){
		if(mainPanel == null){
			initializeMainPanel();
		}

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

		if(text.equals(Card.GO_TO_FIRST_SPECIAL_TEXT)){
			tempLabel.setForeground(Color.BLACK);
			tempPanel.setBackground(Color.MAGENTA);
		} else if(text.equals(Card.GO_TO_SECOND_SPECIAL_TEXT)){
			tempLabel.setForeground(Color.BLACK);
			tempPanel.setBackground(Color.CYAN);
		} else if(text.equals(Card.GO_TO_THIRD_SPECIAL_TEXT)){
			tempLabel.setForeground(Color.BLACK);
			tempPanel.setBackground(Color.PINK);
		} else if (text.equals(Card.GO_TO_FOURTH_SPECIAL_TEXT)){
			tempLabel.setForeground(Color.WHITE);
			tempPanel.setBackground(Color.GRAY);
		} else if (text.equals(Card.GO_TO_FIFTH_SPECIAL_TEXT)){
			tempLabel.setForeground(Color.WHITE);
			tempPanel.setBackground(Color.BLACK);
		} else if (text.equals(Card.SKIP_TEXT)){
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
		if(mainPanel == null){
			initializeMainPanel();
		}

		return drawButton;
	}

	public Deck getDrawDeck(){
		return drawDeck;
	}

	public void enableDrawButton(){
		if(drawButton == null){
			initializeDrawButton();
		}

		if(drawPanel == null){
			initializeDrawPanel();
		}

		if(mainPanel == null){
			initializeMainPanel();
		}

		drawButton.setEnabled(true);
	}

	public void disableDrawButton(){
		if(mainPanel == null){
			initializeMainPanel();
		}

		drawButton.setEnabled(false);
	}


	// Class for the panel displaying the most recently drawn card.
	private class CardPanel implements Serializable{
		private static final long serialVersionUID = 1L;

		private transient JPanel panel;
		private transient JPanel wrapperPanel;

		public CardPanel(){
			// Set initial blank card
			currentColor = DEFAULT_COLOR;

			initializePanel();
			panel.setBackground(currentColor);

			initializeWrapperPanel();

			wrapperPanel.add(panel);
		}

		public void refreshBackgroundColor(){
			panel.setBackground(currentColor);
		}

		public JPanel getPanel(){
			if(panel == null){
				initializePanel();
			}

			if(wrapperPanel == null){
				initializeWrapperPanel();
			}

			setPanel(panel);

			return wrapperPanel;
		}

		public void setPanel(JPanel newPanel){
			if(wrapperPanel == null){
				initializeWrapperPanel();
			}

			if(wrapperPanel.isAncestorOf(panel)){
				wrapperPanel.remove(panel);
			}

			panel = newPanel;
			wrapperPanel.add(panel);
			wrapperPanel.validate();
			wrapperPanel.repaint();
		}

		private void initializeWrapperPanel(){
			wrapperPanel = new JPanel();
			wrapperPanel.setLayout(new CardLayout());
		}

		private void initializePanel(){
			panel = new JPanel();
		}
	}


	private class DrawListener implements ActionListener, Serializable{
		private static final long serialVersionUID = 1L;
		private DeckPanel deckPanel;
		private boolean pressedBoomerang = false;

		public DrawListener(DeckPanel deckPanel){
			this.deckPanel = deckPanel;
		}

		// Every time we click the button, it will display the
		// 	color of the next card in the deck
		public void actionPerformed(ActionEvent e){
			MainFrame gameFrame = WorldOfSweets.getMainGameFrame();
			// ============================= //
			// Draw a card and pull its data //
			// ============================= //
			if ((JButton)e.getSource() == boomerangButton){
				pressedBoomerang = true;
			}
			if ((JButton)e.getSource() != boomerangButton || (JButton)e.getSource() == boomerangButton && gameFrame.getCurrentPlayer().getNumBoomerangs() > 0){
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
				case Card.SKIP: 		newPanel = createSpecialPanel(Card.SKIP_TEXT); break;
				case Card.GO_TO_FIRST_SPECIAL: newPanel = createSpecialPanel(Card.GO_TO_FIRST_SPECIAL_TEXT); break;
			case Card.GO_TO_SECOND_SPECIAL: newPanel = createSpecialPanel(Card.GO_TO_SECOND_SPECIAL_TEXT); break;
				case Card.GO_TO_THIRD_SPECIAL: newPanel = createSpecialPanel(Card.GO_TO_THIRD_SPECIAL_TEXT); break;
				case Card.GO_TO_FOURTH_SPECIAL: newPanel = createSpecialPanel(Card.GO_TO_FOURTH_SPECIAL_TEXT); break;
				case Card.GO_TO_FIFTH_SPECIAL: newPanel = createSpecialPanel(Card.GO_TO_FIFTH_SPECIAL_TEXT); break;

			}
			cardPanel.setPanel(newPanel);
			deckPanel.refreshPanels();
			drawButton.requestFocus();
			currentColor = cardColor;
		}
			// ============================================= //
			// Update the current Player with the drawn card //
			// ============================================= //
			// Get the "parent" GUI window that is holding this DeckPanel
			// Window parent = SwingUtilities.getWindowAncestor(deckPanel.getPanel());

			// If this DeckPanel has a "parent", then we're playing a game of WorldOfSweets,
			//	so we need to update the Player that just "drew" a card,
			//	and then rotate to the next Player
			// Else, this DeckPanel doesn't have a "parent" because we're running a Unit Test,
			//	so we should not do ANYTHING more.

			if(gameFrame != null){ // When running the Unit Tests, the "parent" for a DeckPanel will be (NULL)
				if(gameFrame.getNumPlayers() > 0){
					// ----------------------------------- //
					// Get the Player who just drew a Card //
					// ----------------------------------- //
					Player currentPlayer = gameFrame.getCurrentPlayer();

					//get the timerPanel to check if game has started or ended
					TimerPanel timer = gameFrame.getTimerPanel();
                    if(!timer.timerIsRunning()){
                    	timer.startTimer();
                    }

					// Move to Player to their next BoardSpace
					if (pressedBoomerang && currentPlayer.getNumBoomerangs() > 0){
						Player boomerangedPlayer;
						currentPlayer.useBoomerang();
						Object[] options = new Object[gameFrame.getNumPlayers() - 1];
						Player[] otherPlayers = new Player[gameFrame.getNumPlayers() - 1];
						int optionsSize = 0;
						for (int i = 0; i < gameFrame.getNumPlayers(); i++){
							if (!gameFrame.getPlayer(i).getName().equals(currentPlayer.getName())){
								options[optionsSize] = gameFrame.getPlayer(i).getName();
								otherPlayers[optionsSize] = gameFrame.getPlayer(i);
								optionsSize++;
							}
						}
						int dialogResult = JOptionPane.showOptionDialog(null,
							"Choose a player to boomerang",
							"Choose a player to boomerang!",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[0]);
						boomerangedPlayer = otherPlayers[dialogResult];
						// System.out.println("boomeranged player is " + boomerangedPlayer.getName());

						gameFrame.updatePlayerPosition(boomerangedPlayer, currentCard, true);
						boomerangError = false;
						pressedBoomerang = false;
					}else if (pressedBoomerang){
							JOptionPane.showMessageDialog(null,
	    						"You don't have any boomerangs left!",
	    						"Error",
	    						JOptionPane.ERROR_MESSAGE);
							boomerangError = true;
							pressedBoomerang = false;
					}else{
						gameFrame.updatePlayerPosition(currentPlayer, currentCard, false);
						boomerangError = false;
						pressedBoomerang = false;
					}
					// -------------------------------------------- //
					// Check if the current Player has won the game //
					// -------------------------------------------- //
					if(gameFrame.playerHasWon(currentPlayer)){
						// Disable the "draw" button //
						deckPanel.disableDrawButton();

						// Disable the "Save Game" button //
						gameFrame.disableSaveButton();

						// Diable the game timer //
						timer.stopTimer();

						// Congratulate the winning player //
						JOptionPane.showMessageDialog(null, "Congratulations to " + currentPlayer.getName() + " for winning this game of 'WorldOfSweets'!");

						// End the game //
						System.exit(0);
					}
					if (!boomerangError){
						// Rotate to the next Player
						gameFrame.getNextPlayer();
					}
				}
			}
		}
	}
}
