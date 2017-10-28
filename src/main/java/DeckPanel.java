/*
 * @author Brian Knotten (Github: "BK874"). Primary author of this file, used originally in the "BitsPlease" repository.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DeckPanel extends JPanel{
    
    Deck drawDeck = new Deck();
    CardPanel cPanel = new CardPanel();
    JPanel drawPanel = new JPanel(new FlowLayout());
    JButton drawButton = new JButton("Draw");
    
    public DeckPanel(){
	// The two subpanels will be next to each other
	setLayout(new GridLayout(1, 2));

	// Add the draw button to the Draw Panel
	drawButton.setFont(new Font("Courier", Font.PLAIN, 48));
	drawButton.addActionListener((ActionListener) new DrawListener(cPanel));
	drawPanel.add(drawButton);

	//Add both panels to the Frame
	add(drawPanel);
	add(cPanel);
    }

    // Class for the panel displaying the most recently drawn card.
    class CardPanel extends JPanel{
	CardLayout cardLayout = new CardLayout();

	public CardPanel(){
	    setLayout(cardLayout);

	    // Create and add each of the color panels
	    // representing the single color cards.
	    JPanel redPanel = createColorPanel(Color.red);
	    JPanel yellowPanel = createColorPanel(Color.yellow);
	    JPanel bluePanel = createColorPanel(Color.blue);
	    JPanel greenPanel = createColorPanel(Color.green);
	    JPanel orangePanel = createColorPanel(Color.orange);
	    JPanel whitePanel = createColorPanel(Color.white);
	    
      	    add(whitePanel, "WHITE");
	    add(redPanel, "RED");
	    add(yellowPanel, "YELLOW");
	    add(bluePanel, "BLUE");
	    add(greenPanel, "GREEN");
	    add(orangePanel, "ORANGE");
	}
    }

    // Helper method for creating the single color panels
    private JPanel createColorPanel(Color color){
	JPanel tempPanel = new JPanel();
	tempPanel.setBackground(color);
	return tempPanel;
    }

    
    private class DrawListener implements ActionListener{
	public DrawListener (CardPanel cPanel){
	}
	
	// Every time we click the button, it will display the
	// color of the next card in the deck
	public void actionPerformed(ActionEvent e){
	    cPanel.cardLayout.show(cPanel, drawDeck.draw().getColor());
	}
    }
}

