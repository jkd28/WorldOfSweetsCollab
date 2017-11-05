import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayerPanel extends JPanel{
    private JPanel currPanel = new JPanel(new GridBagLayout());
    private JPanel nextPanel = new JPanel(new GridBagLayout());
    private GridBagLayout gridBag = new GridBagLayout();
    private JPanel orderPanel = new JPanel(gridBag);
    private JLabel currDisplayText = new JLabel();
    private JLabel nextDisplayText = new JLabel();
    private JLabel ordDisplayText = new JLabel();
    private final String START_TEXT = "First turn!";
    private final String CURR_PRE_TEXT = "Current Player: \n";
    private final String NEXT_PRE_TEXT = "Next Player: \n";
    private final String ORD_PRE_TEXT = "Player Order: \n";
    private GridBagConstraints c = new GridBagConstraints();

    public PlayerPanel(Player[] players){
        setLayout(new GridLayout(3, 1));

	// Set the initial text of each JPanel
	currDisplayText.setText(START_TEXT);
	nextDisplayText.setText(NEXT_PRE_TEXT + players[0].getName());
	ordDisplayText.setText(ORD_PRE_TEXT);

    	c.anchor = GridBagConstraints.WEST; // Force JLabels to align left
	c.gridy = 0; // Place the first JLabel in the first row
	orderPanel.add(ordDisplayText, c);

	// Add each player's name to the turn order
	for (int i = 0; i < players.length; i++){
	    JLabel tempLabel = new JLabel((i+1) + " " + players[i].getName());
	    c.gridy = i+1; // Place each JLabel in the next row successively 
	    orderPanel.add(tempLabel, c);
	}

	// Add the JLabels to current and next player JPanels
	currPanel.add(currDisplayText);
	nextPanel.add(nextDisplayText);

	// Add the JPanels to the main JPanel
    	add(currPanel);
	add(nextPanel);
	add(orderPanel);
    }

    // Change the current and next players
    public void changePlayer(Player[] players, int nextPlayer){
	if (nextPlayer == 0){
	    currDisplayText.setText(CURR_PRE_TEXT + players[3].getName());
	}else{
	    currDisplayText.setText(CURR_PRE_TEXT + players[nextPlayer-1].getName());
	}
        nextDisplayText.setText(NEXT_PRE_TEXT + players[nextPlayer].getName());
    }

    public String getCurrPlayerText(){
	return currDisplayText.getText();
    }

    public String getNextPlayerText(){
	return nextDisplayText.getText();
    }

    public String[] getOrderText(){
	Component[] components = orderPanel.getComponents();
	String[] orderText = new String[components.length];

	orderText[0] = ordDisplayText.getText();
	for (int i = 1; i < components.length; i++){
	    JLabel tempLabel = (JLabel)orderPanel.getComponent(i);
	    orderText[i] = tempLabel.getText();
	}

	return orderText;
    }
}
