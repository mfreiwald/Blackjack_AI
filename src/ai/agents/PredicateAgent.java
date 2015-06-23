package ai.agents;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;
import garrettsmith.blackjack.Result;
import garrettsmith.playingcards.Card;
import garrettsmith.playingcards.Card.Value;
import garrettsmith.playingcards.CardList;

import java.util.function.Predicate;

public class PredicateAgent extends BaseAgent {
	
	private boolean first = true;
	private boolean firstGame = true;
	
	public int[] cardsInDeck = new int[10];
	
	private void initCardsInDeck() {
		int decks = this.getGame().getRules().getNumberOfDecks();
		for(int i = 0; i < cardsInDeck.length; i++) {
			cardsInDeck[i] = decks * 4;
		} 
		cardsInDeck[9] *= 4;
	}
	
	private void decreaseCard(Card card)
	{
		if (Card.Value.ACE.equals(card.getValue())) {
			cardsInDeck[0]--;
		} else if (Card.Value.KING.equals(card.getValue())) {
			cardsInDeck[9]--;
		} else if (Card.Value.QUEEN.equals(card.getValue())) {
			cardsInDeck[9]--;
		} else if (Card.Value.JACK.equals(card.getValue())) {
			cardsInDeck[9]--;
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
	
	public PredicateAgent() {
		super("Predicate Agent");
	}

	@Override
	public Move playTurn(Hand hand) {
		if(firstGame)
		{
			initCardsInDeck();
			firstGame = false;
		}
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
//		System.out.println("!eb & hc: " + easlyBusted.negate().and(highChance).test(hand));
//		System.out.println("idh & hc: " + isDealerHigher.and(highChance).test(hand));
//		System.out.println("idh: " + isDealerHigher.test(hand));
//		System.out.println("hc: " + highChance.test(hand));
//		System.out.println("!eb: " + easlyBusted.negate().test(hand));
//		System.out.println("dlc: " + dealerlowCard.test(hand));
//		System.out.println("dmc: " + dealerMediumCard.test(hand));
//		System.out.println("dlc | dmc: " + dealerlowCard.or(dealerMediumCard).test(hand));
		if( splitPossible.and(dealerlowCard.and(easlyBusted)).test(hand) )
		{
			return Move.SPLIT;
		}
		if( surrenderPossible.and(dealerHighCard.and(easlyBusted.and(highChance.negate()))).test(hand) )
		{
			return Move.SURRENDER;
		}
		// (dlc | dmc) & sth & hc
		if( doubleDownPossible.and((dealerlowCard.or(dealerMediumCard)).and(saveToHit.and(highChance))).test(hand) )
		{
			return Move.DOUBLE;
		}
		// sth | (!eb & hc) | (idh & hc)
		if( hitPossible.and(saveToHit.or(easlyBusted.negate().and(mediumChance)).or(isDealerHigher.and(mediumChance))).test(hand) )
		{
			return Move.HIT;
		}
		// Default
		return Move.STAND;
	}
	
	@Override
	public void gameEnd(Hand hand, double gain, Result result, CardList dealerCards) {
		for(int i = 1; i < dealerCards.size(); i++)
		{
			decreaseCard(dealerCards.get(i));
		}
	};
	
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
	
	final Predicate<Hand> doubleDownPossible = new Predicate<Hand>() {

		@Override
		public boolean test(Hand hand) {
			return hand.isDoubleDownAllowed();
		}
	};
	
	final Predicate<Hand> hitPossible = new Predicate<Hand>() {

		@Override
		public boolean test(Hand hand) {
			return hand.isHitAllowed();
		}
	};
	
	final Predicate<Hand> standPossible = new Predicate<Hand>() {

		@Override
		public boolean test(Hand hand) {
			return hand.isStandAllowed();
		}
	};
	
	final Predicate<Hand> saveToHit = new Predicate<Hand>() {

		@Override
		public boolean test(Hand hand) {
			return hand.getValue() <= 11;
		}
	};
	
	final Predicate<Hand> surrenderPossible = new Predicate<Hand>() {

		@Override
		public boolean test(Hand hand) {
			return hand.isSurrenderAllowed();
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
	
	final Predicate<Hand> highChance = new Predicate<Hand>() {
		
		@Override
		public boolean test(Hand hand) {
			int left = 21 - hand.getBestValue();
			int cards = 0;
			double usefulCards = 0;
			for(int i = 0; i < cardsInDeck.length; i++)
			{
				cards += cardsInDeck[i];
				if(i+1 <= left)
					usefulCards += cardsInDeck[i];
			}
			return (usefulCards/cards) > 0.8;
		}
	};
	
final Predicate<Hand> mediumChance = new Predicate<Hand>() {
		
		@Override
		public boolean test(Hand hand) {
			int left = 21 - hand.getBestValue();
			int cards = 0;
			double usefulCards = 0;
			for(int i = 0; i < cardsInDeck.length; i++)
			{
				cards += cardsInDeck[i];
				if(i+1 <= left)
					usefulCards += cardsInDeck[i];
			}
			return (usefulCards/cards) > 0.5;
		}
	};
}
