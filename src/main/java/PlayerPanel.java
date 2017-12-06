import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.Serializable;

public class PlayerPanel implements Serializable {
	private static final long serialVersionUID = 1L;
    private static final String START_TEXT = "First turn!";
    private static final String CURR_PRE_TEXT = "Current Player: \n";
    private static final String NEXT_PRE_TEXT = "Next Player: \n";
    private static final String ORD_PRE_TEXT = "Player Order: \n";

    private Player[] players;
    private Player currentPlayer;
    private Player nextPlayer;

    private transient JPanel mainPanel;
    private transient JPanel currPanel;
    private transient JPanel nextPanel;
    private transient JPanel orderPanel;
    private transient JLabel currDisplayText;
    private transient JLabel nextDisplayText;
    private transient JLabel ordDisplayText;
    private transient GridBagLayout gridBag;
    private transient GridBagConstraints constraints;

    public PlayerPanel(Player[] newPlayers){
    	players = newPlayers;
        if(players.length > 0){
            nextPlayer = players[0];
        }

    	initializeSwingComponents();
    }
    public PlayerPanel(){
    	this(new Player[0]);
    }

    public JPanel getPanel(){
    	if(mainPanel == null){
    		initializeSwingComponents();
    	}

    	return mainPanel;
    }

    // Change the current and next players
    public void changePlayer(Player currentPlayer, Player nextPlayer){
    	if(mainPanel == null){
    		initializeSwingComponents();
    	}

        this.currentPlayer = currentPlayer;
        this.nextPlayer = nextPlayer;

        this.updateCurrAndNextPlayerDisplayText();
    }

    private void updateCurrAndNextPlayerDisplayText(){
        if(currentPlayer != null){
            currDisplayText.setText(CURR_PRE_TEXT + currentPlayer.getName() + " has " + currentPlayer.getNumBoomerangs() + " boomerangs");
        }
        else{
            currDisplayText.setText(START_TEXT);
        }

        if(nextPlayer != null){
            nextDisplayText.setText(NEXT_PRE_TEXT + nextPlayer.getName() + " has " + nextPlayer.getNumBoomerangs() + " boomerangs");
        }
        else{
            nextDisplayText.setText(NEXT_PRE_TEXT);
        }
    }

    public String getCurrPlayerText(){
    	if(mainPanel == null){
    		initializeSwingComponents();
    	}

		return currDisplayText.getText();
    }

    public String getNextPlayerText(){
    	if(mainPanel == null){
    		initializeSwingComponents();
    	}

		return nextDisplayText.getText();
    }

    public String[] getOrderText(){
    	if(mainPanel == null){
    		initializeSwingComponents();
    	}

		Component[] components = orderPanel.getComponents();
		String[] orderText = new String[components.length];

		orderText[0] = ordDisplayText.getText();
		for (int i = 1; i < components.length; i++){
		    JLabel tempLabel = (JLabel)orderPanel.getComponent(i);
		    orderText[i] = tempLabel.getText();
		}

		return orderText;
    }

    private void initializeSwingComponents(){
    	mainPanel = new JPanel(new GridLayout(3, 1));
		currPanel = new JPanel(new GridBagLayout());
	    nextPanel = new JPanel(new GridBagLayout());
	    orderPanel = new JPanel(new GridBagLayout());
	    currDisplayText = new JLabel();
	    nextDisplayText = new JLabel();
	    ordDisplayText = new JLabel();
	    gridBag = new GridBagLayout();
	    constraints = new GridBagConstraints();

	    // Set the initial text of each JPanel
		updateCurrAndNextPlayerDisplayText();
		ordDisplayText.setText(ORD_PRE_TEXT);

	    constraints.anchor = GridBagConstraints.WEST; // Force JLabels to align left
		constraints.gridy = 0; // Place the first JLabel in the first row
		orderPanel.add(ordDisplayText, constraints);

		// Add each player's name to the turn order
		for (int i = 0; i < players.length; i++){
		    JLabel tempLabel = new JLabel((i+1) + ") " + players[i].getName());
		    constraints.gridy = i+1; // Place each JLabel in the next row successively
		    orderPanel.add(tempLabel, constraints);
		}

		// Add the JLabels to current and next player JPanels
		currPanel.add(currDisplayText);
		nextPanel.add(nextDisplayText);

		// Add the JPanels to the main JPanel
	    mainPanel.add(currPanel);
		mainPanel.add(nextPanel);
		mainPanel.add(orderPanel);
    }
}
