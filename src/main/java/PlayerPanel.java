import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayerPanel extends JPanel{
    private JLabel displayText = new JLabel();
    private final String PRE_TEXT = "Current Player: \n";

    public PlayerPanel(Player p){
        setLayout(new BorderLayout());

        displayText.setText(PRE_TEXT + p.getName());
        displayText.setHorizontalAlignment(JLabel.CENTER);
        displayText.setVerticalAlignment(JLabel.CENTER);
    	add(displayText, BorderLayout.CENTER);
    }

    public void changePlayer(Player newPlayer){
        displayText.setText(PRE_TEXT + newPlayer.getName());
    }
}
