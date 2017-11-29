import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.Serializable;
import java.math.*;

public class TimerPanel implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final Font LABEL_FONT = new Font("Dialog", Font.BOLD, 20);

    public static final int MILLISECONDS_PER_SECOND = 1000;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int SECONDS_PER_HOUR = 3600; //3600 = 60 * 60
    public static final int DELAY_IN_MILLISECONDS = MILLISECONDS_PER_SECOND;
    public static final String DEFAULT_LABEL_TEXT = "00:00:00";

    private javax.swing.Timer timer;
    private int numSeconds;
    private transient JLabel label;

    public TimerPanel(int startingTimeInMilliseconds){
        initializeDefaultLabel();

        timer = new javax.swing.Timer(DELAY_IN_MILLISECONDS, new TimerListener(this));

        if(startingTimeInMilliseconds > 0){
            numSeconds = (int) Math.floor(startingTimeInMilliseconds / MILLISECONDS_PER_SECOND);
        }
        else{
            numSeconds = 0;
        }

        updateLabel();
    }
    public TimerPanel(){
        this(0);
    }

    public void startTimer(){
        timer.start();
    }

    public void stopTimer(){
        timer.stop();
    }

    public boolean timerIsRunning(){
        return timer.isRunning();
    }

    public void incrementTimer(){
        numSeconds++;
    }

    public String getRealTime(){
        int totalSeconds = numSeconds;

        int hours = (int) Math.floor( totalSeconds / SECONDS_PER_HOUR );
        totalSeconds -= hours * SECONDS_PER_HOUR;

        int minutes = (int) Math.floor( totalSeconds / SECONDS_PER_MINUTE );
        totalSeconds -= minutes * SECONDS_PER_MINUTE;

        int seconds = totalSeconds; // Since all that's left is the seconds

        String realTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        return realTime;
    }

    public void updateLabel(){
        if(label == null){
            initializeDefaultLabel();
        }

        label.setText(getRealTime());
    }

    public JLabel getLabel(){
        if(label == null){
            initializeDefaultLabel();
        }

        return label;
    }

    private void initializeDefaultLabel(){
        label = new JLabel(DEFAULT_LABEL_TEXT);
        label.setFont(TimerPanel.LABEL_FONT);
    }

    private class TimerListener implements ActionListener, Serializable{
        private static final long serialVersionUID = 1L;
        
        private TimerPanel timerPanel;

        private TimerListener(TimerPanel timerPanel){
            this.timerPanel = timerPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            timerPanel.incrementTimer();
            timerPanel.updateLabel();
        }
    }
}
