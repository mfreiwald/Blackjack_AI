package ai.agents;

import ai.agents.main.GameLog;
import ai.agents.main.GameNotifications;
import ai.agents.main.NotificationObserver;
import garrettsmith.blackjack.Blackjack;
import garrettsmith.blackjack.EventHandler;
import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;
import garrettsmith.blackjack.Result;
import garrettsmith.playingcards.Card;
import garrettsmith.playingcards.CardList;

public abstract class BaseAgent extends NotificationObserver implements EventHandler {

	public final String name;
	private Blackjack game;
	
	protected double wager = 1.0;
	private double _purse = 0.0;
	private boolean _hasDealerCardBeenPrinted = false;
	
	private int Result_Push = 0;
	private int Result_Win = 0;
	private int Result_Lose = 0;
	private int Result_Late_Surrender = 0;
	private int Result_Dealer_Blackjack = 0;
	private int Result_Busted = 0;
	private int Result_Dealer_Busted = 0;
	private int Result_Blackjack = 0;
	private int Result_Blackjack_Push = 0;
	
	
	public BaseAgent(String name) {
		this.name = name;
		GameNotifications.register(this);
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
			this.Result_Push++;
			
		} else if (Result.WIN.equals(result)) {
			GameLog.println("won: " + Double.toString(gain));
			this.Result_Win++;
			
		} else if (Result.LOSE.equals(result)) {
			GameLog.println("lost: " + Double.toString(gain));
			this.Result_Lose++;
			
		} else if (Result.LATE_SURRENDER.equals(result)) {
			GameLog.println("surrendered: " + Double.toString(gain));
			this.Result_Late_Surrender++;
			
		} else if (Result.DEALER_BLACKJACK.equals(result)) {
			GameLog.println("lost: " + Double.toString(gain));
			GameLog.println(" Dealer had a blackjack");
			this.Result_Dealer_Blackjack++;
			
		} else if (Result.BUSTED.equals(result)) {
			GameLog.println("BUSTED: " + Double.toString(gain));
			this.Result_Busted++;
			
		} else if (Result.DEALER_BUSTED.equals(result)) {
			GameLog.println("won");
			GameLog.println(" Dealer BUSTED: "
					+ Double.toString(gain));
			this.Result_Dealer_Busted++;
			
		} else if (Result.BLACKJACK.equals(result)) {
			GameLog.println("got BLACKJACK!: " + Double.toString(gain));
			this.Result_Blackjack++;
			
		} else if (Result.BLACKJACK_PUSH.equals(result)) {
			GameLog.println("pushed, BOTH had blackjack: "
					+ Double.toString(gain));
			this.Result_Blackjack_Push++;
			
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
		GameLog.println("offerEarlySurrender not implemented..");
		return false;
	}

	@Override
	public Move offerRegularTurn(Hand hand) {
		printDealerCardIfNeeded(hand);
		printPlayerCards(hand);
		
		return playTurn(hand);
	}
	
	
	public static String formatCard(Card card) {

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
	
	
	public void printStats() {
		System.out.println("Win: "+this.Result_Win);
		System.out.println("Lose: "+this.Result_Lose);
		System.out.println("Push: "+this.Result_Push);
		System.out.println("Late Surrender: "+this.Result_Late_Surrender);
		System.out.println("Dealer Blackjack: "+this.Result_Dealer_Blackjack);
		System.out.println("Busted: "+this.Result_Busted);
		System.out.println("Dealer Busted: "+this.Result_Dealer_Busted);
		System.out.println("Blackjack: "+this.Result_Blackjack);
		System.out.println("Blackjack Push: "+this.Result_Blackjack_Push);
		System.out.println();
		System.out.println("Sum Win: " + this.getWins());
		System.out.println("Sum Lose: "+this.getLose());
		System.out.println("Sum Push: "+this.getPush());
	}
	
	public double getPurse() {
		return this._purse;
	}
	
	public int getWins() {
		return this.Result_Win + this.Result_Blackjack + this.Result_Dealer_Busted + this.Result_Late_Surrender;
	}
	
	public int getLose() {
		return this.Result_Busted + this.Result_Lose + this.Result_Dealer_Blackjack;
	}

	public int getPush() {
		return this.Result_Blackjack_Push + this.Result_Push;
	}
	
	public void setGame(Blackjack game) {
		this.game = game;
	}
	
	protected Blackjack getGame() {
		return this.game;
	}
	
	public double getWager() {
		return this.wager;
	}
}
