import java.util.Stack;
import java.util.Collections;
public class Deck {
    private Stack<Card> cardDeck;

    public Deck() {
        initializeDeck();
    }

    private void initializeDeck() {
        // Avoid magic numbers
        int numCardsPerColor = 12;
        String[] colors = {"RED", "YELLOW", "BLUE", "GREEN", "ORANGE"};
        Card newCard;

        // Create cards for each color, with a ratio of 10 singles to 2 doubles
        for(int i = 0; i < colors.length; i++) {
            for(int j = 0; j < numCardsPerColor; j++) {
                if (j < 10) {
                    newCard = new Card(1, colors[i]);
                } else {
                    newCard = new Card(2, colors[i]);
                }
                cardDeck.push(newCard);
            }
        }

        // To emulate a true deck of cards, shuffle
        Collections.shuffle(cardDeck);
        return;
    }
}
