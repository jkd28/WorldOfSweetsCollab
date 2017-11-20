import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class TokenPanel{
    private String[] usedTokens;
    private String token = "t";
    private JButton previousButton = new JButton("err");
    JFrame _frame = new JFrame("Tokens");
    JPanel panel = new JPanel();
    JLabel label = new JLabel("Choose a token:");
    JButton end = new JButton("Finish");

	public TokenPanel(String[] used){
        usedTokens = used;
        panel.setLayout(new GridLayout(0,5));
        ActionListener buttonListener = new ButtonListener();
        File dir = new File("src/main/resources/");
        File[] directoryListing = dir.listFiles();
        try{
            for(File child: directoryListing){
                BufferedImage myPicture = ImageIO.read(child);
                ImageIcon icon = new ImageIcon(myPicture);
                icon.setDescription(child.getName());
                JButton button = new JButton(icon);
                button.setFont(new Font("Dialog", Font.PLAIN, 24));
                button.addActionListener(buttonListener);
                for (int i = 0; i < usedTokens.length; i++){
                    if (child.getName().equals(usedTokens[i])){
                        System.out.println("sucess");
                        button.setEnabled(false);
                    }
                }
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
        new TokenPanel(null);
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
