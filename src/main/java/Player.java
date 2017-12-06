import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private BoardSpace position = null; //all players start at "0th" position according to write up
    private String token;
    private boolean dad = false;
    public Player(String n){
        name = n;
	if(name.equals("Dad")){
	    dad = true;
	}
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
	if(name.equals("Dad")){
	    dad = true;
	}
        return true;
    }

    public boolean isDad(){
	return dad;
    }

    public String getToken(){
      return token;
    }
    public boolean setToken(String t){
      token = t;
      return true;
    }
}
