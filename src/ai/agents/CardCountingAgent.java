package ai.agents;

import ai.agents.main.GameLog;
import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;
import garrettsmith.blackjack.Result;
import garrettsmith.playingcards.Card;
import garrettsmith.playingcards.Card.Value;
import garrettsmith.playingcards.CardList;

public class CardCountingAgent extends BaseAgent {

	int count = 0;
	int startValue = 0;
	Hand startHand = null;
	
	public CardCountingAgent() {
		super("CardCountingAgent");
	}
	
	@Override
	public Move playTurn(Hand hand) {
		if(startHand == null) {
			firstTurn(hand);
		} else {
			Card lastCard = hand.getCards().get(hand.getCards().size()-1);
			countCard(lastCard);
		}
		
		if(count <= 0) {
			return Move.STAND;
		} else {
			return Move.HIT;
		}
	}
	
	@Override
	public void gameEnd(Hand hand, double gain, Result result,
			CardList dealerCards) {
		
		
		super.gameEnd(hand, gain, result, dealerCards);
		
		Card lastCard = hand.getCards().get(hand.getCards().size()-1);
		countCard(lastCard);
		
		for(int i=0; i < dealerCards.size(); i++) {
			if(i != 1) {
				Card card = dealerCards.get(i);
				countCard(card);
			}
		}

		this.startHand = null;
		
		GameLog.println("Sum: "+this.count);
	}
	
	private void firstTurn(Hand hand) {
		this.startHand = hand;
		
		countCard(hand.getDealerValue());
		CardList cards = hand.getCards();
		for(int i=0; i < cards.size(); i++) {
			Card card = cards.get(i);
			countCard(card);
		}
		//this.startValue = hand.getValue();

	}
	
	private void countCard(Card card) {
		int value = cardValue(card);
		countCard(value);
	}
	
	private void countCard(int value) {
		if(value < 7) {
			count++;
		} else if(value > 9) { 
			count--;
		}
	}
	
	
	protected static int cardValue(Card card) {
		if (Card.Value.ACE.equals(card.getValue())) {
			return 10;
		} else if (Card.Value.KING.equals(card.getValue())) {
			return 10;
		} else if (Card.Value.QUEEN.equals(card.getValue())) {
			return 10;
		} else if (Card.Value.JACK.equals(card.getValue())) {
			return 10;
		} else if (Card.Value.TEN.equals(card.getValue())) {
			return 10;
		} else if (Card.Value.NINE.equals(card.getValue())) {
			return 9;
		} else if (Card.Value.EIGHT.equals(card.getValue())) {
			return 8;
		} else if (Card.Value.SEVEN.equals(card.getValue())) {
			return 7;
		} else if (Card.Value.SIX.equals(card.getValue())) {
			return 6;
		} else if (Card.Value.FIVE.equals(card.getValue())) {
			return 5;
		} else if (Card.Value.FOUR.equals(card.getValue())) {
			return 4;
		} else if (Card.Value.THREE.equals(card.getValue())) {
			return 3;
		} else if (Card.Value.TWO.equals(card.getValue())) {
			return 2;
		}
		throw new IllegalArgumentException("unknown card type: "
				+ card.toString());
	}
	

}
