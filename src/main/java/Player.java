public class Player{
    private String name;
    private BoardSpace position = null; //all players start at "0th" position according to write up
    private String posColor; //
    private int token;//what should this be? int doesn't feel right
    private int wins = 0;

    public Player(String n, int t){
        name = n;
        token = t;
    }
    public Player(String n){
        name = n;
    }

    public boolean Move(BoardSpace bs){
        setPosition(bs);
        // setposColor(bs.Color);
        return true;
    }

    public BoardSpace getPosition(){
        return position;
    }
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

    public int getToken(){
        return token;
    }
    public boolean setToken(int t){
        token = t;
        return true;
    }

    // public String getposColor(){
    //     return posColor;
    // }
    // public boolean setposColor(String c){
    //     posColor = c;
    //     return true;
    // }

    public int getWins(){
        return wins;
    }
    //shouldn't need to add more then 1 win at a time...
    public boolean addWin(){
        wins++;
        return true;
    }
}
