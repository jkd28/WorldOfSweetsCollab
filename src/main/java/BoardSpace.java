/*
 * @author Benjamin Muscato (Github: "BenjaminMuscato"). Primary author of this file, used originally in the "BitsPlease" repository.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;

public class BoardSpace extends JPanel{
	private JLabel label;
	private Collection<Player> players;
	private String originalText;
	private static final Color BORDER_COLOR = Color.BLACK;


	// ------------ //
	// Constructors //
	// ------------ //
	public BoardSpace(Color newBackgroundColor, JLabel newLabel, Player[] newPlayers){
		this.setBackground(newBackgroundColor);
		this.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		
		// "label"
		if(newLabel != null){
			label = new JLabel("<html>" + newLabel.getText() + "</html>");
			add(label); 		// Actually add the JLabel to this BoardSpace (which is just an extension of JPanel)
		}
		else{
			label = new JLabel("<html></html>");
		}

		// "originalText"
		if(newLabel != null){
			originalText = newLabel.getText();
		}
		else{
			originalText = new String("");
		}

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
	private void updateText(){
		String labelText = new String("<html>" + originalText);
		if(!players.isEmpty()){
			labelText = labelText + "<br>[";
			for(Player player : players){
				labelText = labelText + player.getName() + ", ";
			}
			labelText = labelText.substring(0, labelText.length() - 2);
			labelText = labelText + "]";
		}
		labelText = labelText + "</html>";

		label.setText(labelText);
	}

	// TODO
	public String toString(){
		return super.toString();
	}


	// ------------------- //
	// Getters and Setters //
	// ------------------- //
	// "players"
	public void addPlayer(Player player){
		players.add(player);
		updateText();
	}
	public boolean removePlayer(Player player){
		boolean result = players.remove(player);
		updateText();
		return result;
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
}