import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TimerPanel extends JPanel{
    public boolean gameFinished = false;
    public TimerPanel(){
        //put in a thread as per project instructions
        Thread myThread = new Thread(() -> {
            Timer timer = new Timer();
            JLabel label = new JLabel("Game Time: " + timer.getRealTime());
            label.setFont(new Font("Dialog", Font.BOLD, 20));
            add(label);
            while (!gameFinished){
                timer.updateSeconds();
                timer.setRealTime(timer.updateTime());
                label.setText("Game Time: " + timer.getRealTime());
                try{
                    Thread.sleep(timer.SEC_IN_MS);
                }catch(Exception e){
                    System.out.println("something went wrong.");
                }
            }
        });
        myThread.start();
    }
}
