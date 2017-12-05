import java.io.Serializable;
import java.awt.Color;

public class Card implements Serializable {
    private static final long serialVersionUID = 1L;
    private int value;  // Can be 0 (skip), 1 (single), 2 (double), 3-7 (special)
    private Color color; // Can be Red, Yellow, Blue, Green, Orange

    public static final int SKIP = 0;
    public static final int SINGLE = 1;
    public static final int DOUBLE = 2;
    public static final int GO_TO_FIRST_SPECIAL = 3;
    public static final int GO_TO_SECOND_SPECIAL = 4;
    public static final int GO_TO_THIRD_SPECIAL = 5;
    public static final int GO_TO_FOURTH_SPECIAL = 6;
    public static final int GO_TO_FIFTH_SPECIAL = 7;

    public static final String SKIP_TEXT = "<html>Skip!</html>";
    public static final String GO_TO_FIRST_SPECIAL_TEXT = "<html>Go to<br>Candy Cane Forest!</html>";
    public static final String GO_TO_SECOND_SPECIAL_TEXT = "<html>Go to<br>Minty Mountains!</html>";
    public static final String GO_TO_THIRD_SPECIAL_TEXT = "<html>Go to<br>Bubble Gum Trapeze!</html>";
    public static final String GO_TO_FOURTH_SPECIAL_TEXT = "<html>Go to<br>Marshmallow Marsh!</html>";
    public static final String GO_TO_FIFTH_SPECIAL_TEXT = "<html>Go to<br>Licorice Lagoon!</html>";

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
        }else if (value == Card.GO_TO_FIRST_SPECIAL){
            return true;
	}else if (value == Card.GO_TO_SECOND_SPECIAL){
            return true;
	}else if (value == Card.GO_TO_THIRD_SPECIAL){
            return true;
	}else if (value == Card.GO_TO_FOURTH_SPECIAL){
            return true;
	}else if (value == Card.GO_TO_FIFTH_SPECIAL){
            return true;
        } else {
            return false;
        }
    }
}
