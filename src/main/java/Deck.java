import java.util.Stack;
import java.util.Collections;
import java.awt.Color;
public class Deck {
    private Stack<Card> cardDeck;

    public Deck() {
        cardDeck = initializeDeck();
    }

    public Card draw(){
        if (cardDeck.empty()) {
            // reinitialize the deck, equivalent to reshuffling all cards
            cardDeck = initializeDeck();
        }
        return cardDeck.pop();
    }

    public int size(){
        return cardDeck.size();
    }

    private Stack<Card> initializeDeck() {
        Stack<Card> deckStack = new Stack<Card>();
        // Avoid magic numbers
        int numCardsPerColor = 12;
        Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.ORANGE};
        Card newCard;

        // Create cards for each color, with a ratio of 10 singles to 2 doubles
        for(int i = 0; i < colors.length; i++) {
            for(int j = 0; j < numCardsPerColor; j++) {
                if (j < 10) {
                    newCard = new Card(Card.SINGLE, colors[i]);
                } else {
                    newCard = new Card(Card.DOUBLE, colors[i]);
                }
                deckStack.push(newCard);
            }
        }

        // Add Skip-Turn cards
        int numberOfSkipCards = 5;
        for (int i = 0; i < numberOfSkipCards; i++){
            deckStack.push(new Card(Card.SKIP, Color.RED)); // doesn't really matter what Color we push to it
        }

        // Add Go-To-Middle Cards
        int numberOfMiddleCards = 3;
        for (int i = 0; i < numberOfMiddleCards; i++){
            deckStack.push(new Card(Card.GO_TO_MIDDLE, Color.RED)); // doesn't really matter what Color we push to it
        }
        
        // To emulate a true deck of cards, shuffle
        Collections.shuffle(deckStack);
        return deckStack;
    }
}
