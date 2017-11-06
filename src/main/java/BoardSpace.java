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
			label = newLabel;
			add(label);
		}
		else{
			label = new JLabel("Test");
		}

		// "originalText"
		if(newLabel != null){
			originalText = label.getText();
		}
		else{
			originalText = new String("tesT");
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

		updateText();
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
		String labelText = new String(originalText);
		for(Player player : players){
			labelText = labelText + player.getName() + ", ";
		}
		label.setText(labelText);
	}

	// TODO
	public String toString(){
		return super.toString();
	}


	// ------------------- //
	// Getters and Setters //
	// ------------------- //
	// "label"
	// public JLabel getLabel(){
	// 	return label;
	// }
	// public void setLabel(JLabel label){
	// 	this.label = label;
	// }
	// public void setLabel(String label){
	// 	this.setLabel(new JLabel(label));
	// }

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