/*
 * @author Brian Knotten (Github: "BK874"). Primary author of this file, used originally in the "BitsPlease" repository.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DeckPanel extends JPanel{

    Deck drawDeck = new Deck();
    CardPanel cPanel = new CardPanel();
    JPanel drawPanel = new JPanel(new BorderLayout());
    JButton drawButton = new JButton("<html>World of Sweets!<br /> Click to Draw!</html>");

    String currentColor;

    public DeckPanel(){
	// The two subpanels will be next to each other
	setLayout(new GridLayout(1, 2));

	// Add the draw button to the Draw Panel
	drawButton.setFont(new Font("Courier", Font.PLAIN, 24));
	drawButton.addActionListener((ActionListener) new DrawListener(cPanel));
	drawPanel.add(drawButton, BorderLayout.CENTER);

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
	    JPanel redPanel2 =createDoubleColorPanel(Color.red);
	    JPanel yellowPanel = createColorPanel(Color.yellow);
	    JPanel yellowPanel2 = createDoubleColorPanel(Color.yellow);
	    JPanel bluePanel = createColorPanel(Color.blue);
	    JPanel bluePanel2 = createDoubleColorPanel(Color.blue);
	    JPanel greenPanel = createColorPanel(Color.green);
	    JPanel greenPanel2 = createDoubleColorPanel(Color.green);
	    JPanel orangePanel = createColorPanel(Color.orange);
	    JPanel orangePanel2 = createDoubleColorPanel(Color.orange);
	    JPanel whitePanel = createColorPanel(Color.white);

	    JPanel skipPanel = new JPanel(new GridBagLayout());
	    JLabel skipLabel = new JLabel("Skip!");
	    skipLabel.setFont(new Font("Courier", Font.PLAIN, 48));
	    skipLabel.setForeground(Color.WHITE);
	    skipPanel.setBackground(Color.BLACK);
	    skipPanel.add(skipLabel);

      	    add(whitePanel, "WHITE");
	    add(redPanel, "RED");
	    add(redPanel2, "RED2");
	    add(yellowPanel, "YELLOW");
	    add(yellowPanel2, "YELLOW2");
	    add(bluePanel, "BLUE");
	    add(bluePanel2, "BLUE2");
	    add(greenPanel, "GREEN");
	    add(greenPanel2, "GREEN2");
	    add(orangePanel, "ORANGE");
	    add(orangePanel2, "ORANGE2");
	    add(skipPanel, "SKIP");

	    currentColor = "WHITE";
	}
    }

    // Helper method for creating the single color panels
    private JPanel createColorPanel(Color color){
	JPanel tempPanel = new JPanel();
	tempPanel.setBackground(color);
	return tempPanel;
    }

    // Helper method for creating the double color panels
    private JPanel createDoubleColorPanel(Color color){
	JPanel tempPanel = new JPanel(new GridLayout(2, 1, 0, 15));
	JPanel tempPanel2 = createColorPanel(color);
	JPanel tempPanel3 = createColorPanel(color);
	tempPanel.add(tempPanel2);
	tempPanel.add(tempPanel3);

	return tempPanel;
    }

    // Returns the color of the current card
    public String getCurrentColor(){
	return currentColor;
    }


    private class DrawListener implements ActionListener{
	public DrawListener (CardPanel cPanel){
	}

	// Every time we click the button, it will display the
	// color of the next card in the deck
	public void actionPerformed(ActionEvent e){
	    Card drawnCard = drawDeck.draw();
	    if (drawnCard.getValue() == 1){
		cPanel.cardLayout.show(cPanel, drawnCard.getColor());
		currentColor = drawnCard.getColor();
	    } else if (drawnCard.getValue() == 2){
		cPanel.cardLayout.show(cPanel, drawnCard.getColor() + "2");
		currentColor = drawnCard.getColor() + "2";
	    } else {
		cPanel.cardLayout.show(cPanel, drawnCard.getColor());
		currentColor = drawnCard.getColor();
	    }

        MainFrame.getnextPlayer();
	}
    }
}
