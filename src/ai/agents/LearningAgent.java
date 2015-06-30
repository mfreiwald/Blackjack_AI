package ai.agents;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;
import garrettsmith.blackjack.Result;
import garrettsmith.playingcards.Card;
import garrettsmith.playingcards.CardList;

import java.util.Map;
import java.util.TreeMap;

public class LearningAgent extends BaseAgent {

	private int tmpWins = 0;
	private int tmpLose = 0;
	private int tmpPush = 0;

	private TreeMap<CardList, Integer> winningHands = new TreeMap<>();
	private TreeMap<CardList, Integer> losingHands = new TreeMap<>();

	public LearningAgent() {
		super("LearningAgent");
	}

	@Override
	public Move playTurn(Hand hand) {

		Card first = hand.getCards().get(0);
		Card second = hand.getCards().get(1);

		Map.Entry<CardList, Integer> foundWinnerHand = null;
		Map.Entry<CardList, Integer> foundLoserHand = null;
		// Winner Hands
		for (Map.Entry<CardList, Integer> winnerHand : winningHands.entrySet()) {
			if (winnerHand.getKey().contains(first) && winnerHand.getKey().contains(second)) {
				foundWinnerHand = winnerHand;
				break;
			}
		}

		// Loser Hands
		for (Map.Entry<CardList, Integer> loserHand : losingHands.entrySet()) {
			if (loserHand.getKey().contains(first) && loserHand.getKey().contains(second)) {
				foundLoserHand = loserHand;
				break;
			}
		}

		if (foundWinnerHand != null && foundLoserHand != null) {
			//System.out.println("Hand in Win and Lose. Win:"+foundWinnerHand.getValue() +", Lose:"+ foundLoserHand.getValue());

			if (foundWinnerHand.getValue() > foundLoserHand.getValue()) {
				// winnerHand öfter
				if (foundWinnerHand.getKey().size() > 2) {
					return Move.HIT;
				} else {
					return playBasicStrategy(hand); //Move.STAND;
				}
			} else {
				// loserhand öfter
				return playBasicStrategy(hand); //Move.STAND;
			}
		} else if (foundWinnerHand != null) {
			if (foundWinnerHand.getKey().size() > 2) {
				return Move.HIT;
			} else {
				return playBasicStrategy(hand); //Move.STAND;
			}
		} else {
			return playBasicStrategy(hand); //Move.STAND;
		}
	}

	private Move playBasicStrategy(Hand hand) {
		final BaseAgent basicStrategy = new BasicStrategyAgent();
		return basicStrategy.playTurn(hand);
	}

	@Override
	public void gameEnd(Hand hand, double gain, Result result,
			CardList dealerCards) {

		// gewonnen oder verloren?
		CardList winnerHand = null;
		CardList loserHand = null;

		// Pushed
		if (tmpPush < this.getPush()) {
			winnerHand = null;
			loserHand = null;
			// Verloren
		} else if (tmpLose < this.getLose()) {

			winnerHand = dealerCards;
			loserHand = hand.getCards();

			// Gewonnen
		} else if (tmpWins < this.getWins()) {
			winnerHand = hand.getCards();
			loserHand = dealerCards;

		}

		// hand vom gewinner speichern
		if (winnerHand != null) {
			if (winningHands.containsKey(winnerHand)) {
				winningHands.put(winnerHand, winningHands.get(winnerHand) + 1);
			} else {
				winningHands.put(winnerHand, 1);
			}
			// System.out.println("WinnerHand:");
			String output = "[";
			for (Card card : winnerHand) {
				output += BaseAgent.formatCard(card) + ", ";
			}
			output = output.trim() + "]";
			// System.out.println(output);
		}
		if (loserHand != null) {
			if (losingHands.containsKey(loserHand)) {
				losingHands.put(loserHand, losingHands.get(loserHand) + 1);
			} else {
				losingHands.put(loserHand, 1);
			}
		}
		tmpWins = this.getWins();
		tmpLose = this.getLose();
		tmpPush = this.getPush();

	}
}
