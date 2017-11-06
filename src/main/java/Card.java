public class Card{
    private int value;  // Can be 0 (skip), 1 (single), 2 (double)
    private String color; // Can be Red, Blue

    public Card(int v, String c){
        value = v;
        color = c.toUpperCase();
        if (!validateValueColorPair()) {
            throw new IllegalArgumentException("Invalid value/color pairing");
        }
    }

    // Accessors
    public int getValue(){
        return value;
    }

    public String getColor(){
        return color;
    }

    // Ensure that the supplied value/color pairing is valid for the game rules
    private boolean validateValueColorPair(){
        String[] validColors = {"RED", "YELLOW", "BLUE", "GREEN", "ORANGE"};

        if (value == 1 || value == 2){
            for(String valid : validColors){
                if (color.equals(valid)) {
                    return true;
                }
            }
            // if no valid color matches the supplied color, it is invalid
            return false;

        } else if (value == 0) {
            // for value 0, the color must be "SKIP"
            if (color.equals("SKIP")) {
                return true;
            } else {
                return false;
            }
        }else if (value == 3){
            // for value 3, the color must be "MIDDLE"
            if (color.equals("MIDDLE")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
