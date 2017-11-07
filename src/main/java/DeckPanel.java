/*
 * @author Brian Knotten (Github: "BK874"). Primary author of this file, used originally in the "BitsPlease" repository.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DeckPanel extends JPanel{

    private Deck drawDeck = new Deck();
    private CardPanel cPanel = new CardPanel();
    private JPanel drawPanel = new JPanel(new BorderLayout());
    private JButton drawButton = new JButton("<html>World of Sweets!<br /> Click to Draw!</html>");

    public static final Color DEFAULT_COLOR = Color.WHITE;
    private Color currentColor = DEFAULT_COLOR;
    private Card currentCard = null;

    public DeckPanel(){
		// The two subpanels will be next to each other
		setLayout(new GridLayout(1, 2));

		// Add the draw button to the Draw Panel
		drawButton.setFont(new Font("Calibri", Font.PLAIN, 24));
		drawButton.addActionListener((ActionListener) new DrawListener(cPanel));
		drawPanel.add(drawButton, BorderLayout.CENTER);

		//Add both panels to the Frame
		add(drawPanel);
		add(cPanel);
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
		public DrawListener (CardPanel cPanel){
		}

		// Every time we click the button, it will display the
		// color of the next card in the deck
		public void actionPerformed(ActionEvent e){
			// Draw a card and pull its data
			Card drawnCard = drawDeck.draw();
				currentCard = drawnCard;
		    int cardValue = drawnCard.getValue();
		    Color cardColor = drawnCard.getColor();

		    // Create and set the panel for this drawn card
		    JPanel newPanel = new JPanel();
		    switch(cardValue){
		    	case Card.SINGLE: 		newPanel = createSingleColorPanel(cardColor); break;
		    	case Card.DOUBLE: 		newPanel = createDoubleColorPanel(cardColor); break;
		    	case Card.SKIP: 		newPanel = createSpecialPanel("<html>Skip!</html>"); break;
		    	case Card.GO_TO_MIDDLE: newPanel = createSpecialPanel("<html>Go to<br>Middle!</html>"); break;
		    }
		    cPanel.setPanel(newPanel);
		    currentColor = cardColor;


		    // Update the current Player with the drawn card
		    Player currentPlayer = MainFrame.getCurrentPlayer();

		    // Check if the current Player has won the game
		    


		    // Rotate to the next Player
		    // This section is here as a quick "hack" because the Gradle tests do not instantiate any Players,
		    //		which means that those Gradle tests would otherwise throw an Exception here and 
		    //		crash the whole damn party.
		    //		It's not really an acceptable long-term solution, but we have other priorities.
		    //		(BenjaminMuscto)
		    try{
				MainFrame.getNextPlayer();
		    }catch (Exception a){
				//System.err.println("No players!");
				//System.exit(1);
		    }
		}
    }
}
