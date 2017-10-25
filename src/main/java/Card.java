public class Card{
    private int value;  // Can be either 1 or 2
    private String color; // Can be Red, Blue

    public Card(int v, String c){
        value = validateValue(v);
        color = validateColor(c);
    }

    // Accessors
    public int getValue(){
        return value;
    }

    public String getColor(){
        return color;
    }

    // Since 1 and 2 are the only valid input for the value field, an
    // error is thrown when neither of those 2 is entered
    private int validateValue(int someValue) throws IllegalArgumentException {
        if (someValue != 1 && someValue != 2){
            throw new IllegalArgumentException("Value must be 1 or 2!");
        } else {
            return someValue;
        }
    }

    // The string provided for color must be one of the valid colors for the game
    // so an IllegalArgumentException is thrown if the provided string is not
    // considered a valid color
    private String validateColor(String someColor) throws IllegalArgumentException {
        String[] validColors = {"RED", "YELLOW", "BLUE", "GREEN", "ORANGE"};
        someColor = someColor.toUpperCase();

        for(String valid : validColors){
            if (valid.equals(someColor)) {
                return someColor;
            }
        }
        // If we reach here, the string cannot be a vaild color
        throw new IllegalArgumentException("Color must be a valid color!");
    }
}
