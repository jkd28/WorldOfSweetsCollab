import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class TokenPanel{
  private String token = "t";
  private JButton previousButton = new JButton("err");
  private String[] emojis = {"candy.png", "donut_emoji.png", "Ice_Cream_Emoji.png", "Strawberry_Cake_Emoji.png", "cheese-wedge.png",
                            "alien.png", "goblin.png", "robot-face.png", "jack-o-lantern.png", "man-with-chinese-cap.png",
                             "gorilla.png", "squid.png", "duck.png", "spouting-whale.png", "unicorn-face.png",
                             "ear.png", "gem-stone.png", "key.png", "light-bulb.png", "vulcan-salute.png"};

  /*private String[] emojis = {"\uD83C\uDF6C", "\uD83C\uDF66", "\uD83C\uDF69", "\uD83C\uDF70", "\uD83E\uDDC0",
                           "\uD83D\uDC72", "\uD83D\uDDFF", "\uD83E\uDD16", "\uD83D\uDC7D", "\uD83D\uDC7A",
                           "\uD83E\uDD84", "\uD83E\uDD8D", "\uD83E\uDD91", "\uD83E\uDD86", "\uD83E\uDD8A",
                           "\uD83E\uDD40", "\uD83D\uDC42", "\uD83D\uDD96", "\uD83D\uDCA9", "\uD83D\uDD0A"};*/


  JFrame _frame = new JFrame("Tokens");
  JPanel panel = new JPanel();
  JLabel label = new JLabel("Choose a token:");
  JButton end = new JButton("Finish");

	public TokenPanel(){
    panel.setLayout(new GridLayout(0,5));
    ActionListener buttonListener = new ButtonListener();
    try{
    for(int i = 0; i < emojis.length; i++){
      String filepath = "src/main/resources/" + emojis[i];
      BufferedImage myPicture = ImageIO.read(new File(filepath));
      ImageIcon icon = new ImageIcon(myPicture);
      icon.setDescription(emojis[i]);
      JButton button = new JButton(icon);
      button.setFont(new Font("Dialog", Font.PLAIN, 24));
      button.addActionListener(buttonListener);
      panel.add(button);
    }
    }catch(Exception e){
        System.out.println("Something went wrong.");
        e.printStackTrace();
    }
    end.setFont(new Font("Dialog", Font.PLAIN, 24));
    end.addActionListener(buttonListener);
    end.setEnabled(false);
    label.setFont(new Font("Dialog", Font.PLAIN, 36));
    final JDialog frame = new JDialog(_frame, "Token Selector", true);
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
      end.setEnabled(true);
      if (source.getText().equals("Finish")){
          _frame.dispose();
      }else if (!source.getText().equals("Finish")){
          ImageIcon icon = (ImageIcon)source.getIcon();
          setToken(icon.getDescription());
          source.setEnabled(false);
          if (!previousButton.getText().equals("err")){
            previousButton.setEnabled(true);
          }
          previousButton = source;
      }
    }
  }
  public String getToken(){
    return token;
  }
  public boolean setToken(String i){
    token = i;
    return true;
  }
}
