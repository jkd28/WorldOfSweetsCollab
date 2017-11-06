import java.util.Stack;
import java.util.Collections;
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
                deckStack.push(newCard);
            }
        }

        // Add Skip-Turn cards
        int numberOfSkipCards = 5;
        for (int i = 0; i < numberOfSkipCards; i++){
            deckStack.push(new Card(0, "SKIP"));
        }

        // Add Go-To-Middle Cards
        int numberOfMiddleCards = 3;
        for (int i = 0; i < numberOfMiddleCards; i++){
            deckStack.push(new Card(3, "MIDDLE"));
        }
        
        // To emulate a true deck of cards, shuffle
        Collections.shuffle(deckStack);
        return deckStack;
    }
}
