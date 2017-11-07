import java.awt.Color;

public class Card{
    private int value;  // Can be 0 (skip), 1 (single), 2 (double)
    private Color color; // Can be Red, Blue

    public static final int SKIP = 0;
    public static final int SINGLE = 1;
    public static final int DOUBLE = 2;
    public static final int GO_TO_MIDDLE = 3;

    public static final Color[] VALID_COLORS = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.ORANGE};

    public Card(int v, Color c){
        value = v;
        color = c;
        if (!validateValueColorPair()) {
            throw new IllegalArgumentException("Invalid value/color pairing");
        }
    }

    // Accessors
    public int getValue(){
        return value;
    }

    public Color getColor(){
        return color;
    }

    public static boolean isValidColor(Color color){
        for(Color validColor : VALID_COLORS){
            if (color.equals(validColor)) {
                return true;
            }
        }

        return false;
    }

    // Ensure that the supplied value/color pairing is valid for the game rules
    private boolean validateValueColorPair(){
        if (value == Card.SINGLE || value == Card.DOUBLE){
            for(Color validColor : VALID_COLORS){
                if (color.equals(validColor)) {
                    return true;
                }
            }
            // if no valid color matches the supplied color, it is invalid
            return false;

        } else if (value == Card.SKIP) {
            return true;
        }else if (value == Card.GO_TO_MIDDLE){
            return true;
        } else {
            return false;
        }
    }
}
