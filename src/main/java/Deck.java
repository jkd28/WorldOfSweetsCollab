import java.util.Stack;
import java.util.Collections;
import java.awt.Color;
import java.io.Serializable;

public class Deck implements Serializable {
    private static final long serialVersionUID = 1L;
    private Stack<Card> cardDeck;

    public static final int NUM_SINGLE_CARDS_PER_COLOR = 10;
    public static final int NUM_DOUBLE_CARDS_PER_COLOR = 2;
    public static final int NUM_SKIP_CARDS = 5;
    public static final int NUM_GO_TO_CARDS = 1; // Number of copies of each go to card
    /*public int drawnRed, drawnYellow, drawnBlue, drawnGreen, drawnOrange, drawnSkip,
    drawnRed2, drawnYellow2, drawnBlue2, drawnGreen2, drawnOrange2;
    drawnRed = drawnYellow = drawnBlue = drawnGreen = drawnOrange = drawnSkip = drawnRed2 = drawnYellow2 = drawnBlue2 = drawnGreen2 = drawnOrange2 = 0; */
    
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

    // The fartherest a player can be moved by a single card (ignoring specials)
    // is 10 spaces (drawing a double of the color the player is currently on).
    // Create an array of size 11 (skip = 0) where each index contains the color
    // whose index difference with the current space corresponds with the index (I'm
    // tired but this makes sense in my head). Choose the available card with the
    // lowest index. The special cards are edge cases that can be figured out by
    // comparing the current location index with the indices of the special squares
    public Card dadDraw(){
	if (cardDeck.empty()){
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
