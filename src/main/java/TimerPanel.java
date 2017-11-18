import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TimerPanel extends JPanel{
    public TimerPanel(){
        Thread myThread = new Thread(() -> {
            Timer timer = new Timer();
            JLabel label = new JLabel(timer.getRealTime());
            add(label);
            while (true){
                timer.updateSeconds();
                timer.setRealTime(timer.updateTime());
                label.setText(timer.getRealTime());
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
