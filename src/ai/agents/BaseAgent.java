package ai.agents;

import java.util.List;

import garrettsmith.blackjack.Blackjack;
import garrettsmith.blackjack.EventHandler;
import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;
import garrettsmith.blackjack.Result;
import garrettsmith.playingcards.Card;
import garrettsmith.playingcards.CardList;

public class BaseAgent implements EventHandler {

	@Override
	public void fatalErrorOccurred(Exception e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handFinished(Hand hand, double gain, Result result,
			CardList dealerCards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean offerEarlySurrender(Hand hand) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Move offerRegularTurn(Hand hand) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	private static String formatCard(Card card) {

		if (Card.Value.ACE.equals(card.getValue())) {

			return "an ace";
		} else if (Card.Value.KING.equals(card.getValue())) {

			return "a king";
		} else if (Card.Value.QUEEN.equals(card.getValue())) {

			return "a queen";
		} else if (Card.Value.JACK.equals(card.getValue())) {

			return "a jack";
		} else if (Card.Value.TEN.equals(card.getValue())) {

			return "a ten";
		} else if (Card.Value.NINE.equals(card.getValue())) {

			return "a nine";
		} else if (Card.Value.EIGHT.equals(card.getValue())) {

			return "an eight";
		} else if (Card.Value.SEVEN.equals(card.getValue())) {

			return "a seven";
		} else if (Card.Value.SIX.equals(card.getValue())) {

			return "a six";
		} else if (Card.Value.FIVE.equals(card.getValue())) {

			return "a five";
		} else if (Card.Value.FOUR.equals(card.getValue())) {

			return "a four";
		} else if (Card.Value.THREE.equals(card.getValue())) {

			return "a three";
		} else if (Card.Value.TWO.equals(card.getValue())) {

			return "a two";
		}
		throw new IllegalArgumentException("unknown card type: "
				+ card.toString());
	}
	
	protected static void printCards(CardList cards) {

		Card card;
		for (int i = 0; i < cards.size(); i++) {

			card = (Card) cards.get(i);

			if (i < cards.size() - 2) {

				System.out.print(formatCard(card) + ", ");
			} else if (i == cards.size() - 2) {

				System.out.print(formatCard(card) + " ");
			} else {

				System.out.print("and " + formatCard(card));
			}
		}
		
		System.out.println(" (" + Blackjack.calculateBestValue(cards)
				+ ").");
	}

}
