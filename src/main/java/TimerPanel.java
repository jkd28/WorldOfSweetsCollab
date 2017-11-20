import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.Serializable;

public class TimerPanel extends JPanel implements Serializable{
    public boolean gameFinished = false;
    public boolean gameStarted = false;
    public TimerPanel(){
        //put in a thread as per project instructions
        Thread myThread = new Thread(() -> {
            Timer timer = new Timer();
            JLabel label = new JLabel("Game Time: " + timer.getRealTime());
            label.setFont(new Font("Dialog", Font.BOLD, 20));
            add(label);
            while (!gameFinished){
                if (gameStarted){
                    timer.updateSeconds();
                    timer.setRealTime(timer.updateTime());
                    label.setText("Game Time: " + timer.getRealTime());
                }
                //this can't be in the block or else it won't be given time to compute
                try{
                    Thread.sleep(timer.SEC_IN_MS);
                }catch(Exception e){
                    System.out.println("something went wrong.");
                }
            }
            //System.out.println(gameFinished + " " + gameStarted);
        });
        myThread.start();
        //afafaff
    }
}
