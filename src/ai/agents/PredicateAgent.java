package ai.agents;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;
import garrettsmith.playingcards.Card;
import garrettsmith.playingcards.Card.Value;

import java.util.function.Predicate;

public class PredicateAgent extends BaseAgent {
	
	private boolean first = true;
	
	public int[] cardsInDeck = new int[13];
	
	private void initCardsInDeck() {
		int decks = this.getGame().getRules().getNumberOfDecks();
		for(int i = 0; i < cardsInDeck.length; i++) {
			cardsInDeck[i] = decks * 4;
		} 
	}
	
	private void decreaseCard(Card card)
	{
		if (Card.Value.ACE.equals(card.getValue())) {
			cardsInDeck[0]--;
		} else if (Card.Value.KING.equals(card.getValue())) {
			cardsInDeck[12]--;
		} else if (Card.Value.QUEEN.equals(card.getValue())) {
			cardsInDeck[11]--;
		} else if (Card.Value.JACK.equals(card.getValue())) {
			cardsInDeck[10]--;
		} else if (Card.Value.TEN.equals(card.getValue())) {
			cardsInDeck[9]--;
		} else if (Card.Value.NINE.equals(card.getValue())) {
			cardsInDeck[8]--;
		} else if (Card.Value.EIGHT.equals(card.getValue())) {
			cardsInDeck[7]--;
		} else if (Card.Value.SEVEN.equals(card.getValue())) {
			cardsInDeck[6]--;
		} else if (Card.Value.SIX.equals(card.getValue())) {
			cardsInDeck[5]--;
		} else if (Card.Value.FIVE.equals(card.getValue())) {
			cardsInDeck[4]--;
		} else if (Card.Value.FOUR.equals(card.getValue())) {
			cardsInDeck[3]--;
		} else if (Card.Value.THREE.equals(card.getValue())) {
			cardsInDeck[2]--;
		} else if (Card.Value.TWO.equals(card.getValue())) {
			cardsInDeck[1]--;
		}
	}
	
	@Override
	public void dealerCreateNewDecks(int cards) {
		initCardsInDeck();
	}
	
	PredicateAgent() {
		super("Predicate Agent");
		initCardsInDeck();
	}

	@Override
	public Move playTurn(Hand hand) {
		if(first)
		{
			for(Card card: hand.getCards())
			{
				decreaseCard(card);
			}
			decreaseCard(hand.getDealerCard());
			first = false;
		}
		else
		{
			decreaseCard(hand.getCards().get(hand.getCards().size()-1));
		}
		return Move.STAND;
	}
	
	/* Predicates for Dealer Card */
	final Predicate<Hand> dealerHighCard = new Predicate<Hand>() {

		@Override
		public boolean test(Hand hand) {
			Value value = hand.getDealerCard().getValue();
			return value == Value.TEN
				|| value == Value.JACK
				|| value == Value.QUEEN
				|| value == Value.KING
				|| value == Value.ACE;
		}
	};
	
	final Predicate<Hand> dealerlowCard = new Predicate<Hand>() {

		@Override
		public boolean test(Hand hand) {
			Value value = hand.getDealerCard().getValue();
			return value == Value.TWO
				|| value == Value.THREE
				|| value == Value.FOUR
				|| value == Value.FIVE
				|| value == Value.SIX;
		}
	};
	
	final Predicate<Hand> dealerMediumCard = dealerHighCard.negate().and(dealerlowCard.negate());

	/* Predicates for our cards */
	final Predicate<Hand> splitPossible = new Predicate<Hand>() {

		@Override
		public boolean test(Hand hand) {
			return hand.isSplitAllowed();
		}
	};
	
	final Predicate<Hand> saveToHit = new Predicate<Hand>() {

		@Override
		public boolean test(Hand hand) {
			return hand.getValue() <= 11;
		}
	};
	
	/* Dealer plays soft seventeen so at the end has more than 16 points or bustes. */
	final Predicate<Hand> isDealerHigher = new Predicate<Hand>() {

		@Override
		public boolean test(Hand hand) {
			return hand.getBestValue() <= 16;
		}
	};
	
	final Predicate<Hand> easlyBusted = new Predicate<Hand>() {

		@Override
		public boolean test(Hand hand) {
			return !hand.isSoft() && hand.getBestValue() > 18;
		}
	};
}
