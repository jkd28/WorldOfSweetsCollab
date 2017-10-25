public class Card{
    private int value;  // Can be either 1 or 2
    private String color; // Can be Red, Blue

    public Card(int v, String c){
        value = v;
        color = c.toUpperCase();
    }

    // Accessors
    public int getValue(){
        return value;
    }

    public String getColor(){
        return color;
    }
}
