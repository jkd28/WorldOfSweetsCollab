import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class TokenPanel implements Serializable{
    private static final long serialVersionUID = 1L;
    public static final int FRAME_HEIGHT = 300;
    public static final int FRAME_WIDTH = 300;
    public static final String DEFAULT_DIRECTORY = "src/main/resources/";

    private String[] usedTokens;
    private String token = null;
    private ActionListener buttonListener;
    private File directory;
    
    private transient JFrame frame;
    private transient JPanel panel;
    private transient JDialog dialog;
    private transient JLabel label;
    private transient JButton previousButton;
    private transient JButton end;

    public TokenPanel(String[] used){
        initializeDefaultTokenPanelFrameStuff();

        usedTokens = used;
        
        directory = new File(DEFAULT_DIRECTORY);
        // directory = new File("../resources/");
        loadPanelWithIcons(directory);
    }
    public TokenPanel(){
        this(new String[0]);
    }

    public void setVisible(boolean setVisible){
        if(frame == null){
            initializeDefaultTokenPanelFrameStuff();
        }

        frame.setVisible(setVisible);
        dialog.setVisible(setVisible);
    }
    public String getSelectedToken(){
        return token;
    }
    public boolean setSelectedToken(String i){
        token = i;
        return true;
    }


    private void loadPanelWithIcons(File directory){
        if(!directory.isDirectory()){
            System.err.println(String.format("'%s' is not a valid resource directory.", directory.getName()));
            System.exit(1);
        }

        if(frame == null){
            initializeDefaultTokenPanelFrameStuff();
        }

        try{
            for(File child : directory.listFiles()){
                if(child.getName().toLowerCase().endsWith(".png")){
                    BufferedImage myPicture = ImageIO.read(child);
                    ImageIcon icon = new ImageIcon(myPicture);
                    icon.setDescription(child.getName());
                    JButton button = new JButton(icon);
                    button.setFont(new Font("Dialog", Font.PLAIN, 24));
                    button.addActionListener(buttonListener);
                    for (int i = 0; i < usedTokens.length; i++){
                        if (child.getName().equals(usedTokens[i])){
                            button.setEnabled(false);
                        }
                    }
                    panel.add(button);
                }
            }
        }
        catch(Exception e){
            System.out.println("Something went wrong.");
            e.printStackTrace();
            System.exit(1);
        }
    }


    private void initializeDefaultTokenPanelFrameStuff(){
        buttonListener = new ButtonListener();

        frame = new JFrame("Tokens");

        previousButton = new JButton("err");

        end = new JButton("Finish");
        end.setFont(new Font("Dialog", Font.PLAIN, 24));
        end.addActionListener(buttonListener);
        end.setEnabled(false);

        panel = new JPanel();
        panel.setLayout(new GridLayout(0,5));

        label = new JLabel("Choose a token:");
        label.setFont(new Font("Dialog", Font.PLAIN, 36));

        dialog = new JDialog(frame, "Token Selector", true);
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog.getContentPane().add(panel, BorderLayout.CENTER);
        dialog.getContentPane().add(label, BorderLayout.NORTH);
        dialog.add(end, BorderLayout.SOUTH);
        dialog.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        // dialog.pack();
    }



    private class ButtonListener implements ActionListener, Serializable{
        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e){
            if(frame == null){
                initializeDefaultTokenPanelFrameStuff();
            }

            JButton source = (JButton)e.getSource();
            end.setEnabled(true);
            if (source.getText().equals("Finish")){
                frame.dispose();
            }else if (!source.getText().equals("Finish")){
                dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ImageIcon icon = (ImageIcon)source.getIcon();
                setSelectedToken(icon.getDescription());
                source.setEnabled(false);
                if (!previousButton.getText().equals("err")){
                    previousButton.setEnabled(true);
                }
                previousButton = source;
            }
        }
    }
}
