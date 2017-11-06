/*
 * @author Benjamin Muscato (Github: "BenjaminMuscato"). Primary author of this file, used originally in the "BitsPlease" repository.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;

public class BoardSpace extends JPanel{
	private String label;
	private Color backgroundColor;
	private Collection<Player> players;


	// ------------ //
	// Constructors //
	// ------------ //
	public BoardSpace(Color backgroundColor, String label, Player[] players){
		this.backgroundColor = backgroundColor;
		this.label = label;

		if(players != null){
			this.players = new ArrayList<Player>(players.length);
			for(Player player : players){
				this.addPlayer(player);
			}
		}
		else{
			players = new ArrayList<Player>(0);
		}
	}
	public BoardSpace(Color backgroundColor, String label){
		this(backgroundColor, null, null);
	}
	public BoardSpace(Color backgroundColor){
		this(backgroundColor, null, null);
	}


	// ------------------- //
	// Getters and Setters //
	// ------------------- //
	// "backgroundColor"
	public Color getBackgroundColor(){
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor){
		this.backgroundColor = backgroundColor;
	}

	// "label"
	public String getLabel(){
		return new String(label);
	}
	public void setLabel(String label){
		this.label = label;
	}

	// "players"
	public void addPlayer(Player player){
		players.add(player);
	}
	public boolean removePlayer(Player player){
		return players.remove(player);
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