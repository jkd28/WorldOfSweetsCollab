import java.util.Stack;
import java.util.Collections;
import java.awt.Color;
public class Deck {
    private Stack<Card> cardDeck;

    public static final int NUM_SINGLE_CARDS_PER_COLOR = 10;
    public static final int NUM_DOUBLE_CARDS_PER_COLOR = 2;
    public static final int NUM_SKIP_CARDS = 5;
    public static final int NUM_GO_TO_CARDS = 1; // Number of copies of each go to card

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
        Color[] cardColors = Card.VALID_COLORS;
        Card newCard;

        // Create cards for each color
        for(Color cardColor : cardColors){
            for(int i = 0; i < NUM_SINGLE_CARDS_PER_COLOR; i++){
                newCard = new Card(Card.SINGLE, cardColor);
                deckStack.push(newCard);
            }

            for(int i = 0; i < NUM_DOUBLE_CARDS_PER_COLOR; i++){
                newCard = new Card(Card.DOUBLE, cardColor);
                deckStack.push(newCard);
            }
        }

        // Add Skip-Turn cards
        for (int i = 0; i < NUM_SKIP_CARDS; i++){
            newCard = new Card(Card.SKIP, Color.RED); // doesn't really matter what Color we push to it
            deckStack.push(newCard);
        }

        // Add Go-To-Speical Cards
        for (int i = 0; i < NUM_GO_TO_CARDS; i++){
            newCard = new Card(Card.GO_TO_FIRST_SPECIAL, Color.RED); // doesn't really matter what Color we push to it
	    deckStack.push(newCard);
	    newCard = new Card(Card.GO_TO_SECOND_SPECIAL, Color.RED);
	    deckStack.push(newCard);
	    newCard = new Card(Card.GO_TO_THIRD_SPECIAL, Color.RED);
	    deckStack.push(newCard);
	    newCard = new Card(Card.GO_TO_FOURTH_SPECIAL, Color.RED);
	    deckStack.push(newCard);
	    newCard = new Card(Card.GO_TO_FIFTH_SPECIAL, Color.RED);
            deckStack.push(newCard);
        }
        
        // To emulate a true deck of cards, shuffle
        Collections.shuffle(deckStack);
        return deckStack;
    }
}
