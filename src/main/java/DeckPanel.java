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

	drawButton.setFont(new Font("Courier", Font.PLAIN, 48));
	drawButton.addActionListener((ActionListener) new DrawListener(cPanel));
	drawPanel.add(drawButton);

	//Add both panels to the Frame
	add(drawPanel);
	add(cPanel);
    }

    
    class CardPanel extends JPanel{
    CardLayout cardLayout = new CardLayout();

	public CardPanel(){
	    setLayout(cardLayout);
	    JPanel redPanel = createColorPanel(Color.red);
	    JPanel yellowPanel = createColorPanel(Color.yellow);
	    JPanel bluePanel = createColorPanel(Color.blue);
	    JPanel greenPanel = createColorPanel(Color.green);
	    JPanel orangePanel = createColorPanel(Color.orange);
	    JPanel whitePanel = createColorPanel(Color.white);
	    
	    //add("WHITE", whitePanel);
      	    add(whitePanel, "WHITE");
	    //add("RED", redPanel);
	    add(redPanel, "RED");
	    //add("YELLOW", yellowPanel);
	    add(yellowPanel, "YELLOW");
	    //add("BLUE", bluePanel);
	    add(bluePanel, "BLUE");
	    //add("GREEN", greenPanel);
	    add(greenPanel, "GREEN");
	    //add("ORANGE", orangePanel);
	    add(orangePanel, "ORANGE");
	}
    }

    private JPanel createColorPanel(Color color){
	JPanel tempPanel = new JPanel();
	tempPanel.setBackground(color);
	return tempPanel;
    }

    
    private class DrawListener implements ActionListener{
	// Every time we click the button, it will perform
	// the following action.
	//	CardPanel cPanel;
	
	public DrawListener (CardPanel cPanel){
	    //	    this.cPanel = cPanel;
	}
	
	public void actionPerformed(ActionEvent e){
	    cPanel.cardLayout.show(cPanel, drawDeck.draw().getColor());
	}
    }
}

