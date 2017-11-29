/*
 * @author Benjamin Muscato (Github: "BenjaminMuscato"). Primary author of this file, used originally in the "BitsPlease" repository.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.io.Serializable;

public class BoardSpace implements Serializable {
    private static final long serialVersionUID = 1L;
	private static final Color BORDER_COLOR = Color.BLACK;
	private static final Color DEFAULT_LABEL_FOREGROUND_COLOR = Color.BLACK;

	private Collection<Player> players;
	private String originalText;
	private Color spaceColor = DeckPanel.DEFAULT_COLOR;
	private Color labelForegroundColor = DEFAULT_LABEL_FOREGROUND_COLOR;
	private boolean isGrandmasHouse = false;
	private boolean isStartSpace = false;

	private transient JPanel mainPanel;
	private transient JLabel label;

	// ------------ //
	// Constructors //
	// ------------ //
	public BoardSpace(Color newBackgroundColor, JLabel newLabel, Player[] newPlayers){
		// "spaceColor"
		spaceColor = newBackgroundColor;

		intitializeMainPanel();

		// "label"
		label = newLabel;
		if(label == null){
			intitializeLabel();
		}
		mainPanel.add(label);
		labelForegroundColor = label.getForeground();

		// "originalText"
		originalText = label.getText();

		// "players"
		if(newPlayers != null){
			players = new ArrayList<Player>(newPlayers.length);
			for(Player player : newPlayers){
				this.addPlayer(player);
			}
		}
		else{
			players = new ArrayList<Player>(0);
		}

		updateText(); // Updates the JLabel text of this BoardSpace to include any Players on this space
	}
	public BoardSpace(Color backgroundColor, JLabel label){
		this(backgroundColor, label, null);
	}
	public BoardSpace(Color backgroundColor){
		this(backgroundColor, null, null);
	}
	public BoardSpace(){
		this(Color.WHITE, null, null);
	}


	// ------------- //
	// Misc. Methods //
	// ------------- //
	public JPanel getPanel(){
		if(mainPanel == null){
			this.intitializeMainPanel();
			this.updateText();
		}

		return mainPanel;
	}

	private void intitializeMainPanel(){
		mainPanel = new JPanel();
		mainPanel.setBackground(spaceColor);
		mainPanel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
	}

	private void intitializeLabel(){
		label = new JLabel(originalText);
		label.setForeground(labelForegroundColor);

		if(mainPanel == null){
			intitializeMainPanel();
		}
		mainPanel.add(label);
	}

	public void updateText(){
		String labelText = new String("<html>" + originalText);
		if(!players.isEmpty()){
			labelText = labelText + "<br>[";
			for(Player player : players){
				labelText = labelText + "<img src=\"" + BoardSpace.class.getResource(player.getToken()) + "\">, ";
			}
			labelText = labelText.substring(0, labelText.length() - 2);
			labelText = labelText + "]";
		}
		labelText = labelText + "</html>";
		//label.setFont(new Font("Dialog", Font.PLAIN, 12));

		if(label == null){
			intitializeLabel();
		}

		label.setText(labelText);
		label.setForeground(labelForegroundColor);
		label.setVisible(true);
	}


	// ------------------- //
	// Getters and Setters //
	// ------------------- //
	// "players"
	public void addPlayer(Player player){
		// Check to make sure this Player isn't already here
		if(players.contains(player)){
			return;
		}

		// Now that we're sure the Player isn't already here,
		//	add them to the collection
		players.add(player);

		updateText();
	}
	public boolean removePlayer(Player player){
		boolean result = players.remove(player);
		updateText();
		return result;
	}

	// "label"
	public JLabel getLabel(){
		return label;
	}

	// "spaceColor"
	public Color getSpaceColor(){
		return spaceColor;
	}

	// "isGrandmasHouse"
	public void setAsGrandmasHouse(boolean isGrandmasHouse){
		this.isGrandmasHouse = isGrandmasHouse;
	}

	// "isStartSpace"
	public void setAsStartSpace(boolean isStartSpace){
		this.isStartSpace = isStartSpace;
	}



	// --------------- //
	// Boolean Methods //
	// --------------- //
	public boolean containsPlayer(Player player){
		return players.contains(player);
	}
	public boolean isEmpty(){
		return players.isEmpty();
	}
	public boolean isGrandmasHouse(){
		return isGrandmasHouse;
	}
	public boolean isStartSpace(){
		return isStartSpace;
	}
}
