import java.lang.StringBuilder;

public class Timer{
    public static final int SEC_IN_MS = 1000;
    private int displaySeconds = 0;
    private int realSeconds = 0;
    private String[] times = {"00", ":", "00", ":", "00"};
    private String realTime = "00:00:00";

    public String updateTime(){
      int hours = realSeconds / 3600;
      displaySeconds = realSeconds - (hours * 3600);
      int minutes = realSeconds / 60;
      displaySeconds = realSeconds - (minutes * 60);
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

      StringBuilder strBuilder = new StringBuilder();
      for (int i = 0; i < times.length; i++){
        strBuilder.append(times[i]);
      }
      realTime = strBuilder.toString();
      return realTime;
    }

    public boolean updateSeconds(){
      realSeconds++;
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
