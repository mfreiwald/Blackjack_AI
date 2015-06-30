package ai.agents;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;
import garrettsmith.playingcards.Card;

/**
 * This is the real BasicStragtegyAgent.<br>
 * He is a combination of all other BasicStrategyAgents.<br>
 * Use this one instead of the others!
 *
 * @author amayer
 */
public class BasicStrategyAgent extends BaseAgent {

	public BasicStrategyAgent() {
		super("BasicStrategyAgent");
	}

	@Override
	public Move playTurn(Hand hand) {
		int playerValue = hand.getValue();

		if (hand.isSplitAllowed() && playerValue != 10 && playerValue != 20) {
			BasicStrategySplitAgent splitAgent = new BasicStrategySplitAgent();
			return splitAgent.playTurn(hand);
		}

		boolean containsAce = false;
		for (Card c : hand.getCards()) {
			if (c.getValue() == Card.Value.ACE) {
				containsAce = true;
			}
		}

		if (containsAce) {
			BasicStrategyStandSoftAgent softAgent = new BasicStrategyStandSoftAgent();
			return softAgent.playTurn(hand);
		} else {
			BasicStrategyStandHardAgent hardAgent = new BasicStrategyStandHardAgent();
			return hardAgent.playTurn(hand);
		}
	}

	@Override
	public void dealerCreateNewDecks(int cards) {
		wager = 1.0;
	}
}
