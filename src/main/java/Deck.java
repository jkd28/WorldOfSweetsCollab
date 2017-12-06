import java.util.Stack;
import java.util.Collections;
import java.awt.Color;
import java.io.Serializable;
import java.util.ListIterator;

public class Deck implements Serializable {
    private static final long serialVersionUID = 1L;
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

    // forcing a move to the next space of that color). Create an array of size 16
    // (skip = 0) where each index contains the color whose index difference with
    // the current space corresponds with the index (I'm tired but this makes sense
    // in my head). Choose the available card with the lowest index. The special
    // cards are edge cases that can be figured out by comparing the current location
    // index with the indices of the special squares
    public Card dadDraw(Player player, BoardPanel boardPanel){
	if (cardDeck.empty()){
	    cardDeck = initializeDeck();
	}
	BoardSpace space = player.getPosition();
	int position = boardPanel.getSpaceIndex(space);
	Color[] nearSpaces = getCloseSpaces(position, boardPanel);
	Card[] remaining = getRemainingCards();
	Card worstCard = getWorstCard(position, remaining, nearSpaces);
	return getSpecificCard(worstCard);
    }

    // Given a player's position and the board, returns the color of next 15 spaces.
    // This is the furthest a player can move ignoring special cards - occurs if
    // the player draws a double card of the color they are on and one of the next
    // two spaces of that color is a special instead.
    public Color[] getCloseSpaces(int position, BoardPanel boardPanel){
	ListIterator<BoardSpace> iter = boardPanel.getListIterator(position);
	BoardSpace tempSpace;
	Color[] nearSpaces = new Color[15];
	for(int i = 0; i < nearSpaces.length; i++){
	    tempSpace = iter.next();
	    nearSpaces[i] = tempSpace.getSpaceColor();
	    if(tempSpace.isGrandmasHouse()){
		break;
	    }
	}
	/*	System.out.println("-----");
	for(int i = 0; i < nearSpaces.length; i++){
	    System.out.println(nearSpaces[i]);
	    }*/
	return nearSpaces;
    }

    // Creates an array of the remaining cards by cloning the current
    // deck stack into a temporary stack and popping each card into an
    // array.
    public Card[] getRemainingCards(){
	Stack<Card> tempDeck = (Stack<Card>)cardDeck.clone();
	Card[] remaining = new Card[tempDeck.size()];

	for(int i = 0; i < remaining.length; i++){
	    if(tempDeck.empty()){
		break;
	    }
	    remaining[i] = tempDeck.pop();
	}
	return remaining;
    }

    // Retrieves a specific card from the deck while maintaining relative order
    public Card getSpecificCard(Card card){
	Card[] cards = new Card[cardDeck.size()];
	Card currentCard = null;
	Card desiredCard = null;
	int lastIndex = 0;

	// Keep popping cards from the deck until you find the card
	// you want. Store every other card in an array. Once you find
	// the desired card, save it and stop iterating. Then iterate
	// through the array of cards in reverse order and push each
	// back onto the stack to preserve order.
	for(int i = 0; i < cards.length; i++ ){
	    if(cardDeck.empty()){
		break;
	    }
	    currentCard = cardDeck.pop();
	    if(card.getColor().equals(currentCard.getColor()) &&
	       card.getValue() == currentCard.getValue()){
		desiredCard = currentCard;
		if(lastIndex != 0){
		lastIndex = i-1;
		}
		break;
	    } else {
		cards[i] = currentCard;
	    }
	}

	if(lastIndex != 0){
	    for(int i = lastIndex; i >= 0; i--){
		cardDeck.push(cards[i]);
	    }
	}
	return desiredCard;
    }

    // Given the player's position, an array of the remaining cards, and an array
    // of the 15 closest spaces (forward of the position), determine the worst card.
    public Card getWorstCard(int position, Card[] remaining, Color[] nearSpaces){
	int smallestIndex = Integer.MAX_VALUE;
	Card worstCard = null;

	// Iterate over the remaining cards and compare the color of the each card
	// to the color of the closest forward spaces. If the color matches and the
	// index of the current space is smaller than the current smallest spaces,
	// set the card as the worstCard and the index as the smallest index.
	for(int i = 0; i < remaining.length; i++){
	    for(int j = 0; j < nearSpaces.length; j++){
		int currentValue = remaining[i].getValue();
		Color currentColor = remaining[i].getColor();
		if(!nearSpaces[j].equals(Color.WHITE)){
		    // Skip cards will almost always be the worst card
		    if(currentValue== 0){ 
			worstCard = remaining[i];
			smallestIndex = 0;

			// Check for single cards
		    } else if(currentValue == 1 && currentColor.equals(nearSpaces[j]) && j < smallestIndex){
			worstCard = remaining[i];
			smallestIndex = j;

			// If a special space is within the nearest 15, the
			// corresponding card may be the worst
		    } else if(currentValue > 2 && currentColor.equals(nearSpaces[j]) && j < smallestIndex){
			worstCard = remaining[i];
			smallestIndex = j;

			// Check for double cards; index multiplied by 2 for weight
		    } else if(currentValue == 2 && currentColor.equals(nearSpaces[j]) && j*2 < smallestIndex){
			worstCard = remaining[i];
			smallestIndex = j*2;
		    }

		    // Check for special card edge cases: If the current position
		    // is farther than a special space, the corresponding card
		    // will move the player backwards and will be one of the worst
		    // cards. The statements are not if-else because 
		    if(currentValue > 2){
			if(position >= 60 && currentValue == 7 && smallestIndex >-1){
			    worstCard = remaining[i];
			    smallestIndex = -1;
			}
			if(position >= 48 && currentValue == 6 && smallestIndex >-2){
			    worstCard = remaining[i];
			    smallestIndex = -2;
			}
			if(position >= 36 && currentValue == 5 && smallestIndex >-3){
			    worstCard = remaining[i];
			    smallestIndex = -3;
			}
			if(position >= 24 && currentValue == 4 && smallestIndex >-4){
			    worstCard = remaining[i];
			    smallestIndex = -4;
			}
			if(position >= 12 && currentValue == 3 && smallestIndex >-5){
			    worstCard = remaining[i];
			    smallestIndex = -5;
			}
		    }
		}
	    }
	}
	System.out.println("Color: " +  worstCard.getColor());
	System.out.println("Value: " +  worstCard.getValue());
	return worstCard;
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
            newCard = new Card(Card.SKIP, Color.DARK_GRAY); // doesn't really matter what Color we push to it
            deckStack.push(newCard);
        }

        // Add Go-To-Speical Cards
        for (int i = 0; i < NUM_GO_TO_CARDS; i++){
            newCard = new Card(Card.GO_TO_FIRST_SPECIAL, Color.MAGENTA);
	    deckStack.push(newCard);
	    newCard = new Card(Card.GO_TO_SECOND_SPECIAL, Color.CYAN);
	    deckStack.push(newCard);
	    newCard = new Card(Card.GO_TO_THIRD_SPECIAL, Color.PINK);
	    deckStack.push(newCard);
	    newCard = new Card(Card.GO_TO_FOURTH_SPECIAL, Color.GRAY);
	    deckStack.push(newCard);
	    newCard = new Card(Card.GO_TO_FIFTH_SPECIAL, Color.BLACK);
            deckStack.push(newCard);
        }
        
        // To emulate a true deck of cards, shuffle
        Collections.shuffle(deckStack);
        return deckStack;
    }
}
