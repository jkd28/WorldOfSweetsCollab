import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TokenPanel{
  private String token = "t";
  private JButton previousButton = new JButton("err");

  private String[] emojis = {"\uD83C\uDF6C", "\uD83C\uDF66", "\uD83C\uDF69", "\uD83C\uDF70", "\uD83E\uDDC0",
                           "\uD83D\uDC72", "\uD83D\uDDFF", "\uD83E\uDD16", "\uD83D\uDC7D", "\uD83D\uDC7A",
                           "\uD83E\uDD84", "\uD83E\uDD8D", "\uD83E\uDD91", "\uD83E\uDD86", "\uD83E\uDD8A",
                           "\uD83E\uDD40", "\uD83D\uDC42", "\uD83D\uDD96", "\uD83D\uDCA9", "\uD83D\uDD0A"};

  JFrame _frame = new JFrame("Tokens");
  JPanel panel = new JPanel();
  JLabel label = new JLabel("Choose a token:");
  JButton end = new JButton("Finish");

	public TokenPanel(){
    panel.setLayout(new GridLayout(0,5));
    ActionListener buttonListener = new ButtonListener();
    for(int i = 0; i < emojis.length; i++){
      JButton button = new JButton(emojis[i]);
      button.setFont(new Font("Dialog", Font.PLAIN, 24));
      button.addActionListener(buttonListener);
      panel.add(button);
    }
    end.setFont(new Font("Dialog", Font.PLAIN, 24));
    end.addActionListener(buttonListener);
    label.setFont(new Font("Dialog", Font.PLAIN, 36));
    final JDialog frame = new JDialog(_frame, "hello", true);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().add(panel, BorderLayout.CENTER);
    frame.getContentPane().add(label, BorderLayout.NORTH);
    frame.add(end, BorderLayout.SOUTH);
    frame.pack();
    frame.setVisible(true);
	}

	 public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TokenPanel();
	}
  private class ButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JButton source = (JButton)e.getSource();
      if (source.getText().equals("Finish")){
          _frame.dispose();
      }
      setToken(source.getText());
      //test.setText(getToken());
      source.setEnabled(false);
      if (!previousButton.getText().equals("err")){
        previousButton.setEnabled(true);
      }
      previousButton = source;
    }
  }
  public String getToken(){
    return token;
  }
  public boolean setToken(String s){
    token = s;
    return true;
  }
}
