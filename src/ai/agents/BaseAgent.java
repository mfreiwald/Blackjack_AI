package ai.agents;

import ai.agents.main.GameLog;
import garrettsmith.blackjack.Blackjack;
import garrettsmith.blackjack.EventHandler;
import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;
import garrettsmith.blackjack.Result;
import garrettsmith.playingcards.Card;
import garrettsmith.playingcards.CardList;

public abstract class BaseAgent implements EventHandler {

	public final String name;
	
	private double _purse = 0.0;
	private boolean _hasDealerCardBeenPrinted = false;
	
	public BaseAgent(String name) {
		this.name = name;
	}
	
	public void newGame() {
		this._hasDealerCardBeenPrinted = false;
	}
	
	abstract public Move playTurn(Hand hand);
	
	public void gameEnd(Hand hand, double gain, Result result,
			CardList dealerCards) {
		
	}
	
	@Override
	public void fatalErrorOccurred(Exception e) {
		GameLog.err("a fatal error occurred: " + e.getMessage());
		e.printStackTrace();
		System.exit(1);		
	}

	@Override
	public void handFinished(Hand hand, double gain, Result result,
			CardList dealerCards) {

		GameLog.println();
		GameLog.println("Game Over");
		GameLog.print(" " + this.name + " ");
		
		if (Result.PUSH.equals(result)) {
			GameLog.println("pushed: 0.0");
		} else if (Result.WIN.equals(result)) {
			GameLog.println("won: " + Double.toString(gain));
		} else if (Result.LOSE.equals(result)) {
			GameLog.println("lost: " + Double.toString(gain));
		} else if (Result.LATE_SURRENDER.equals(result)) {
			GameLog.println("surrendered: " + Double.toString(gain));
		} else if (Result.DEALER_BLACKJACK.equals(result)) {
			GameLog.println("lost: " + Double.toString(gain));
			GameLog.println(" Dealer had a blackjack");
		} else if (Result.BUSTED.equals(result)) {
			GameLog.println("BUSTED: " + Double.toString(gain));
		} else if (Result.DEALER_BUSTED.equals(result)) {
			GameLog.println("won");
			GameLog.println(" Dealer BUSTED: "
					+ Double.toString(gain));
		} else if (Result.BLACKJACK.equals(result)) {
			GameLog.println("got BLACKJACK!: " + Double.toString(gain));
		} else if (Result.BLACKJACK_PUSH.equals(result)) {
			GameLog.println("pushed, BOTH had blackjack: "
					+ Double.toString(gain));
		} else {
			throw new IllegalArgumentException("unknown value of parameter "
					+ "result: " + result.value());
		}
		
		GameLog.print("Dealer: ");
		printCards(dealerCards);
		GameLog.println(" ("
				+ Integer.toString(Blackjack.calculateBestValue(dealerCards))
				+ ")");
		
		GameLog.print(this.name + ": ");
		printCards(hand.getCards());
		GameLog.println(" (" + Blackjack.calculateBestValue(hand.getCards())
				+ ")");
		_purse += gain;
		
		GameLog.println();
		GameLog.println(this.name + " have " + Double.toString(_purse)
				+ " in the purse.");
		
		this.gameEnd(hand, gain, result, dealerCards);
	}

	@Override
	public boolean offerEarlySurrender(Hand hand) {
		// TODO Auto-generated method stub
		GameLog.println("offerEarlySurrender not implemented..");
		return false;
	}

	@Override
	public Move offerRegularTurn(Hand hand) {
		printDealerCardIfNeeded(hand);
		printPlayerCards(hand);
		
		return playTurn(hand);
	}
	
	
	private static String formatCard(Card card) {

		if (Card.Value.ACE.equals(card.getValue())) {
			return "A";
			//return "an ace";
		} else if (Card.Value.KING.equals(card.getValue())) {
			return "K";
			//return "a king";
		} else if (Card.Value.QUEEN.equals(card.getValue())) {
			return "Q";
			//return "a queen";
		} else if (Card.Value.JACK.equals(card.getValue())) {
			return "J";
			//return "a jack";
		} else if (Card.Value.TEN.equals(card.getValue())) {
			return "10";
			//return "a ten";
		} else if (Card.Value.NINE.equals(card.getValue())) {
			return "9";
			//return "a nine";
		} else if (Card.Value.EIGHT.equals(card.getValue())) {
			return "8";
			//return "an eight";
		} else if (Card.Value.SEVEN.equals(card.getValue())) {
			return "7";
			//return "a seven";
		} else if (Card.Value.SIX.equals(card.getValue())) {
			return "6";
			//return "a six";
		} else if (Card.Value.FIVE.equals(card.getValue())) {
			return "5";
			//return "a five";
		} else if (Card.Value.FOUR.equals(card.getValue())) {
			return "4";
			//return "a four";
		} else if (Card.Value.THREE.equals(card.getValue())) {
			return "3";
			//return "a three";
		} else if (Card.Value.TWO.equals(card.getValue())) {
			return "2";
			//return "a two";
		}
		throw new IllegalArgumentException("unknown card type: "
				+ card.toString());
	}
	
	
	protected static void printCards(CardList cards) {
		GameLog.print("[");
		Card card;
		for (int i = 0; i < cards.size(); i++) {

			card = (Card) cards.get(i);

			if (i == cards.size() - 1) {
				GameLog.print(formatCard(card));
			} else {
				GameLog.print(formatCard(card) + ", ");
			}
		}
		
		GameLog.print("] ");
		
		
		/*
		Card card;
		for (int i = 0; i < cards.size(); i++) {

			card = (Card) cards.get(i);

			if (i < cards.size() - 2) {

				GameLog.print(formatCard(card) + ", ");
			} else if (i == cards.size() - 2) {

				GameLog.print(formatCard(card) + " ");
			} else {

				GameLog.print("and " + formatCard(card));
			}
		}
		
		GameLog.println(" (" + Blackjack.calculateBestValue(cards)
				+ ").")
		*/
	}
	
	protected static void printPoints(Hand hand) {
		GameLog.println(" (" + (hand.isSoft() ? "soft " : "")
				+ Integer.toString(hand.getBestValue()) + ")");
	}
	
	private void printDealerCard(Hand hand) {

		
		GameLog.print("Dealer: ["
				+ formatCard(hand.getDealerCard()) + "] ");
		
		GameLog.print("(");
		GameLog.print(hand.getDealerValue());
		GameLog.println(")");
	}
	
	protected void printDealerCardIfNeeded(Hand hand) {

		if (!_hasDealerCardBeenPrinted) {

			printDealerCard(hand);
			_hasDealerCardBeenPrinted = true;
		}
	}
	
	public void printPlayerCards(Hand hand) {
		GameLog.print(this.name + ": ");
		
		BaseAgent.printCards(hand.getCards());
		BaseAgent.printPoints(hand);
		
	}
	
	public double getPurse() {
		return this._purse;
	}

}
