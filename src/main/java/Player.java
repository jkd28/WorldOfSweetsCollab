import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private BoardSpace position = null; //all players start at "0th" position according to write up
    private String token;
    public Player(String n){
        name = n;
    }

    //Use an instance of a boardspace object as position
    public BoardSpace getPosition(){
        return position;
    }

    //Use an instance of a boardspace object as position
    public boolean setPosition(BoardSpace bs){
        position = bs;
        return true;
    }

    public String getName(){
        return name;
    }
    public boolean setName(String n){
        name = n;
        return true;
    }

    public String getToken(){
      return token;
    }
    public boolean setToken(String t){
      token = t;
      return true;
    }
}
