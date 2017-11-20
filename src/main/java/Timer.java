import java.io.Serializable;

public class Timer implements Serializable {
    public static final int SEC_IN_MS = 1000;
    //total time in seconds
    private int totalSeconds = 0;
    //String array representing the parts of the timer
    private String[] times = {"00", ":", "00", ":", "00"};
    //times array concatenated into a string that is displayed on the timer
    private String realTime = "00:00:00";

    public String updateTime(){
      int displaySeconds;
      int hours = totalSeconds / 3600;
      displaySeconds = totalSeconds - (hours * 3600);
      int minutes = totalSeconds / 60;
      displaySeconds = totalSeconds - (minutes * 60);
      minutes -= (minutes / 60 * 60);
      String realTime;
      String str;

      if (hours < 10){
        str = "0" + Integer.toString(hours);
      }else{
        str = Integer.toString(hours);
      }
      times[0] = str;

      if (minutes < 10){
        str = "0" + Integer.toString(minutes);
      }else{
        str = Integer.toString(minutes);
      }
      times[2] = str;

      if (displaySeconds < 10){
        str = "0" + Integer.toString(displaySeconds);
      }else{
        str = Integer.toString(displaySeconds);
      }
      times[4] = str;

      realTime = times[0] + times[1] + times[2] + times[3] + times[4];
      return realTime;
    }

    public boolean updateSeconds(){
      totalSeconds++;
      return true;
    }
    public String getRealTime(){
        return realTime;
    }
    public boolean setRealTime(String s){
        realTime = s;
        return true;
    }
}
