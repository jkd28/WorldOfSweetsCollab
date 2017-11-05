import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayerPanel extends JPanel{
    private JPanel currPanel = new JPanel(new GridBagLayout());
    private JPanel nextPanel = new JPanel(new GridBagLayout());
    private JLabel currDisplayText = new JLabel();
    private JLabel nextDisplayText = new JLabel();
    private final String START_TEXT = "First turn!";
    private final String CURR_PRE_TEXT = "Current Player: \n";
    private final String NEXT_PRE_TEXT = "Next Player: \n";

    public PlayerPanel(Player p){
        setLayout(new GridLayout(3, 1));

	currDisplayText.setText(START_TEXT);
	nextDisplayText.setText(NEXT_PRE_TEXT + p.getName());
	
	currPanel.add(currDisplayText);
	nextPanel.add(nextDisplayText);

    	add(currPanel);
	add(nextPanel);
    }

    public void changePlayer(Player newPlayer){
        nextDisplayText.setText(NEXT_PRE_TEXT + newPlayer.getName());
    }
}
